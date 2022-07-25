package com.cmoney.backend2.forumocean.service.api.group.v2

enum class Role(val value: String) {
    OWNER("owner"),
    MANAGER("manager"),
    NORMAL_MEMBER("normalMember"),
    NON_MEMBER("nonMember")
}