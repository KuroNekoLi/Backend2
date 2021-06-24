package com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetStockMasterEvaluationListResponseBodyWithError(
    @SerializedName("MasterEvaluationScores")
    val masterEvaluationScores: List<MasterEvaluationScore?>?
):CMoneyError(), IWithError<GetStockMasterEvaluationListResponseBody> {
    override fun toRealResponse(): GetStockMasterEvaluationListResponseBody {
        return GetStockMasterEvaluationListResponseBody(masterEvaluationScores)
    }
}