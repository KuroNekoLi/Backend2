package com.cmoney.backend2.forumocean.service.api.channel.channelname

sealed class SystemClassification : ConvertChannelName{

    class Member(private val memberId : Long?) : SystemClassification() {
        override fun getChannelName(): String? {
            return if (memberId != null){
                "Member.$memberId"
            }else{
                "Member"
            }
        }
    }

    class MemberCollection(private val channelId : Long?) : SystemClassification(){
        override fun getChannelName(): String? {
            return if (channelId != null){
                "MemberCollection.$channelId"
            }else{
                "MemberCollection"
            }
        }
    }

    class Group(private val groupId : Long?) : SystemClassification(){
        override fun getChannelName(): String? {
            return if (groupId != null){
                "Group.$groupId"
            }else{
                "Group"
            }
        }
    }

    class SharedArticle(val sharedArticleId : Long?) : SystemClassification(){
        override fun getChannelName(): String? {
            return if (sharedArticleId != null){
                "SharedArticle.$sharedArticleId"
            }else{
                "SharedArticle"
            }
        }
    }


    object Question : SystemClassification(){
        override fun getChannelName(): String? = "QandA"
    }

    class News(private val newsId : Long?) : SystemClassification(){
        override fun getChannelName(): String? {
            return if (newsId != null){
                "News.$newsId"
            }else{
                "News"
            }
        }
    }

    class Bot(private val botId : Long?) : SystemClassification() {
        override fun getChannelName(): String? {
            return if (botId != null){
                "Bot.$botId"
            }else{
                "Bot"
            }
        }
    }

    object None : SystemClassification(){
        override fun getChannelName(): String? = null
    }
}