package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal class BadgesQueryParams internal constructor() {
    val parentField: MemberProfileField = MemberProfileField.BADGES
    var badgeId: MemberProfileField? = null
    var isEquipped: MemberProfileField? = null
    var hasRead: MemberProfileField? = null

    fun isDefault(): Boolean {
        return badgeId == null &&
            isEquipped == null &&
            hasRead == null
    }
}
