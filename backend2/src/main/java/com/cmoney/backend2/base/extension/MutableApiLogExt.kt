package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.logdatarecorder.MutableApiLog
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLog
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogError
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogRequest
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogResponse
import retrofit2.HttpException
import retrofit2.Response

/**
 * 使用try catch紀錄Api行為，並在finally真正紀錄。
 */
internal suspend fun <R> runCatchingWithLog(block: suspend MutableApiLog.() -> R): Result<R> {
    val mutableApiLog = MutableApiLog()
    return try {
        val successResult = mutableApiLog.block()
        Result.success<R>(successResult)
    } catch (exception: Exception) {
        mutableApiLog.logError(exception)
        Result.failure<R>(exception)
    } finally {
        val apiLog = ApiLog(
            domain = mutableApiLog.domain,
            path = mutableApiLog.path,
            userId = mutableApiLog.userId,
            apiLogRequest = mutableApiLog.apiLogRequest,
            apiLogResponse = mutableApiLog.apiLogResponse,
            apiLogError = mutableApiLog.apiLogError
        )
        LogDataRecorder.getInstance().logApi(apiLog)
    }
}

/**
 * 取用字串紀錄客製化的RequestBody，只要是能辨別出API的RequestBody的字串即可。
 * ex: "field1,field2"
 */
internal fun MutableApiLog.customRequestBody(block: () -> String): Unit {
    val requestBodyString = block()
    this.apiLogRequest = this.apiLogRequest?.copy(
        body = requestBodyString
    ) ?: ApiLogRequest(
        time = null, headers = null, body = requestBodyString, query = null
    )
}

/**
 * 紀錄Request
 *
 * @param setting Backend的Setting，主要紀錄userId
 * @param block 呼叫Api的行為
 */
internal suspend fun <R : Response<ResponseBody>, ResponseBody> MutableApiLog.logRequest(
    setting: Setting,
    block: suspend () -> R
): R {
    this.userId = setting.identityToken.getMemberId()
    val response = block()
    val rawResponse = response.raw()
    val rawRequest = rawResponse.request
    this.domain = rawResponse.request.url.host
    this.path = rawRequest.url.encodedPath
    val logQueryMap = mutableMapOf<String, String>()
    rawRequest.url.queryParameterNames.forEach { name ->
        rawRequest.url.queryParameterValues(name).forEach { value ->
            logQueryMap[name] = value.orEmpty()
        }
    }
    this.apiLogRequest = this.apiLogRequest?.copy(
        time = rawResponse.sentRequestAtMillis,
        headers = rawRequest.headers.toMap(),
        query = logQueryMap.toMap()
    ) ?: ApiLogRequest(
        time = rawResponse.sentRequestAtMillis,
        headers = rawRequest.headers.toMap(),
        query = logQueryMap.toMap(),
        body = null
    )
    this.apiLogResponse = this.apiLogResponse?.copy(
        time = rawResponse.receivedResponseAtMillis,
        statusCode = rawResponse.code
    ) ?: ApiLogResponse(
        time = rawResponse.receivedResponseAtMillis,
        statusCode = rawResponse.code
    )
    return response
}

/**
 * 紀錄API的錯誤行為
 */
internal fun MutableApiLog.logError(exception: Exception) {
    val errorTime = System.currentTimeMillis()
    when (exception) {
        is ServerException -> {
            this.apiLogError = this.apiLogError?.copy(
                time = errorTime,
                errorMsg = exception.message,
                errorCode = exception.code,
                errorPoint = ApiLogError.ERROR_POINT_SERVER
            ) ?: ApiLogError(
                time = errorTime,
                errorMsg = exception.message,
                errorCode = exception.code,
                statusCode = null,
                errorPoint = ApiLogError.ERROR_POINT_SERVER
            )
        }
        is HttpException -> {
            this.apiLogError = this.apiLogError?.copy(
                time = errorTime,
                errorMsg = exception.message,
                errorPoint = ApiLogError.ERROR_POINT_SERVER
            ) ?: ApiLogError(
                time = errorTime,
                errorMsg = exception.message,
                errorCode = null,
                statusCode = null,
                errorPoint = ApiLogError.ERROR_POINT_SERVER
            )
        }
        else -> {
            this.apiLogError = this.apiLogError?.copy(
                time = errorTime,
                errorMsg = exception.toString(),
                errorPoint = ApiLogError.ERROR_POINT_CLIENT
            ) ?: ApiLogError(
                time = errorTime,
                errorMsg = exception.toString(),
                errorCode = null,
                statusCode = null,
                errorPoint = ApiLogError.ERROR_POINT_CLIENT
            )
        }
    }
}