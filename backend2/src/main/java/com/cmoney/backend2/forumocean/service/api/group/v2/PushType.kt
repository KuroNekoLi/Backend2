package com.cmoney.backend2.forumocean.service.api.group.v2

/**
 * 推播設定種類
 */
enum class PushType(val value: String) {
    /**
     * 任何人的行為都會發送推播
     */
    ALL("all"),

    /**
     * 只有幹部及社長的行為會發送推播
     */
    ADMIN("admin"),

    /**
     * 關閉推播
     */
    NONE("none")
}