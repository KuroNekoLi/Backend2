package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption.*
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
    open val id: String?,
    @Transient
    open val modifyTime: Long?
){

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property shareCount 轉推數
     * @property interested 是否感興趣，有此屬性代表感興趣
     * @property interestCount 感興趣的人總數
     * @property rewardPoints 問答獎金
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     */
    data class UnknownArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.UnknownContent?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("@hash-shared")
        override val shareCount: Int?,
        @SerializedName("interested", alternate = ["hasInterest"])
        override val interested: Any?,
        @SerializedName("@hash-interest", alternate = ["interestedCount"])
        override val interestCount: Int?,
        @SerializedName("rewardPoints")
        override val rewardPoints: Int?,
        @SerializedName("@vhash-donate", alternate = ["donation"])
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        SharedInfo,
        QuestionInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property commentDeletedCount 被刪除的回文數
     */
    data class GeneralArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.General?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("@vhash-donate", alternate = ["donation"])
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property commentDeletedCount 被刪除的回文數
     */
    data class NewsArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.News?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        WeightInfo,
        ReportInfo

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property commentDeletedCount 被刪除的回文數
     */
    data class SignalArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Signal?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        WeightInfo,
        ReportInfo

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property commentDeletedCount 被刪除的回文數
     */
    data class GroupArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Group?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("@vhash-donate", alternate = ["donation"])
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property shareCount 轉推數
     * @property voteCount 投票人數
     * @property voteStatus 會員投票狀態(第幾個Index)
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property commentDeletedCount 被刪除的回文數
     */
    data class SharedArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Shared?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("@hash-shared")
        override val shareCount: Int?,
        @SerializedName("@vhash-donate", alternate = ["donation"])
        override val donateCount: Int?,
        @SerializedName("@list-vote")
        override val voteCount : Int?,
        @SerializedName("voteStatus")
        override val voteStatus : Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        SharedInfo,
        DonateInfo,
        VoteStatusInfo,
        WeightInfo,
        ReportInfo

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
     * @property commentCount 總回文數(包含被刪除的回文數)
     * @property interested 是否感興趣，有此屬性代表感興趣
     * @property interestCount 感興趣的人總數
     * @property rewardPoints 問答獎金
     * @property totalReportCount 總檢舉數
     * @property report 是否檢舉
     * @property commentDeletedCount 被刪除的回文數
     */
    data class QuestionArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Question?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @Deprecated("Use myReaction field instead.(make sure api do returns one)")
        @SerializedName("reactionState")
        override val reactionState: Int?,
        @SerializedName("myReaction")
        override val myReaction: String?,
        @SerializedName("reactions", alternate = ["reaction"])
        override val reaction: Map<String, Int>?,
        @SerializedName("@list-reaction")
        override val reactionCount: Int?,
        @SerializedName("collected", alternate = ["hasCollect"])
        override val collected: Any?,
        @SerializedName("@hash-collect", alternate = ["collectedCount"])
        override val collectCount: Int?,
        @SerializedName("comment", alternate = ["myComments"])
        override val myCommentIndex: List<Int>?,
        @SerializedName("@list-comment", alternate = ["commentCount"])
        override val commentCount: Int?,
        @SerializedName("interested", alternate = ["hasInterest"])
        override val interested: Any?,
        @SerializedName("@hash-interest", alternate = ["interestedCount"])
        override val interestCount: Int?,
        @SerializedName("rewardPoints")
        override val rewardPoints: Int?,
        @SerializedName("weight")
        override val weight : Long?,
        @SerializedName("@value-reportCount")
        override val totalReportCount: Int?,
        @SerializedName("report", alternate = ["hasReport"])
        override val report: Any?,
        @Deprecated("v2之後由服務計算正確的回覆數量，故不需要此欄位")
        @SerializedName("@value-commentDeleted")
        override val commentDeletedCount: Int?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        ReactionInfo,
        CollectedInfo,
        CommentInfo,
        QuestionInfo,
        WeightInfo,
        ReportInfo

    /**
     * 個人文章類型
     *
     * @property articleContent 文章內容資訊
     * @property createTime 發文時間
     * @property id 文章Id
     * @property modifyTime 修改時間
     */
    data class PersonalArticleResponseBody(
        @SerializedName("content")
        val articleContent: ArticleContent.Personal?,
        @SerializedName("createTime")
        override val createTime: Long?,
        @SerializedName("id")
        override val id: String?,
        @SerializedName("modifyTime")
        override val modifyTime: Long?,
        @SerializedName("weight")
        override val weight : Long?
    ) : ArticleResponseBody(createTime, id, modifyTime),
        WeightInfo
}

