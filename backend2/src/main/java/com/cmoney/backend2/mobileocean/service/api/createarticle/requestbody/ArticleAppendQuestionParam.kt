package com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody

data class ArticleAppendQuestionParam(
    /**
     * 發問要付出的購物金(提問文章必填)
     */
    val askBonus : Int,
    /**
     * 提問文章是否匿名
     */
    val isAnonymous : Boolean)