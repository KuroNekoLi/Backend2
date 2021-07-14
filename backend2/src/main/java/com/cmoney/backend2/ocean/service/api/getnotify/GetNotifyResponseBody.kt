package com.cmoney.backend2.ocean.service.api.getnotify
import com.cmoney.backend2.ocean.service.api.variable.NotificationType
import com.google.gson.annotations.SerializedName


data class GetNotifyResponseBody(
    @SerializedName("Data")
    val notifyList: List<Notify>?
)

data class Notify(
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("ImageUrl")
    val imageUrl: String?,
    @SerializedName("IsFollowed")
    val isFollowed: Boolean?,
    @SerializedName("IsReaded")
    val isReaded: Boolean?,
    @SerializedName("IsSpecific")
    val isSpecific: Boolean?,
    @SerializedName("MemberChannelId")
    val memberChannelId: Long?,
    @SerializedName("NotificationType")
    val notificationType: NotificationType?,
    @SerializedName("NotifyTime")
    val notifyTime: Long?,
    @SerializedName("RelatedArticleId")
    val relatedArticleId: Long?,
    @SerializedName("Sn")
    val sn: Long?,
    @SerializedName("Title")
    val title: String?
)