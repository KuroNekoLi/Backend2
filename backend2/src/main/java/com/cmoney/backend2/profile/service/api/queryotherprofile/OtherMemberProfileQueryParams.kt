package com.cmoney.backend2.profile.service.api.queryotherprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

/**
 * 其他會員資訊請求要求
 *
 * 預設會要求[id]
 */
internal class OtherMemberProfileQueryParams {
    val id: MemberProfileField = MemberProfileField.ID
    var nickname: MemberProfileField? = null
    var bio: MemberProfileField? = null
    var image: MemberProfileField? = null
    var isBindingCellphone: MemberProfileField? = null
    var communityRoles: MemberProfileField? = null
    var level: MemberProfileField? = null
    var badges: MemberProfileField? = null
}