package com.cmoney.backend2.ocean.service.api.getevaluationlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class GetEvaluationListResponseBodyWithError (
    @SerializedName("Evaluations")
    val evaluations: List<EvaluationRecord>?
):CMoneyError(), IWithError<GetEvaluationListResponseBody> {
    override fun toRealResponse(): GetEvaluationListResponseBody {
        return GetEvaluationListResponseBody(evaluations)
    }
}