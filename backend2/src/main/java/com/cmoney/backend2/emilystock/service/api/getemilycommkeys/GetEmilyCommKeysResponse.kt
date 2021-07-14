package com.cmoney.backend2.emilystock.service.api.getemilycommkeys

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class GetEmilyCommKeysResponse(
    @SerializedName("EmilyCommKeys")
    val emilyCommKeys: List<String>?
): CMoneyError()