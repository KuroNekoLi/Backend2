package com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody

data class ArticleAppendClubParam(
    /**
     * 社團頻道編號 (發文在社團用)
     */
    val clubChannelId : Long,

    /**
     * 是否以社團名義發文
     */
    val isUseClubToPost : Boolean)