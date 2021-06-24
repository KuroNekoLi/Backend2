package com.cmoney.backend2.ocean.service.api.getmasters

import com.google.gson.annotations.SerializedName

data class GetMastersRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,

    /**
     * 解答大師排名(1-熱門值,2-粉絲增長,3-鑽石增長)
     */
    @SerializedName("MasterType")
    val masterType: Int,

    /**
     * 取得排名數
     */
    @SerializedName("FetchCount")
    val fetchCount: Int
)