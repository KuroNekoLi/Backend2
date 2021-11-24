package com.cmoney.backend2.data.extension

import com.cmoney.backend2.base.extension.toListOfSomething
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.data.service.api.FundIdData
import com.cmoney.backend2.data.service.api.FundIdWithError
import com.google.gson.Gson

internal fun FundIdWithError.checkApiError(): FundIdWithError {
    return when (state) {
        FundIdWithError.STATE_FAIL,
        FundIdWithError.STATE_TIMEOUT -> {
            throw ServerException(
                Constant.SERVICE_ERROR_CODE,
                ""
            )
        }
        FundIdWithError.STATE_SUCCESS,
        FundIdWithError.STATE_EMPTY -> this
        else -> this
    }
}

inline fun <reified T> FundIdData.toListOfSomething(gson: Gson): List<T> {
    return DtnoData(title, data).toListOfSomething(gson)
}
