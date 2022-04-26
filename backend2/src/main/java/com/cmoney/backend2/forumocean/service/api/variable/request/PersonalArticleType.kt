package com.cmoney.backend2.forumocean.service.api.variable.request

/**
 * 文章類型
 */
enum class PersonalArticleType(val articleType: String) {
    /**
     * 專欄文章
     */
    COLUMNIST("columnist"),

    /**
     * 筆記文
     */
    NOTE("note")
}