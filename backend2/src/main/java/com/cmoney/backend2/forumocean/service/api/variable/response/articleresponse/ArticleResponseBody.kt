package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.*
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articlestate.ArticleState
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption.ArticleContent
import com.google.gson.annotations.SerializedName

/**
 * 取得文章回傳的結果
 *
 * @property createTime 發文時間
 * @property id 文章Id
 * @property modifyTime 修改時間
 */
sealed class ArticleResponseBody(
    @Transient
    open val createTime: Long?,
    @Transient
    open val id: Long?,
    @Transient
    open val modifyTime: Long?
) {

    /**
     * 未知文章類型 將所有資料都接收
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount 總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property shareCount 轉推數
     * @property interested 是否感興趣，有此屬性代表感興趣
     * @property interestCount 感興趣的人總數
     * @property rewardPoints 問答獎金
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class UnknownArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.UnknownContent?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("@hash-shared")
        override val shareCount: Int?,
        @SerializedName("interested")
        override val interested: Any?,
        @SerializedName("@hash-interest")
        override val interestCount: Int?,
        @SerializedName("rewardPoints")
        override val rewardPoints: Int?,
        @SerializedName("@vhash-donate")
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        SharedInfo,
        QuestionInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo

    /**
     * 一般文章
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount  總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class GeneralArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.General?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("@vhash-donate")
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo

    /**
     * 新聞文章
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount  總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class NewsArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.News?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo

    /**
     * 訊號文章
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount  總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class SignalArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Signal?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo

    /**
     * 社團文章
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount 總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class GroupArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Group?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("@vhash-donate")
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo

    /**
     * 轉推文章
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount 總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property shareCount 轉推數
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class SharedArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Shared?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("@hash-shared")
        override val shareCount: Int?,
        @SerializedName("@vhash-donate")
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        SharedInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo

    /**
     * 問答文章
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     * @property reactionState 我的反應
     * @property reaction [目前反應類別: 該反應數]
     * @property reactionCount 主文的總反應數
     * @property collected 是否收藏，有此屬性代表有收藏
     * @property collectCount 總收藏數
     * @property myCommentIndex 我的回文位置
     * @property commentCount 總回文數
     * @property interested 是否感興趣，有此屬性代表感興趣
     * @property interestCount 感興趣的人總數
     * @property rewardPoints 問答獎金
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property articleState 文章狀態
     */
    data class QuestionArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Question?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: Long?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("reactions")
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected")
        override val collected: Any?,
        @SerializedName("@hash-collect")
        override val collectCount: Int?,
        @SerializedName("comment")
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment")
        override val commentCount: Int?,
        @SerializedName("interested")
        override val interested: Any?,
        @SerializedName("@hash-interest")
        override val interestCount: Int?,
        @SerializedName("rewardPoints")
        override val rewardPoints: Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report")
        override val report: Any?,
        @SerializedName("articleState")
        override val articleState: ArticleState?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        QuestionInfo,
        WeightInfo,
        ReportInfo,
        ArticleStateInfo
}

