package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal class LevelInfoQueryParams internal constructor() {
    val parentField: MemberProfileField = MemberProfileField.LEVEL_INFO
    var exp: MemberProfileField? = null
    var level: MemberProfileField? = null
    var levelExp: MemberProfileField? = null
    var levelExpToNext: MemberProfileField? = null

    fun isDefault(): Boolean {
        return exp == null &&
            level == null &&
            levelExp == null &&
            levelExpToNext == null
    }
}
