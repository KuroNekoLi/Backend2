package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

/**
 * Member profile query
 */
internal class MemberProfileQueryParams internal constructor() {
    var signupDate: MemberProfileField? = null
    var name: MemberProfileField? = null
    var nickname: MemberProfileField? = null
    var gender: MemberProfileField? = null
    var birthday: MemberProfileField? = null
    var address: MemberProfileField? = null
    var bio: MemberProfileField? = null
    var image: MemberProfileField? = null
    var city: MemberProfileField? = null
    var contactEmail: MemberProfileField? = null
    var education: MemberProfileField? = null
    var profession: MemberProfileField? = null
    var investmentExperience: MemberProfileField? = null
    var investmentProperty: MemberProfileField? = null
    var investmentTools: MemberProfileField? = null
    var customerId: MemberProfileField? = null
    var pCoin: MemberProfileField? = null
    var account: AccountQueryParams? = null
    var levelInfo: LevelInfoQueryParams? = null
    var badges: BadgesQueryParams? = null

    fun isDefault(): Boolean {
        return pCoin == null &&
            name == null &&
            nickname == null &&
            gender == null &&
            birthday == null &&
            address == null &&
            signupDate == null &&
            bio == null &&
            contactEmail == null &&
            image == null &&
            city == null &&
            education == null &&
            profession == null &&
            investmentExperience == null &&
            investmentProperty == null &&
            investmentTools == null &&
            customerId == null &&
            account == null &&
            levelInfo == null &&
            account == null &&
            levelInfo == null &&
            badges == null
    }
}
