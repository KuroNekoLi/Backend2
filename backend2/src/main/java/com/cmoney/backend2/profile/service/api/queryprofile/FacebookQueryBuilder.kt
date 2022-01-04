package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

class FacebookQueryBuilder internal constructor(){
    private val queryParams = FacebookQueryParams()

    val fbId: FacebookQueryBuilder
        get() {
            queryParams.fbId = MemberProfileField.FB_ID
            return this
        }

    fun isNeedId(value: Boolean): FacebookQueryBuilder {
        queryParams.fbId = if (value) {
            MemberProfileField.FB_ID
        } else {
            null
        }
        return this
    }

    val email: FacebookQueryBuilder
        get() {
            queryParams.email = MemberProfileField.EMAIL
            return this
        }

    fun isNeedEmail(value: Boolean): FacebookQueryBuilder {
        queryParams.email = if (value) {
            MemberProfileField.EMAIL
        } else {
            null
        }
        return this
    }

    val name: FacebookQueryBuilder
        get() {
            queryParams.name = MemberProfileField.NAME
            return this
        }

    fun isNeedName(value: Boolean): FacebookQueryBuilder {
        queryParams.name = if (value) {
            MemberProfileField.NAME
        } else {
            null
        }
        return this
    }

    internal fun build(): FacebookQueryParams? {
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