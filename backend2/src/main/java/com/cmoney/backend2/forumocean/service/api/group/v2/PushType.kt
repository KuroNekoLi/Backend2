package com.cmoney.backend2.forumocean.service.api.group.v2

enum class PushType {
    /**
     * 任何人的行為都會發送推播
     */
    ALL,

    /**
     * 只有幹部及社長的行為會發送推播
     */
    ADMIN,

    /**
     * 關閉推播
     */
    NONE
}