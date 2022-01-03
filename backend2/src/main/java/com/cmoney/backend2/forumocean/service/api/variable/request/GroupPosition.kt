package com.cmoney.backend2.forumocean.service.api.variable.request

enum class GroupPosition(val position : Int) {
    VISITOR(1),
    NON_MEMBER(2),
    NORMAL(4),
    MANAGEMENT(64),
    PRESIDENT(128)
}