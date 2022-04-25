package com.cmoney.backend2.forumocean.service.api.channel.channelname.definechannelnamebuilder

import com.cmoney.backend2.forumocean.service.api.channel.channelname.IChannelNameBuilder

sealed class DefineChannelName : IChannelNameBuilder {

    /**
     * 自定義要取得的頻道名稱
     *
     * @property customString 自定義名稱
     */
    data class CustomChannel(val customString: String) : DefineChannelName() {
        override fun create(): String {
            return customString
        }
    }


    /**
     *  指定使用者的問答頻道  不填memberId 為 不分類問答頻道
     *
     * @property memberId 會員Id
     */
    data class MemberQuestion(val memberId: Long? = null) : DefineChannelName() {
        override fun create(): String {
            return if (memberId == null) {
                "Member-Question"
            } else {
                "Member-Question.$memberId"
            }
        }
    }

    /**
     * 最熱指定使用者的問答頻道  不填memberId 為 最熱不分類問答頻道
     *
     * @property memberId 會員Id
     */
    data class PopularMemberQuestion(val memberId: Long? = null) : DefineChannelName() {
        override fun create(): String {
            return if (memberId == null) {
                "Popular-Member-Question"
            } else {
                "Popular-Member-Question.$memberId"
            }
        }
    }

    /**
     * 指定使用者的一般頻道
     *
     * @property memberId 會員Id
     */
    data class Member(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Member.$memberId"
        }
    }

    /**
     * 最熱 指定使用者的一般頻道
     *
     * @property memberId 會員Id
     */
    data class PopularMember(val memberId: Long? = null) : DefineChannelName() {
        override fun create(): String {
            return if (memberId == null) {
                "Popular-Member.$memberId"
            } else {
                "Popular-Member.$memberId"
            }
        }
    }

    /**
     * 指定被轉推文章的頻道
     *
     * @property articleId 轉推原文Id
     */
    data class Shared(val articleId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Shared.$articleId"
        }
    }

    /**
     * 指定使用者收藏的一般頻道
     *
     * @property memberId 會員Id
     */
    data class Collection(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Collection.$memberId"
        }
    }

    /**
     * 指定使用者收藏的問答頻道
     *
     * @property memberId 會員Id
     */
    data class CollectionQuestion(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Collection-Question.$memberId"
        }
    }

    /**
     * 指定社團頻道
     *
     * @property groupId 社團Id
     */
    data class Group(val groupId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Group.$groupId"
        }
    }

    /**
     * 最熱指定社團頻道
     *
     * @property groupId 社團Id
     */
    data class PopularGroup(val groupId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Popular-Group.$groupId"
        }
    }

    /**
     * 指定社團置頂頻道
     *
     * @property groupId 社團Id
     */
    data class GroupPin(val groupId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Group-Pin.$groupId"
        }
    }

    /**
     * 不填入股票代號 為 指定商品的一般頻道
     * 填入股票代號 為 指定股票的一般頻道
     * @see CommodityType 可參考此類別的定義
     *
     * @property commodityType 商品類型
     * @property stockId
     */
    data class Commodity(val commodityType: String, val stockId: String? = null) :
        DefineChannelName() {
        override fun create(): String {
            return if (stockId == null) {
                commodityType
            } else {
                "$commodityType.$stockId"
            }
        }
    }

    /**
     * 不填入股票代號 為 最熱指定商品的一般頻道
     * 填入股票代號 為 最熱指定股票的一般頻道
     * @see CommodityType 可參考此類別的定義
     *
     * @property commodityType 商品類型
     */
    data class PopularCommodity(val commodityType: String, val stockId: String? = null) :
        DefineChannelName() {
        override fun create(): String {
            return if (stockId == null) {
                "Popular-$commodityType"
            } else {
                "Popular-$commodityType.$stockId"
            }
        }
    }

    /**
     * 不填入股票代號 為 指定商品的問答頻道
     * 填入股票代號 為 指定股票的問答頻道
     *
     * @see CommodityType 可參考此類別的定義
     *
     * @property commodityType 商品類型
     */
    data class QuestionCommodity(val commodityType: String, val stockId: String? = null) :
        DefineChannelName() {
        override fun create(): String {
            return if (stockId == null) {
                "Question-$commodityType"
            } else {
                "Question-$commodityType.$stockId"
            }
        }
    }

    /**
     * 不填入股票代號 為 最熱指定商品的問答頻道
     * 填入股票代號 為 最熱指定股票的問答頻道
     *
     * @see CommodityType 可參考此類別的定義
     *
     * @property commodityType 商品類型
     */
    data class PopularQuestionCommodity(val commodityType: String, val stockId: String? = null) :
        DefineChannelName() {
        override fun create(): String {
            return if (stockId == null) {
                "Popular-Question-$commodityType"
            } else {
                "Popular-Question-$commodityType.$stockId"
            }
        }
    }

    /**
     * 指定股票的訊號頻道
     *
     * @see CommodityType 可參考此類別的定義
     *
     * @property commodityType 商品類型
     */
    data class BotCommodity(val commodityType: String, val stockId: String) : DefineChannelName() {
        override fun create(): String {
            return "Bot-$commodityType.$stockId"
        }
    }

    /**
     * 最熱指定股票的訊號頻道
     *
     * @see CommodityType 可參考此類別的定義
     *
     * @property commodityType 商品類型
     */
    data class PopularBotCommodity(val commodityType: String, val stockId: String) :
        DefineChannelName() {
        override fun create(): String {
            return "Popular-Bot-$commodityType.$stockId"
        }
    }

    /**
     * 指定股票的新聞頻道
     *
     * @property commodityType 商品類型
     * @property stockId 股票代號
     */
    data class NewsCommodity(val commodityType: String, val stockId: String) : DefineChannelName() {
        override fun create(): String {
            return "News-$commodityType.$stockId"
        }
    }

    /**
     * 最熱指定股票的新聞頻道
     *
     * @property commodityType 商品類型
     * @property stockId 股票代號
     */
    data class PopularNewsCommodity(val commodityType: String, val stockId: String) :
        DefineChannelName() {
        override fun create(): String {
            return "Popular-News-$commodityType.$stockId"
        }
    }

    /**
     * 指定AppId及股票頻道
     *
     * @property appId App Id
     * @property commodityType 商品類型
     * @property stockId 股票代號
     */
    data class AppCommodity(val appId: Int, val commodityType: String, val stockId: String) :
        DefineChannelName() {
        override fun create(): String {
            return "$appId-$commodityType.$stockId"
        }
    }

    /**
     * 最熱指定AppId及股票頻道
     *
     * @property appId App Id
     * @property commodityType 商品類型
     * @property stockId 股票代號
     */
    data class PopularAppCommodity(val appId: Int, val commodityType: String, val stockId: String) :
        DefineChannelName() {
        override fun create(): String {
            return "Popular-$appId-$commodityType.$stockId"
        }
    }

    /**
     * 指定訊號頻道
     *
     * @property botId 訊號頻道Id
     */
    data class Bot(val botId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Bot.$botId"
        }
    }

    /**
     * 最熱指定訊號頻道
     *
     * @property botId 訊號頻道Id
     */
    data class PopularBot(val botId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Popular-Bot.$botId"
        }
    }

    /**
     * 指定新聞頻道
     *
     * @property newsId 新聞頻道Id
     */
    data class News(val newsId: Long) : DefineChannelName() {
        override fun create(): String {
            return "News.$newsId"
        }
    }

    /**
     * 最熱指定新聞頻道
     *
     * @property newsId 新聞頻道Id
     */
    data class PopularNews(val newsId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Popular-News.$newsId"
        }
    }

    /**
     * 指定標籤頻道
     *
     * @property tagName 標籤名稱
     */
    data class Topic(val tagName: String) : DefineChannelName() {
        override fun create(): String {
            return "Topic.$tagName"
        }
    }

    /**
     * 最熱指定標籤頻道
     *
     * @property tagName 標籤名稱
     */
    data class PopularTopic(val tagName: String) : DefineChannelName() {
        override fun create(): String {
            return "Popular-Topic.$tagName"
        }
    }

    /**
     * 指定股票及標籤頻道
     *
     * @property commodityType 商品類型
     * @property stockId 股票代號
     * @property tagName 標籤名稱
     */
    data class CommodityTopic(val commodityType: String, val stockId: String, val tagName: String) :
        DefineChannelName() {
        override fun create(): String {
            return "$commodityType.$stockId-Topic.$tagName"
        }
    }

    /**
     * 最熱指定股票及標籤頻道
     *
     * @property commodityType 商品類型
     * @property stockId 股票代號
     * @property tagName 標籤名稱
     */
    data class PopularCommodityTopic(
        val commodityType: String,
        val stockId: String,
        val tagName: String
    ) : DefineChannelName() {
        override fun create(): String {
            return "Popular-$commodityType.$stockId-Topic.$tagName"
        }
    }

    /**
     * 個人筆記頻道
     *
     * @property memberId 會員id
     */
    data class MemberNote(val memberId: Long): DefineChannelName() {
        override fun create(): String {
            return "Note.$memberId"
        }
    }

    data class MemberColumnist(val memberId: Long): DefineChannelName() {
        override fun create(): String {
            return "Member-Columnist.$memberId"
        }
    }

    object Columnist: DefineChannelName() {
        override fun create(): String {
            return "Columnist"
        }
    }

    /**
     * 指定使用者的一般頻道（美股專用)
     *
     * @property memberId 會員id
     */
    data class MemberUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Member.$memberId-USStock"
        }
    }

    /**
     * 最熱指定使用者的一般頻道（美股專用)
     *
     * @property memberId 會員id
     */
    data class PopularMemberUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Popular-Member.$memberId-USStock"
        }
    }

    /**
     * 指定使用者的問答頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class MemberQuestionUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Member-Question.$memberId-USStock"
        }
    }

    /**
     * 最熱指定使用者的問答頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class PopularMemberQuestionUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Popular-Member-Question.$memberId-USStock"
        }
    }

    /**
     * 指定使用者的一般及問答頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class MemberAllUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Member-All.$memberId-USStock"
        }
    }

    /**
     * 最熱指定使用者的一般及問答頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class PopularMemberAllUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Popular-Member-All.$memberId-USStock"
        }
    }

    /**
     * 指定使用者收藏的一般頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class CollectionUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Collection.$memberId-USStock"
        }
    }

    /**
     * 指定使用者收藏的問答頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class CollectionQuestionUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Collection-Question.$memberId-USStock"
        }
    }

    /**
     * 指定使用者收藏的一般及問答頻道(美股專用)
     *
     * @property memberId 會員id
     */
    data class CollectionAllUsStock(val memberId: Long) : DefineChannelName() {
        override fun create(): String {
            return "Collection-All.$memberId-USStock"
        }
    }
}
