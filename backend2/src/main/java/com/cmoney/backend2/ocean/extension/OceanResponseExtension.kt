package com.cmoney.backend2.ocean.extension

import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import retrofit2.Response

/**
 * 只有 Http code 為400，會去解析Error Body,其他error走HttpException
{
"Error":{
"Code":101,
"Message":"Auth Failed"
}
}
 */
@Throws(
    ServerException::class,
    HttpException::class,
    EmptyBodyException::class,
    JsonSyntaxException::class
)
inline fun <reified T : Response<T1>, reified T1> T.checkOceanResponseBody(gson: Gson): T1 {
    return when {
        this.isSuccessful -> {
            requireBody()
        }
        this.code() == 400 -> {
            val cmoneyError = gson.fromJson(
                errorBody()?.string(),
                CMoneyError::class.java
            )
            throw ServerException(
                cmoneyError.detail?.code ?: Constant.SERVICE_ERROR_CODE,
                cmoneyError.detail?.message.orEmpty()
            )
        }
        else -> {
            //主要是 response.code() in 401..599
            throw HttpException(this)
        }
    }
}