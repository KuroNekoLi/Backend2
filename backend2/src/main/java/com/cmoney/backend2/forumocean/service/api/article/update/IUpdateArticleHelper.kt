package com.cmoney.backend2.forumocean.service.api.article.update

interface IUpdateArticleHelper {
    /**
     * 建立修改文章的requestBody
     *
     * @return
     */
    fun create(): UpdateArticleRequestBody
}