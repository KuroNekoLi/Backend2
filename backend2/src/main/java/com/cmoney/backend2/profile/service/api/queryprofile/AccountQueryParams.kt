package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal class AccountQueryParams internal constructor() {
    val parentField: MemberProfileField = MemberProfileField.ACCOUNT
    var email: MemberProfileField? = null
    var cellphone: CellphoneQueryParams? = null
    var facebook: FacebookQueryParams? = null
    var appleId: MemberProfileField? = null
    var guestId: MemberProfileField? = null

    fun isDefault(): Boolean {
        return email == null &&
            cellphone == null &&
            facebook == null &&
            appleId == null &&
            guestId == null
    }
}
