package com.cmoney.backend2.additioninformationrevisit.service.api.request

import com.google.gson.annotations.SerializedName

data class GetRequestParam(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    /**
     * 參數，jsonString
     */
    @SerializedName("Json")
    val json: String,
    /**
     * 後續處理步驟，jsonArray
     *
     * ex: [
     *   {
     *     "processType": "string",
     *     "parameterJson": "string"
     *   }
     * ]
     */
    @SerializedName("Processing")
    val processing: List<ProcessStep>
)