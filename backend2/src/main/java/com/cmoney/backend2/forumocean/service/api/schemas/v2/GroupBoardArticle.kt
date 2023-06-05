package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的GroupBoardArticle
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class GroupBoardArticle(
    @SerializedName("id")
    var id: String?,
    @SerializedName("creatorId")
    var creatorId: Int?,
    @SerializedName("unsend")
    var unsend: Boolean?,
    @SerializedName("content")
    var articleContent: GroupBoardArticleContent?,
    @SerializedName("createTime")
    var createTime: Long?,
    @SerializedName("modifyTime")
    var modifyTime: Long?,
    @SerializedName("commentCount")
    var commentCount: Int?,
    @SerializedName("donation")
    var donation: Int?,
    @SerializedName("collectedCount")
    var collectedCount: Int?,
    @SerializedName("emojiCount")
    var emojiCount: EmojiCount?,
    @SerializedName("hasCollect")
    var hasCollect: Boolean?,
    @SerializedName("hasReport")
    var hasReport: Boolean?,
    @SerializedName("myEmoji")
    var myEmoji: String?,
    @SerializedName("interestedCount")
    var interestedCount: Int?,
    @SerializedName("rewardPoints")
    var rewardPoints: Int?,
    @SerializedName("hasInterest")
    var hasInterest: Boolean?,
    @SerializedName("myComments")
    var myComments: List<Int>?,
    @SerializedName("label")
    var label: Label?,
    @SerializedName("isHidden")
    var isHidden: Boolean?,
    @SerializedName("isAnonymous")
    var isAnonymous: Boolean?,
    @SerializedName("authType")
    var authType: String?,
    @SerializedName("isPromotedArticle")
    var isPromotedArticle: Boolean?,
    @SerializedName("isPinnedPromotedArticle")
    var isPinnedPromotedArticle: Boolean?
)