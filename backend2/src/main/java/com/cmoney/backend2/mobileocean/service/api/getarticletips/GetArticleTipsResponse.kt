package com.cmoney.backend2.mobileocean.service.api.getarticletips

import com.google.gson.annotations.SerializedName

data class GetArticleTipsResponse(
    /**
     * 打賞清單
     */
    @SerializedName("Tips")
    val tips : List<Tip>
)