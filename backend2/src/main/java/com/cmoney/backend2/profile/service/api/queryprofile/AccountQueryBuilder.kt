package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

class AccountQueryBuilder internal constructor() {
    private val queryParams: AccountQueryParams = AccountQueryParams()
    private val cellphoneQueryBuilder = CellphoneQueryBuilder()
    private val facebookQueryBuilder = FacebookQueryBuilder()

    val email: AccountQueryBuilder
        get() {
            queryParams.email = MemberProfileField.EMAIL
            return this
        }

    fun isNeedEmail(value: Boolean): AccountQueryBuilder {
        queryParams.email = if (value) {
            MemberProfileField.EMAIL
        } else {
            null
        }
        return this
    }

    val cellphone: AccountQueryBuilder.(CellphoneQueryBuilder.() -> CellphoneQueryBuilder) -> AccountQueryBuilder
        get() {
            return { block ->
                cellphoneQueryBuilder.block()
                this
            }
        }

    val facebook: AccountQueryBuilder.(FacebookQueryBuilder.() -> FacebookQueryBuilder) -> AccountQueryBuilder
        get() {
            return { block ->
                facebookQueryBuilder.block()
                this
            }
        }

    val appleId: AccountQueryBuilder
        get() {
            queryParams.appleId = MemberProfileField.APPLE_ID
            return this
        }

    fun isNeedAppleId(value: Boolean): AccountQueryBuilder {
        queryParams.appleId = if (value) {
            MemberProfileField.APPLE_ID
        } else {
            null
        }
        return this
    }

    val guestId: AccountQueryBuilder
        get() {
            queryParams.guestId = MemberProfileField.GUEST_ID
            return this
        }

    fun isNeedAccountGuestId(value: Boolean): AccountQueryBuilder {
        queryParams.guestId = if (value) {
            MemberProfileField.GUEST_ID
        } else {
            null
        }
        return this
    }


    internal fun build(): AccountQueryParams? {
        queryParams.apply {
            facebook = facebookQueryBuilder.build()
            cellphone = cellphoneQueryBuilder.build()
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