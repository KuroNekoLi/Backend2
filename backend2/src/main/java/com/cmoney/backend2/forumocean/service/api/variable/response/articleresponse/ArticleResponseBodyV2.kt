package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse

import com.google.gson.annotations.SerializedName

data class ArticleResponseBodyV2(
    @SerializedName("id")
    val id: String?,
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("content")
    val articleContent: ArticleContentV2?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("modifyTime")
    val modifyTime: Long?,
    @SerializedName("myEmoji")
    val myEmoji: String?,
    @SerializedName("emojiCount")
    val emojiCount: Map<String, Int>?,
    @SerializedName("hasCollect")
    val collected: Boolean?,
    @SerializedName("collectedCount")
    val collectCount: Int?,
    @SerializedName("myComments")
    val myCommentIndex: List<Int>?,
    @SerializedName("commentCount")
    val commentCount: Int?,
    @SerializedName("@hash-shared")
    val shareCount: Int?,
    @SerializedName("hasInterest")
    val interested: Boolean?,
    @SerializedName("interestedCount")
    val interestCount: Int?,
    @SerializedName("rewardPoints")
    val rewardPoints: Int?,
    @SerializedName("donation")
    val donateCount: Int?,
    @SerializedName("@list-vote")
    val voteCount: Int?,
    @SerializedName("voteStatus")
    val voteStatus: Int?,
    @SerializedName("@value-reportCount")
    val totalReportCount: Int?,
    @SerializedName("hasReport")
    val hasReport: Boolean?,
    @SerializedName("isHidden")
    val isHidden: Boolean?,
    @SerializedName("isAnonymous")
    val anonymous: Boolean?,
    @SerializedName("authType")
    val authType: String?,
    @SerializedName("isPromotedArticle")
    val isPromotedArticle: Boolean?,
    @SerializedName("isPinnedPromotedArticle")
    val isPinnedPromotedArticle: Boolean?
)