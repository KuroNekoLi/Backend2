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

internal fun MutableApiLog.customRequestBody(block: () -> String): Unit {
    val requestBodyString = block()
    this.apiLogRequest = ApiLogRequest(
        time = null, headers = null, body = requestBodyString, query = null
    )
}

internal suspend fun <R : Response<ResponseBody>, ResponseBody> MutableApiLog.logRequest(setting: Setting, block: suspend () -> R): R {
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

internal fun MutableApiLog.logError(exception: Exception) {
    val errorTime = System.currentTimeMillis()
    when (exception) {
        is ServerException -> {
            this.apiLogError = this.apiLogError?.copy(
                time = errorTime,
                errorMsg = exception.message,
                errorCode = exception.code,
                errorPoint = 1
            ) ?: ApiLogError(
                time = errorTime,
                errorMsg = exception.message,
                errorCode = exception.code,
                statusCode = null,
                errorPoint = 1
            )
        }
        is HttpException -> {
            this.apiLogError = this.apiLogError?.copy(
                time = errorTime,
                errorMsg = exception.message(),
                errorPoint = 1
            ) ?: ApiLogError(
                time = errorTime,
                errorMsg = exception.message,
                errorCode = null,
                statusCode = null,
                errorPoint = 1
            )
        }
        else -> {
            this.apiLogError = this.apiLogError?.copy(
                time = errorTime,
                errorMsg = exception.message,
                errorPoint = 2
            ) ?: ApiLogError(
                time = errorTime,
                errorMsg = exception.toString(),
                errorCode = null,
                statusCode = null,
                errorPoint = 2
            )
        }
    }
}