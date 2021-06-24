package com.cmoney.backend2.ocean.service.api.deletearticle


import com.cmoney.backend2.base.model.setting.Setting
import com.google.gson.annotations.SerializedName

data class DeleteArticleRequestBody(
    @SerializedName("AppId")
    val appId: Int?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("Guid")
    val guid: String?
) {
    class Builder {

        fun build(setting: Setting, articleId: Long): DeleteArticleRequestBody {
            return DeleteArticleRequestBody(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                articleId = articleId
            )
        }
    }
}