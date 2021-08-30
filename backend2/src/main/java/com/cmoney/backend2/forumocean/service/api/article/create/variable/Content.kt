package com.cmoney.backend2.forumocean.service.api.article.create.variable

import androidx.annotation.IntRange
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.CommodityTag
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.google.gson.annotations.SerializedName

sealed class Content(
    @Transient open val text : String?
) {
    sealed class Article(@Transient override val text : String?) : Content(text){
        /**
         * 一般文章
         *
         * @property text 文章內容
         * @property multiMedia 多媒體資訊
         * @property commodityTags 股票tag資訊
         * @property voteOptions 投票選項
         * @property voteMinutes 發文起算投票截止分鐘數
         * @property topics 標籤
         */
        data class General(
            @SerializedName("text")
            override val text: String?,
            @SerializedName("multiMedia")
            val multiMedia : List<MediaType>?,
            @SerializedName("commodityTags")
            val commodityTags : List<CommodityTag>?,
            @SerializedName("voteOptions")
            val voteOptions : List<String>?,
            @IntRange(from = 5,to = 10080)
            @SerializedName("voteMinutes")
            val voteMinutes : Int?,
            @SerializedName("topics")
            val topics : List<String>?
        ) : Article(text)

        /**
         * 社團文章
         *
         * @property text 文章內容
         * @property groupId 社團頻道ID
         * @property multiMedia 多媒體資訊
         * @property commodityTags 股票tag資訊
         * @property position 以社團名義發文
         * @property voteOptions 投票選項
         * @property voteMinutes 發文起算投票截止分鐘數
         */
        data class Group(
            @SerializedName("text")
            override val text: String?,
            @SerializedName("groupId")
            val groupId : Long,
            @SerializedName("multiMedia")
            val multiMedia : List<MediaType>?,
            @SerializedName("commodityTags")
            val commodityTags : List<CommodityTag>?,
            @SerializedName("position")
            val position : Any?,
            @SerializedName("voteOptions")
            val voteOptions : List<String>?,
            @IntRange(from = 5,to = 10080)
            @SerializedName("voteMinutes")
            val voteMinutes : Int?
        ) : Article(text)

        /**
         * 轉推文章
         *
         * @property text 文章內容
         * @property multiMedia 多媒體資訊
         * @property sharedPostsArticleId 轉推文章Id
         * @property commodityTags 股票tag資訊
         * @property voteOptions 投票選項
         * @property voteMinutes 發文起算投票截止分鐘數
         * @property topics 標籤
         */
        data class Shared(
            @SerializedName("text")
            override val text: String?,
            @SerializedName("multiMedia")
            val multiMedia : List<MediaType>?,
            @SerializedName("sharedPostsArticleId")
            val sharedPostsArticleId : Long,
            @SerializedName("commodityTags")
            val commodityTags : List<CommodityTag>?,
            @SerializedName("voteOptions")
            val voteOptions : List<String>?,
            @IntRange(from = 5,to = 10080)
            @SerializedName("voteMinutes")
            val voteMinutes : Int?,
            @SerializedName("topics")
            val topics : List<String>?
        ) : Article(text)
    }

    /**
     * 問答文章
     *
     * @property text 文章內容
     * @property multiMedia 多媒體資訊
     * @property anonymous 是否匿名
     * @property commodityTags 股票tag資訊
     * @property topics 標籤
     */
    data class Question(
        @SerializedName("text")
        override val text: String?,
        @SerializedName("multiMedia")
        val multiMedia : List<MediaType>?,
        @SerializedName("anonymous")
        val anonymous : Any?,
        @SerializedName("commodityTags")
        val commodityTags : List<CommodityTag>?,
        @SerializedName("topics")
        val topics : List<String>?
    ) : Content(text)
}