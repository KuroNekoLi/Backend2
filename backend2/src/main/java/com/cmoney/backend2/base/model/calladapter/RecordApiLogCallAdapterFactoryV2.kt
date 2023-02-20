package com.cmoney.backend2.base.model.calladapter

import com.cmoney.backend2.base.model.logdatarecorder.MutableApiLog
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLog
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogError
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogRequest
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

class RecordApiLogCallAdapterFactoryV2(
    private val setting: Setting,
    private val logDataRecorder: LogDataRecorder
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val recordApiLogAnnotation = annotations.find {
            it is RecordApi
        } ?: return null
        val isLogCMoneyAction = (recordApiLogAnnotation as RecordApi).cmoneyAction.isNotEmpty()

        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        require(returnType is ParameterizedType) { "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>" }
        val responseType = getParameterUpperBound(
            0,
            returnType
        )
        return object : CallAdapter<Any?, Call<*>> {
            override fun responseType(): Type {
                return responseType
            }

            override fun adapt(call: Call<Any?>): Call<Any?> {
                val mutableApiLog = MutableApiLog()
                mutableApiLog.userId = setting.identityToken.getMemberId()
                val logCMoneyAction = recordApiLogAnnotation.cmoneyAction.takeIf { isLogCMoneyAction }
                val request = call.request()
                val requestUrl = request.url
                mutableApiLog.domain = "${requestUrl.scheme}://${requestUrl.host}"
                val logHeaders = request.headers.toMap()
                    .filter { header ->
                        header.key != "Authorization"
                    }.takeIf { it.isNotEmpty() }

                mutableApiLog.path = requestUrl.encodedPath


                val logQueryMap = requestUrl.queryParameterNames
                    .flatMap { name ->
                        requestUrl.queryParameterValues(name)
                            .filterNotNull().map { value ->
                                name to value
                            }
                    }
                    .toMap()
                    .takeIf { it.isNotEmpty() }

                mutableApiLog.apiLogRequest = ApiLogRequest(
                    time = System.currentTimeMillis(),
                    headers = logHeaders,
                    query = logQueryMap,
                    body = null,
                    action = logCMoneyAction
                )
                return ApiLogCall(mutableApiLog, call, logDataRecorder)
            }
        }
    }

    internal class ApiLogCall<T>(
        private val mutableApiLog: MutableApiLog,
        private val delegate: Call<T?>,
        private val logDataRecorder: LogDataRecorder
    ) : Call<T?> {
        private fun logApi(mutableApiLog: MutableApiLog) {
            val apiLog = ApiLog(
                domain = mutableApiLog.domain,
                path = mutableApiLog.path,
                userId = mutableApiLog.userId,
                apiLogRequest = mutableApiLog.apiLogRequest,
                apiLogResponse = mutableApiLog.apiLogResponse,
                apiLogError = mutableApiLog.apiLogError
            )
            logDataRecorder.logApi(apiLog)
        }

        override fun enqueue(callback: Callback<T?>) {
            delegate.enqueue(object : Callback<T?> {
                override fun onResponse(call: Call<T?>, response: Response<T?>) {
                    val rawResponse = response.raw()
                    mutableApiLog.apiLogResponse = ApiLogResponse(
                        time = rawResponse.receivedResponseAtMillis,
                        statusCode = rawResponse.code
                    )
                    if (call.isCanceled) {
                        onFailure(this@ApiLogCall, IOException("Canceled"))
                    } else {
                        if (!response.isSuccessful) {
                            val errorResponseBody = response.errorBody()?.source()?.buffer?.clone()
                                ?.use { buffer ->
                                    val contentType = rawResponse.body?.contentType()
                                    val charset: Charset =
                                        contentType?.charset(StandardCharsets.UTF_8)
                                            ?: StandardCharsets.UTF_8
                                    buffer.readString(charset)
                                }
                            mutableApiLog.apiLogError = ApiLogError(
                                time = System.currentTimeMillis(),
                                errorMsg = errorResponseBody,
                                errorPoint = ApiLogError.ERROR_POINT_SERVER,
                                errorCode = null,
                                statusCode = null
                            )
                        }
                        logApi(mutableApiLog)
                        callback.onResponse(this@ApiLogCall, response)
                    }
                }

                override fun onFailure(call: Call<T?>, t: Throwable) {
                    mutableApiLog.apiLogError = ApiLogError(
                        time = System.currentTimeMillis(),
                        errorMsg = t.toString(),
                        errorCode = null,
                        statusCode = null,
                        errorPoint = ApiLogError.ERROR_POINT_CLIENT
                    )
                    logApi(mutableApiLog)
                    callback.onFailure(this@ApiLogCall, t)
                }
            })
        }

        override fun isExecuted(): Boolean {
            return delegate.isExecuted
        }

        @Throws(IOException::class)
        override fun execute(): Response<T?> {
            return delegate.execute()
        }

        override fun cancel() {
            delegate.cancel()
        }

        override fun isCanceled(): Boolean {
            return delegate.isCanceled
        }

        override fun clone(): Call<T?> {
            return ApiLogCall(mutableApiLog, delegate.clone(), logDataRecorder)
        }

        override fun request(): Request {
            return delegate.request()
        }

        override fun timeout(): Timeout {
            return delegate.timeout()
        }
    }
}