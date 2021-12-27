package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

class BadgesQueryBuilder internal constructor(){
    private val queryParams: BadgesQueryParams = BadgesQueryParams()

    val badgeId: BadgesQueryBuilder
        get() {
            queryParams.badgeId = MemberProfileField.BADGE_ID
            return this
        }

    fun isNeedId(value: Boolean): BadgesQueryBuilder {
        queryParams.badgeId = if (value) {
            MemberProfileField.BADGE_ID
        } else {
            null
        }
        return this
    }

    val isEquipped: BadgesQueryBuilder
        get() {
            queryParams.isEquipped = MemberProfileField.IS_EQUIPPED
            return this
        }

    fun isNeedIsEquipped(value: Boolean): BadgesQueryBuilder {
        queryParams.isEquipped = if (value) {
            MemberProfileField.IS_EQUIPPED
        } else {
            null
        }
        return this
    }

    val hasRead: BadgesQueryBuilder
        get() {
            queryParams.hasRead = MemberProfileField.HAS_READ
            return this
        }

    fun isNeedHasRead(value: Boolean): BadgesQueryBuilder {
        queryParams.hasRead = if (value) {
            MemberProfileField.HAS_READ
        } else {
            null
        }
        return this
    }

    internal fun build(): BadgesQueryParams? {
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