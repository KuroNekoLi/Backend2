package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal class FacebookQueryParams internal constructor() {
    val parentField: MemberProfileField = MemberProfileField.FACEBOOK
    var fbId: MemberProfileField? = null
    var email: MemberProfileField? = null
    var name: MemberProfileField? = null

    fun isDefault(): Boolean {
        return fbId == null &&
            email == null &&
            name == null
    }
}
