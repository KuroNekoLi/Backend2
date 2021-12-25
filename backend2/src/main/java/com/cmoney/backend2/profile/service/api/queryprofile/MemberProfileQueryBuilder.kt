package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

class MemberProfileQueryBuilder internal constructor() {
    private val queryParams = MemberProfileQueryParams()
    private val accountQueryBuilder = AccountQueryBuilder()
    private val levelInfoQueryBuilder = LevelInfoQueryBuilder()
    private val badgesQueryBuilder = BadgesQueryBuilder()

    val pCoin: MemberProfileQueryBuilder
        get() {
            queryParams.pCoin = MemberProfileField.P_COIN
            return this
        }

    fun isNeedPCoin(value: Boolean): MemberProfileQueryBuilder {
        queryParams.pCoin = if (value) {
            MemberProfileField.P_COIN
        } else {
            null
        }
        return this
    }

    val name: MemberProfileQueryBuilder
        get() {
            queryParams.name = MemberProfileField.NAME
            return this
        }

    fun isNeedName(value: Boolean): MemberProfileQueryBuilder {
        queryParams.name = if (value) {
            MemberProfileField.NICKNAME
        } else {
            null
        }
        return this
    }

    val nickname: MemberProfileQueryBuilder
        get() {
            queryParams.nickName = MemberProfileField.NICKNAME
            return this
        }

    fun isNeedNickName(value: Boolean): MemberProfileQueryBuilder {
        queryParams.nickName = if (value) {
            MemberProfileField.NICKNAME
        } else {
            null
        }
        return this
    }

    val gender: MemberProfileQueryBuilder
        get() {
            queryParams.gender = MemberProfileField.GENDER
            return this
        }

    fun isNeedGender(value: Boolean): MemberProfileQueryBuilder {
        queryParams.gender = if (value) {
            MemberProfileField.GENDER
        } else {
            null
        }
        return this
    }

    val birthday: MemberProfileQueryBuilder
        get() {
            queryParams.birthday = MemberProfileField.BIRTHDAY
            return this
        }

    fun isNeedBirthday(value: Boolean): MemberProfileQueryBuilder {
        queryParams.birthday = if (value) {
            MemberProfileField.BIRTHDAY
        } else {
            null
        }
        return this
    }

    val address: MemberProfileQueryBuilder
        get() {
            queryParams.address = MemberProfileField.ADDRESS
            return this
        }

    fun isNeedAddress(value: Boolean): MemberProfileQueryBuilder {
        queryParams.address = if (value) {
            MemberProfileField.ADDRESS
        } else {
            null
        }
        return this
    }

    val signupDate: MemberProfileQueryBuilder
        get() {
            queryParams.signupDate = MemberProfileField.SIGNUP_DATE
            return this
        }

    fun isNeedSignupDate(value: Boolean): MemberProfileQueryBuilder {
        queryParams.signupDate = if (value) {
            MemberProfileField.SIGNUP_DATE
        } else {
            null
        }
        return this
    }

    val bio: MemberProfileQueryBuilder
        get() {
            queryParams.bio = MemberProfileField.BIO
            return this
        }

    fun isNeedBio(value: Boolean): MemberProfileQueryBuilder {
        queryParams.bio = if (value) {
            MemberProfileField.BIO
        } else {
            null
        }
        return this
    }

    val contactEmail: MemberProfileQueryBuilder
        get() {
            queryParams.contactEmail = MemberProfileField.CONTACT_EMAIL
            return this
        }

    fun isNeedContactEmail(value: Boolean): MemberProfileQueryBuilder {
        queryParams.contactEmail = if (value) {
            MemberProfileField.CONTACT_EMAIL
        } else {
            null
        }
        return this
    }

    val image: MemberProfileQueryBuilder
        get() {
            queryParams.image = MemberProfileField.IMAGE
            return this
        }

    fun isNeedImage(value: Boolean): MemberProfileQueryBuilder {
        queryParams.image = if (value) {
            MemberProfileField.IMAGE
        } else {
            null
        }
        return this
    }

    val city: MemberProfileQueryBuilder
        get() {
            queryParams.city = MemberProfileField.CITY
            return this
        }

    fun isNeedCity(value: Boolean): MemberProfileQueryBuilder {
        queryParams.city = if (value) {
            MemberProfileField.CITY
        } else {
            null
        }
        return this
    }

    val education: MemberProfileQueryBuilder
        get() {
            queryParams.education = MemberProfileField.EDUCATION
            return this
        }

    fun isNeedEducation(value: Boolean): MemberProfileQueryBuilder {
        queryParams.education = if (value) {
            MemberProfileField.EDUCATION
        } else {
            null
        }
        return this
    }

    val profession: MemberProfileQueryBuilder
        get() {
            queryParams.profession = MemberProfileField.PROFESSION
            return this
        }

    fun isNeedProfession(value: Boolean): MemberProfileQueryBuilder {
        queryParams.profession = if (value) {
            MemberProfileField.PROFESSION
        } else {
            null
        }
        return this
    }

    val investmentExperience: MemberProfileQueryBuilder
        get() {
            queryParams.investmentExperience = MemberProfileField.INVESTMENT_EXPERIENCE
            return this
        }

    fun isNeedInvestmentExperience(value: Boolean): MemberProfileQueryBuilder {
        queryParams.investmentExperience = if (value) {
            MemberProfileField.INVESTMENT_EXPERIENCE
        } else {
            null
        }
        return this
    }

    val investmentProperty: MemberProfileQueryBuilder
        get() {
            queryParams.investmentProperty = MemberProfileField.INVESTMENT_PROPERTY
            return this
        }

    fun isNeedInvestmentProperty(value: Boolean): MemberProfileQueryBuilder {
        queryParams.investmentProperty = if (value) {
            MemberProfileField.INVESTMENT_PROPERTY
        } else {
            null
        }
        return this
    }

    val investmentTools: MemberProfileQueryBuilder
        get() {
            queryParams.investmentTools = MemberProfileField.INVESTMENT_TOOLS
            return this
        }

    fun isNeedInvestmentTools(value: Boolean): MemberProfileQueryBuilder {
        queryParams.investmentTools = if (value) {
            MemberProfileField.INVESTMENT_TOOLS
        } else {
            null
        }
        return this
    }

    val customerId: MemberProfileQueryBuilder
        get() {
            queryParams.customerId = MemberProfileField.CUSTOMER_ID
            return this
        }

    fun isNeedCustomerId(value: Boolean): MemberProfileQueryBuilder {
        queryParams.customerId = if (value) {
            MemberProfileField.CUSTOMER_ID
        } else {
            null
        }
        return this
    }

    val account: MemberProfileQueryBuilder.(AccountQueryBuilder.() -> AccountQueryBuilder) -> MemberProfileQueryBuilder
        get() {
            return { block ->
                accountQueryBuilder.block()
                this
            }
        }

    val levelInfo: MemberProfileQueryBuilder.(LevelInfoQueryBuilder.() -> LevelInfoQueryBuilder) -> MemberProfileQueryBuilder
        get() {
            return { block ->
                levelInfoQueryBuilder.block()
                this
            }
        }

    val badges: MemberProfileQueryBuilder.(BadgesQueryBuilder.() -> BadgesQueryBuilder) -> MemberProfileQueryBuilder
        get() {
            return { block ->
                badgesQueryBuilder.block()
                this
            }
        }

    internal fun build(): MemberProfileQueryParams? {
        queryParams.apply {
            account = accountQueryBuilder.build()
            levelInfo = levelInfoQueryBuilder.build()
            badges = badgesQueryBuilder.build()
        }
        return if (isParamsDefault()) {
            null
        } else {
            queryParams
        }
    }

    private fun isParamsDefault(): Boolean {
        return queryParams.isDefault()
    }
}