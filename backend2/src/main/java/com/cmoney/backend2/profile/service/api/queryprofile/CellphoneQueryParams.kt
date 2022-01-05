package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal class CellphoneQueryParams internal constructor() {
    val parentField: MemberProfileField = MemberProfileField.CELLPHONE
    var code: MemberProfileField? = null
    var number: MemberProfileField? = null

    fun isDefault(): Boolean {
        return code == null && number == null
    }
}
