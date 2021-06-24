package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

/**
 *  通知類型 參考網址
 * https://docs.google.com/spreadsheets/d/1M7HaGQTjOhtRDKkqNXAE7sxoMr_tHjfzquSy1F-0Oro/edit#gid=1820030006
 */
enum class NotificationType(val value: Int) {
    @SerializedName("1")
    ReplyArticleNotify(1),	//回應文通知
    @SerializedName("2")
    GroupArticleNotify(2),//社團發文通知
    @SerializedName("4")
    LikeArticle(4),//一般文章被按讚 一般文章的回文被按讚
    @SerializedName("6")
    FollowMemberArticle(6),//追蹤對象發文
    @SerializedName("7")
    Follow(7),//有人追蹤你
    @SerializedName("8")
    ArticleTip(8),//文章打賞
    @SerializedName("9")
    NotifyQuestionAsker(9),//	通知提問者問題被回覆
    @SerializedName("10")
    NotifyMemberInterestedInQuestion(10),//	通知我也想知道者問題被回覆
    @SerializedName("11")
    NoAnswerForAsker(11),//通知提問者提問沒人回答
    @SerializedName("12")
    NoAnswerForInterested(12),//通知我也想知道者提問沒人回答
    @SerializedName("13")
    ReplyWithoutLikeForAsker(13),//通知提問者問題沒有最佳解
    @SerializedName("14")
    ReplyWithoutLikeInterested(14),//	通知我也想知道者問題沒有最佳解
    @SerializedName("15")
    BestAnswerForAsker(15),//通知提問者有最佳解
    @SerializedName("16")
    BestAnswerForInterested(16),//通知我也想知道者有最佳解
    @SerializedName("17")
    BestAnswerForReplier(17),//通知回答者被選為最佳解
    @SerializedName("18")
    FollowMemberAskArticle(18),//追蹤對象提問
    @SerializedName("19")
    LikeQAndAArticle(19),//提問文章被按讚 提問文章的回文被按讚
    @SerializedName("22")
    ReplySuggest(22),//給一點建議被回覆
}