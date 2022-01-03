package com.cmoney.backend2.profile.service.api.getusergraphqlinfo

/**
 * 取多位使用者可以取用的欄位資訊
 *
 * @property value
 */
enum class UserGraphQLInfo(val value : String) {
    ID("id"),
    NickName("nickname"),
    Level("level"),
    Image("image"),
    Badge("badges"),
    Bio("bio"),
    IsBindingCellphone("isBindingCellphone"),
    CommunityRoles("communityRoles")
}