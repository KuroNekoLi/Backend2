package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.CollectedInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.CommentInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.DonateInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.QuestionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.ReportInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo
import com.google.gson.annotations.SerializedName

/**
 * 回文內容v2
 *
 * @property creatorId 發文者
 */
data class CommentContentV2(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("creatorId")
    val creatorId: Long?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("modifyTime")
    val modifyTime: Long?,
    @SerializedName("text")
    val text : String?,
    @SerializedName("multiMedia")
    val multiMedia : List<MediaTypeInfo>?,
    @SerializedName("isHidden")
    val isHidden:Boolean?,
    @Deprecated("Use myReaction field instead.(make sure api do returns one)")
    @SerializedName("reactionState")
    override val reactionState: Int?,
    @SerializedName("myReaction")
    override val myReaction: String?,
    @SerializedName("reaction")
    override val reaction: Map<String, Int>?,
    @SerializedName("@list-reaction")
    override val reactionCount: Int?,
    @SerializedName("hasCollect")
    override val collected: Any?,
    @SerializedName("collectedCount")
    override val collectCount: Int?,
    @SerializedName("myComments")
    override val myCommentIndex: List<Int>?,
    @SerializedName("commentCount")
    override val commentCount: Int?,
    @SerializedName("donation")
    override val donateCount: Int?,
    @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
    @SerializedName("@value-commentDeleted")
    override val commentDeletedCount: Int?,
    @SerializedName("@value-reportCount")
    override val totalReportCount: Int?,
    @SerializedName("hasReport")
    override val report: Any?,
    @SerializedName("interested", alternate = ["hasInterest"])
    override val interested: Any?,
    @SerializedName("@hash-interest", alternate = ["interestedCount"])
    override val interestCount: Int?,
    @SerializedName("rewardPoints")
    override val rewardPoints: Int?,
) : ReactionInfo,
    CollectedInfo,
    CommentInfo,
    DonateInfo,
    ReportInfo,
    QuestionInfo
