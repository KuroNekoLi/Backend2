package com.cmoney.backend2.base.model.calladapter

import com.cmoney.backend2.base.model.logdatarecorder.MutableApiLog
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLog
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogError
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogRequest
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogResponse
import okhttp3.Request
import okio.Buffer
import okio.Timeout
import retrofit2.*
import java.io.EOFException
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

class RecordApiLogCallAdapterFactory(
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
        val isLogRequestBody = (recordApiLogAnnotation as RecordApi).isLogRequestBody

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
                mutableApiLog.domain = setting.domainUrl
                mutableApiLog.userId = setting.identityToken.getMemberId()

                val request = call.request()
                val requestUrl = request.url
                val requestBody = request.body

                mutableApiLog.path = requestUrl.encodedPath

                var logRequestBody: String? = null
                if (isLogRequestBody) {
                    val buffer = Buffer()
                    requestBody?.writeTo(buffer)
                    val contentType = requestBody?.contentType()
                    val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
                    if (buffer.isProbablyUtf8()) {
                        logRequestBody = buffer.use {
                            it.readString(charset)
                        }
                    }
                }
                val logQueryMap = mutableMapOf<String, String>()
                requestUrl.queryParameterNames.forEach { name ->
                    requestUrl.queryParameterValues(name).forEach { value ->
                        logQueryMap[name] = value.orEmpty()
                    }
                }

                mutableApiLog.apiLogRequest = ApiLogRequest(
                    time = System.currentTimeMillis(),
                    headers = request.headers.toMap(),
                    query = logQueryMap.toMap(),
                    body = logRequestBody
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
                                    val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
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

    private fun isAnnotationPresent(
        annotations: Array<Annotation>,
        cls: Class<out Annotation?>
    ): Boolean {
        for (annotation in annotations) {
            if (cls.isInstance(annotation)) {
                return true
            }
        }
        return false
    }

    private fun Buffer.isProbablyUtf8(): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = size.coerceAtMost(64)
            copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (_: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }
}