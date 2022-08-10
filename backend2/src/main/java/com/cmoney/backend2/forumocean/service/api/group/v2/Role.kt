package com.cmoney.backend2.forumocean.service.api.group.v2

/**
 * 角色
 */
enum class Role(val value: String) {
    /**
     * 社長
     */
    OWNER("owner"),

    /**
     * 幹部
     */
    MANAGER("manager"),

    /**
     * 一般社員
     */
    NORMAL_MEMBER("normalMember"),

    /**
     * 非社員
     */
    NON_MEMBER("nonMember")
}