package com.cmoney.backend2.additioninformationrevisit.service.api.request

import com.google.gson.annotations.SerializedName

/**
 * 附加資訊的請求Body
 *
 * @property json Json參數
 * @property processing 後續處理步驟，排序、比較等等參數。
 *
 */
class GetRequestParam(
    @SerializedName("Json")
    val json: String,
    @SerializedName("Processing")
    val processing: List<ProcessStep>
)