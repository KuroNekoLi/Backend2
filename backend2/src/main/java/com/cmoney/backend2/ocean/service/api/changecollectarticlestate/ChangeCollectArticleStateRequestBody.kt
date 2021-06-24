package com.cmoney.backend2.ocean.service.api.changecollectarticlestate


import com.google.gson.annotations.SerializedName

data class ChangeCollectArticleStateRequestBody(
    @SerializedName("AppId")
    val appId: Int?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("Guid")
    val guid: String?,
    @SerializedName("IsCollected")
    val isCollected: Boolean?
)