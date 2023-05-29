package com.cmoney.backend2.additioninformationrevisit.service.api.request

import com.google.gson.annotations.SerializedName

/**
 * 處理步驟
 *
 * @property type 類型
 * @property json 處理參數
 *
 */
data class ProcessStep(
    @SerializedName("ProcessType")
    val type: String,
    @SerializedName("ParameterJson")
    val json: String
)
