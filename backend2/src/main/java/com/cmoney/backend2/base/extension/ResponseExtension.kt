package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

/**
 * Http code 為200，並且ResponseBody有CMoneyError物件。如果有CMoneyError則拋出[ServerException]。
{
"Error":{
"Code":101,
"Message":"Auth Failed"
}
}
 */
@Throws(ServerException::class)
fun <T : CMoneyError> T.checkIWithError(): T {
    if (detail == null) {
        return this
    }
    throw ServerException(
        detail.code ?: Constant.SERVICE_ERROR_CODE,
        detail.message.orEmpty()
    )
}

/**
 * Http code 為200，並使用[ISuccess]來決定本次請求是否成功，而不是使用status code。
 * 出現特徵會有IsSuccess、ResponseCode、ResponseMsg這幾個類似欄位．
{
"IsSuccess": false,//不一定有意義
"ResponseCode": 2,
"ResponseMsg": "密碼錯誤"
}
 */
@Throws(ServerException::class)
fun <T : ISuccess> T.checkISuccess(): T {
    if (isResponseSuccess()) {
        return this
    } else {
        throw ServerException(getErrorCode(), getErrorMessage())
    }
}

/**
 * 檢查Http Status是否在200-299，如果是回傳[ResponseBody]，否則拋出[HttpException]。
 */
@Throws(HttpException::class)
fun <T1 : Response<T2>, T2> T1.checkIsSuccessful(): T1 {
    if (this.isSuccessful) {
        return this
    }
    throw HttpException(this)
}

/**
 * 要求[Response]一定要有[ResponseBody]，否則拋出[EmptyBodyException]。
 */
@Throws(EmptyBodyException::class)
fun <T : Response<R>, R> T.requireBody(): R {
    return this.body() ?: throw EmptyBodyException()
}

/**
 * 處理http status code，200-299的狀態，並回傳[R]，如果沒有拋出[EmptyBodyException]。
 * 400的狀態，解析[CMoneyError]，並拋出[ServerException]。
 * 其他拋出[HttpException]。
 */
@Throws(
    ServerException::class,
    HttpException::class,
    EmptyBodyException::class,
    JsonSyntaxException::class
)
fun <T : Response<R>, R> T.checkResponseBody(gson: Gson): R {
    return when {
        this.isSuccessful -> {
            requireBody()
        }
        this.code() == 400 -> {
            throw parseServerException(gson)
        }
        else -> {
            throw HttpException(this)
        }
    }
}

/**
 * code是204的狀態的狀態，並且沒有[ResponseBody]。
 * 400的狀態，解析[CMoneyError]，並拋出[ServerException]。
 * 其他拋出[HttpException]。
 */
@Throws(
    ServerException::class,
    HttpException::class,
    JsonSyntaxException::class
)
fun <T : Response<Void>> T.handleNoContent(gson: Gson) {
    return when {
        code() == 204 -> {
            //不處理任何Body
        }
        code() == 400 -> {
            throw parseServerException(gson)
        }
        else -> {
            throw HttpException(this)
        }
    }
}

/**
 * 自行處理成功時的狀態，400時處理[CMoneyError]，其他[HttpException]。
 */
@Throws(ServerException::class, HttpException::class, JsonSyntaxException::class)
fun <T : Response<ResponseBody>, R> T.handleHttpStatusCode(
    gson: Gson,
    successHandler: (code: Int, ResponseBody?) -> R
): R {
    return when {
        isSuccessful -> {
            successHandler.invoke(code(), body())
        }
        code() == 400 -> {
            throw parseServerException(gson)
        }
        else -> {
            throw HttpException(this)
        }
    }
}

/**
 * 處理CMoneyError。
 * 優先解析第一層結構的message，並且沒有code。
 * 如果第一層沒有message，則解析第一層的detail。
 */
@Throws(JsonSyntaxException::class)
fun <T> Response<T>.parseServerException(gson: Gson): ServerException {
    val errorBody = errorBody()?.string()
    val cmoneyError = gson.fromJson(
        errorBody,
        CMoneyError::class.java
    )
    return if (cmoneyError.message.isNullOrEmpty()) {
        ServerException(
            code = cmoneyError.detail?.code ?: Constant.SERVICE_ERROR_CODE,
            message = cmoneyError.detail?.message.orEmpty()
        )
    } else {
        ServerException(
            code = Constant.SERVICE_NOT_SUPPORT_ERROR_CODE,
            message = cmoneyError.message.orEmpty()
        )
    }
}