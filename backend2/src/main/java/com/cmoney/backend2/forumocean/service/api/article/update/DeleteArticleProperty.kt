package com.cmoney.backend2.forumocean.service.api.article.update

/**
 * 可刪除欄位
 *
 */
enum class DeleteArticleProperty(val value : String) {

    /**
     * 多媒體資訊
     *
     */
    MultiMedia("multiMedia"),

    /**
     * 標籤
     *
     */
    Topics("topics"),

    /**
     *OG
     */
    OpenGraph("openGraph")
}