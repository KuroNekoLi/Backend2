package com.cmoney.backend2.ocean.service.api.variable

sealed class ChannelInfoOption(open val value : Int){

    /**
     * 社團頻道編號用的所需資訊
     *
     * @constructor
     *
     *
     * @param value
     */
    sealed class Club(value: Int) : ChannelInfoOption(value){
        /**
         * 頻道介紹
         */
        object Description : Club(1)

        /**
         * 頻道頭像
         */
        object Image : Club(2)

        /**
         * 社團資訊
         */
        object ClubInfo : Club(2048)

        /**
         * 會員在這個社團的資訊
         */
        object MemberClubInfo : Club(4096)

        /**
         * 瀏覽者在這個社團的資訊
         */
        object ViewerClubInfo : Club(32768)
    }

    /**
     * 會員頻道編號用的所需資訊
     *
     * @constructor
     *
     * @param value
     */
    sealed class Member(value: Int) : ChannelInfoOption(value){

        /**
         * 頻道介紹
         */
        object Description : Member(1)

        /**
         * 頻道頭像
         */
        object Image : Member(2)

        /**
         * 粉絲數(追蹤這個頻道的會員數)
         */
        object FansCount : Member(4)

        /**
         * 獲得讚(對頻道內的文章按讚數)
         */
        object LikeCount : Member(8)

        /**
         * 頻道的文章總數
         */
        object ArticleCount : Member(16)

        /**
         * 是否已追蹤此頻道
         */
        object IsFollowed : Member(32)

        /**
         * 頻道等級
         */
        object LevelInfo : Member(64)

        /**
         * 鑽石等級
         */
        object DiamondInfo : Member(128)

        /**
         * 會員解答數
         */
        object AnswersCount : Member(256)

        /**
         * 關注數(此會員頻道追蹤中的頻道數量)
         */
        object AttentionCount : Member(512)

        /**
         * 會員註冊時間 UnixTime(秒)
         */
        object MemberRegisterTime : Member(1024)

        /**
         * 評分資訊
         */
        object EvaluationInfo : Member(8192)

        /**
         * 瀏覽者對頻道的評價
         */
        object ViewerClubInfo : Member(16384)
    }

    /**
     * Rss 訊號頻道編號用的所需資訊
     *
     * @constructor
     *
     * @param value
     */
    sealed class RssSignal(value: Int) : ChannelInfoOption(value){

        /**
         * 頻道介紹
         */
        object Description : RssSignal(1)

        /**
         * 頻道頭像
         */
        object Image : RssSignal(2)

        /**
         * 粉絲數(追蹤這個頻道的會員數)
         */
        object FansCount : RssSignal(4)

        /**
         * 獲得讚(對頻道內的文章按讚數)
         */
        object LikeCount : RssSignal(8)

        /**
         * 頻道的文章總數
         */
        object ArticleCount : RssSignal(16)

        /**
         * 是否已追蹤此頻道
         */
        object IsFollowed : RssSignal(32)
    }

    /**
     * 其他頻道編號用的所需資訊
     *
     * @constructor
     *
     * @param value
     */
    sealed class Other(value: Int) : ChannelInfoOption(value){

        /**
         * 頻道介紹
         */
        object Description : Other(1)

        /**
         * 頻道頭像
         */
        object Image : Other(2)
    }
}