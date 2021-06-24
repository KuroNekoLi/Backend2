package com.cmoney.backend2.ocean.service.api.variable

class ArticleNeedInfo {

    /**
     * 文章附加資訊列舉
     *
     * @property value
     */
    enum class NeedInfo(val value: Int) {
        /**
         * 文章標題
         */
        Title(1),

        /**
         * 內文附帶的圖片
         */
        ContentImage(2),

        /**
         * 內文附帶的影片連結
         */
        ContentVideoUrl(4),

        /**
         * 來源連結(新聞文章才有)
         */
        SourceUrl(8),

        /**
         * 文章來自XXX
         */
        ArticleFrom(16),

        /**
         * 股票標籤資訊
         */
        StockTags(32),

        /**
         * 打賞資訊
         */
        TipInfo(64),

        /**
         * 問答文章資訊 非提問文章，則不會提供此資訊
         */
        AskInfo(128),

        /**
         * 按讚數
         */
        LikeCount(1024),

        /**
         * 按噓數
         */
        DislikeCount(2048),

        /**
         * 回覆數
         */
        ReplyCount(4096),

        /**
         * 轉推數
         */
        RetweetCount(8192),

        /**
         * 點擊數
         */
        ClickCount(16384),

        /**
         * 使用者是否有按讚  按噓
         */
        IsLikedAndIsDisliked(65536),

        /**
         * 使用者是否有收藏此文章
         */
        IsCollected(131072),

        /**
         * 是否已追蹤此頻道
         */
        IsFollowedAuthor(1048576),

        /**
         *  發文者資訊(包含匿名)
         */
        AuthorInfo(2097152),

        /**
         * .發文者等級
         */
        AuthorInfoLevel(4194304),

        /**
         * 發文者鑽石資訊
         */
        AuthorInfoDiamond(8388608),

        /**
         * 文章所屬頻道位置(社團)
         */
        LocationChannelInfo(33554432),

        /**
         * 被轉推的原文資訊
         */
        RetweetOriginalArticle(67108864)
    }

    /**
     * 設定的文章附加資訊清單
     */
    private val needInfo = ArrayList<NeedInfo>()

    /**
     * 加入附加資訊
     *
     * @param info
     */
    fun add(info: NeedInfo) = needInfo.add(info)

    /**
     * 移除附加資訊
     *
     * @param info
     */
    fun remove(info: NeedInfo) = needInfo.remove(info)

    /**
     * 加入附加資訊清單
     *
     * @param infoList
     */
    fun addAll(infoList: Collection<NeedInfo>) = needInfo.addAll(infoList)

    /**
     * 加入新聞文章縮需的附加資訊
     * Title
     * SourceUrl
     *
     */
    fun addNewsArticleInfo() {
        addAll(listOf(NeedInfo.Title, NeedInfo.SourceUrl))
    }

    /**
     * 加入文章作者的附加資訊
     * AuthorInfo
     * AuthorInfoDiamond
     * AuthorInfoLevel
     *
     */
    fun addAuthorInfo() {
        addAll(listOf(NeedInfo.AuthorInfo, NeedInfo.AuthorInfoDiamond, NeedInfo.AuthorInfoLevel))
    }

    /**
     * 加入文章的所屬社團訊息附加資訊
     * LocationChannelInfo
     */
    fun addClubAppendInfo() {
        addAll(listOf(NeedInfo.LocationChannelInfo))
    }

    /**
     * 加入轉推文章訊息的附加資訊
     * RetweetCount
     * RetweetOriginalArticle
     */
    fun addArticleRetweetInfo() {
        addAll(listOf(NeedInfo.RetweetCount, NeedInfo.RetweetOriginalArticle))
    }

    /**
     * 加入問答文章相關的附加資訊
     * AskInfo
     * AuthorInfo
     */
    fun addQuestionArticleInfo() {
        addAll(listOf(NeedInfo.AskInfo, NeedInfo.AuthorInfo))
    }

    /**
     * 加入文章互動的附加資訊
     * DislikeCount
     * ClickCount
     * LikeCount
     * CollectionCount
     * ReplyCount
     * RetweetCount
     * TipInfo
     */
    fun addArticleInteractAppendInfo() {
        addAll(
            listOf(
                NeedInfo.DislikeCount,
                NeedInfo.ClickCount,
                NeedInfo.LikeCount,
                NeedInfo.ReplyCount,
                NeedInfo.RetweetCount,
                NeedInfo.TipInfo
            )
        )
    }

    /**
     * 加入文章觀看者相關的附加資訊
     * IsCollected
     * IsFollowedAuthor
     * IsLikedAndIsDisliked
     * TipInfo
     *
     */
    fun addArticleViewerInfo() {
        addAll(
            listOf(
                NeedInfo.IsCollected,
                NeedInfo.IsFollowedAuthor,
                NeedInfo.IsLikedAndIsDisliked,
                NeedInfo.TipInfo
            )
        )
    }

    /**
     * 加入文章內容相關的附加資訊
     * Title
     * ArticleFrom
     * RetweetOriginalArticle
     * ContentImage
     * ContentVideoUrl
     * StockTags
     * SourceUrl
     *
     */
    fun addArticleContentAppendInfo() {
        addAll(
            listOf(
                NeedInfo.Title,
                NeedInfo.ArticleFrom,
                NeedInfo.RetweetOriginalArticle,
                NeedInfo.ContentImage,
                NeedInfo.ContentVideoUrl,
                NeedInfo.StockTags,
                NeedInfo.SourceUrl
            )
        )
    }

    /**
     * 取得所有文章附加資訊加總的結果
     *
     */
    fun getCombinedResult() = needInfo.distinct().sumBy { it.value }
}