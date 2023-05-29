package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.club

import com.google.gson.annotations.SerializedName


data class GetJoinedClubArticlesResponse(
    @SerializedName("articles")
    var articles: ArrayList<JoinedClubArticle> = arrayListOf(),
    @SerializedName("hasNext")
    var hasNext: Boolean? = null,
    @SerializedName("nextStartWeight")
    var nextStartWeight: Int? = null
)

data class JoinedClubArticle(
    @SerializedName("unsend")
    var unsend: Boolean? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("creatorId")
    var creatorId: Int? = null,
    @SerializedName("content")
    var content: Content? = Content(),
    @SerializedName("createTime")
    var createTime: Int? = null,
    @SerializedName("modifyTime")
    var modifyTime: Int? = null,
    @SerializedName("commentCount")
    var commentCount: Int? = null,
    @SerializedName("donation")
    var donation: Int? = null,
    @SerializedName("collectedCount")
    var collectedCount: Int? = null,
    @SerializedName("emojiCount")
    var emojiCount: EmojiCount? = EmojiCount(),
    @SerializedName("hasCollect")
    var hasCollect: Boolean? = null,
    @SerializedName("hasReport")
    var hasReport: Boolean? = null,
    @SerializedName("myEmoji")
    var myEmoji: String? = null,
    @SerializedName("interestedCount")
    var interestedCount: Int? = null,
    @SerializedName("rewardPoints")
    var rewardPoints: Int? = null,
    @SerializedName("hasInterest")
    var hasInterest: Boolean? = null,
    @SerializedName("myComments")
    var myComments: ArrayList<Int> = arrayListOf(),
    @SerializedName("label")
    var label: Label? = Label(),
    @SerializedName("isHidden")
    var isHidden: Boolean? = null,
    @SerializedName("isAnonymous")
    var isAnonymous: Boolean? = null,
    @SerializedName("authType")
    var authType: String? = null,
    @SerializedName("isPromotedArticle")
    var isPromotedArticle: Boolean? = null,
    @SerializedName("isPinnedPromotedArticle")
    var isPinnedPromotedArticle: Boolean? = null
)

data class Content(
    @SerializedName("commodityTags")
    var commodityTags: ArrayList<CommodityTags> = arrayListOf(),
    @SerializedName("text")
    var text: String? = null,
    @SerializedName("multiMedia")
    var multiMedia: ArrayList<MultiMedia> = arrayListOf()
)

data class CommodityTags(
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("key")
    var key: String? = null,
    @SerializedName("bullOrBear")
    var bullOrBear: Int? = null
)

data class MultiMedia(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("width")
    var width: Int? = null,
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("mediaType")
    var mediaType: String? = null,
    @SerializedName("url")
    var url: String? = null
)

data class EmojiCount(
    @SerializedName("like")
    var like: Int? = null,
    @SerializedName("dislike")
    var dislike: Int? = null,
    @SerializedName("laugh")
    var laugh: Int? = null,
    @SerializedName("money")
    var money: Int? = null,
    @SerializedName("shock")
    var shock: Int? = null,
    @SerializedName("cry")
    var cry: Int? = null,
    @SerializedName("think")
    var think: Int? = null,
    @SerializedName("angry")
    var angry: Int? = null
)

data class Label(
    @SerializedName("red")
    var red: Boolean? = null,
    @SerializedName("yellow")
    var yellow: Boolean? = null
)
