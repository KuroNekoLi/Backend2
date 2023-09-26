package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleContentV2
import com.google.gson.annotations.SerializedName

/**
 * 看板文章回傳
 *
 * @property id
 * @property creatorId
 * @property unsend
 * @property articleContent
 * @property createTime
 * @property modifyTime
 * @property myEmoji
 * @property emojiCount
 * @property collected
 * @property collectCount
 * @property myCommentIndex
 * @property commentCount
 * @property shareCount
 * @property interested
 * @property interestCount
 * @property rewardPoints
 * @property donateCount
 * @property voteCount
 * @property voteStatus
 * @property totalReportCount
 * @property hasReport
 * @property isHidden
 * @property anonymous
 * @property authType
 * @property isPromotedArticle
 * @property isPinnedPromotedArticle
 * @property reply 被回覆的訊息內容
 */
data class GroupArticlesResponseBody(
    @SerializedName("id")
    val id: String?,
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("unsend")
    val unsend: Boolean?,
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
    val isPinnedPromotedArticle: Boolean?,
    @SerializedName("reply")
    val reply: ArticleReply?
)