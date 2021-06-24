package com.cmoney.backend2.ocean.service.api.variable.channelinfo

import com.cmoney.backend2.ocean.service.api.variable.*
import com.google.gson.annotations.SerializedName

sealed class ChannelInfo {
    /**
     * 其他類型頻道資訊
     *
     * @property channelId 頻道編號
     * @property channelName 頻道名稱
     * @property description 頻道介紹
     * @property image 頻道頭像
     */
    data class ChannelBaseInfo(
        @SerializedName("ChannelId")
        val channelId: Long?,
        @SerializedName("ChannelName")
        val channelName: String?,
        @SerializedName("Description")
        val description: String?,
        @SerializedName("Image")
        val image: String?
    ) : ChannelInfo()

    /**
     * RSS、訊號頻道資訊
     *
     * @property channelId 頻道編號
     * @property channelName 頻道名稱
     * @property description 頻道介紹
     * @property image 頻道頭像
     * @property fansCount 粉絲數(追蹤這個頻道的會員數)
     * @property likeCount 獲得讚(對頻道內的文章按讚數)
     * @property articleCount 道的文章總數
     * @property isFollowed 是否已追蹤此頻道
     */
    data class RssSignalChannelInfo(
        @SerializedName("ChannelId")
        val channelId: Long?,
        @SerializedName("ChannelName")
        val channelName: String?,
        @SerializedName("Description")
        val description: String?,
        @SerializedName("Image")
        val image: String?,
        @SerializedName("FansCount")
        val fansCount : Int?,
        @SerializedName("LikeCount")
        val likeCount : Int?,
        @SerializedName("ArticleCount")
        val articleCount :Int?,
        @SerializedName("IsFollowed")
        val isFollowed : Boolean?
    ) : ChannelInfo()

    /**
     * 會員頻道資訊
     *
     * @property channelId 頻道編號
     * @property channelName 頻道名稱
     * @property description 頻道介紹
     * @property image 頻道頭像
     * @property answersCount 會員解答數
     * @property articleCount 頻道的文章總數
     * @property attentionCount 關注數(此會員頻道追蹤中的頻道數量)(清單不提供此屬性)
     * @property diamondInfo 鑽石等級
     * @property evaluationInfo 評分資訊
     * @property fansCount 粉絲數(追蹤這個頻道的會員數)
     * @property isFollowed 是否已追蹤此頻道
     * @property levelInfo 頻道等級
     * @property likeCount 獲得讚(對頻道內的文章按讚數)
     * @property memberRegisterTime 會員註冊時間 UnixTime(秒)
     * @property viewerEvaluationInfo 瀏覽者對頻道的評價
     */
    data class MemberChannelInfo(
        @SerializedName("ChannelId")
        val channelId: Long?,
        @SerializedName("ChannelName")
        val channelName: String?,
        @SerializedName("Description")
        val description: String?,
        @SerializedName("Image")
        val image: String?,
        @SerializedName("AnswersCount")
        val answersCount: Int?,
        @SerializedName("ArticleCount")
        val articleCount: Int?,
        @SerializedName("AttentionCount")
        val attentionCount: Int?,
        @SerializedName("DiamondInfo")
        val diamondInfo: DiamondInfo?,
        @SerializedName("EvaluationInfo")
        val evaluationInfo: EvaluationInfo?,
        @SerializedName("FansCount")
        val fansCount: Int?,
        @SerializedName("IsFollowed")
        val isFollowed: Boolean?,
        @SerializedName("LevelInfo")
        val levelInfo: LevelInfo?,
        @SerializedName("LikeCount")
        val likeCount: Int?,
        @SerializedName("MemberRegisterTime")
        val memberRegisterTime: Long?,
        @SerializedName("ViewerEvaluationInfo")
        val viewerEvaluationInfo: ViewerEvaluationInfo?
    ) : ChannelInfo()

    /**
     * 社團頻道資訊
     *
     * @property channelId 頻道編號
     * @property channelName 頻道名稱
     * @property description 頻道介紹
     * @property image 頻道頭像
     * @property memberClubInfo 會員在這個社團的資訊
     * @property viewerClubInfo 瀏覽者在這個社團的資訊
     * @property clubInfo 社團資訊
     */
    data class ClubChannelInfo(
        @SerializedName("ChannelId")
        val channelId: Long?,
        @SerializedName("ChannelName")
        val channelName: String?,
        @SerializedName("Description")
        val description: String?,
        @SerializedName("Image")
        val image: String?,
        @SerializedName("MemberClubInfo")
        val memberClubInfo: MemberClubInfo?,
        @SerializedName("ViewerClubInfo")
        val viewerClubInfo: MemberClubInfo?,
        @SerializedName("ClubInfo")
        val clubInfo: ClubInfo?
    ) : ChannelInfo()
}