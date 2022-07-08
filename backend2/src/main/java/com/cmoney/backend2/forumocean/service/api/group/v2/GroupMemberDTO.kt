package com.cmoney.backend2.forumocean.service.api.group.v2

data class GroupMemberDTO(
    val image: String,
    val memberId: Int,
    val nickName: String,
    val roleIds: List<Int>
)