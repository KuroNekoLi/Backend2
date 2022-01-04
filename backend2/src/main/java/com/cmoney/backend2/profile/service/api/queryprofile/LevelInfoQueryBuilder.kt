package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

class LevelInfoQueryBuilder internal constructor() {
    private val queryParams: LevelInfoQueryParams = LevelInfoQueryParams()

    val exp: LevelInfoQueryBuilder
        get() {
            queryParams.exp = MemberProfileField.EXP
            return this
        }

    fun isNeedExp(value: Boolean): LevelInfoQueryBuilder {
        queryParams.exp = if (value) {
            MemberProfileField.EXP
        } else {
            null
        }
        return this
    }

    val level: LevelInfoQueryBuilder
        get() {
            queryParams.level = MemberProfileField.LEVEL
            return this
        }

    fun isNeedLevel(value: Boolean): LevelInfoQueryBuilder {
        queryParams.level = if (value) {
            MemberProfileField.LEVEL
        } else {
            null
        }
        return this
    }

    val levelExp: LevelInfoQueryBuilder
        get() {
            queryParams.levelExp = MemberProfileField.LEVEL_EXP
            return this
        }

    fun isNeedLevelExp(value: Boolean): LevelInfoQueryBuilder {
        queryParams.levelExp = if (value) {
            MemberProfileField.LEVEL_EXP
        } else {
            null
        }
        return this
    }

    val levelExpToNext: LevelInfoQueryBuilder
        get() {
            queryParams.levelExpToNext = MemberProfileField.LEVEL_EXP_TO_NEXT
            return this
        }

    fun isNeedLevelExpToNext(value: Boolean): LevelInfoQueryBuilder {
        queryParams.levelExpToNext = if (value) {
            MemberProfileField.LEVEL_EXP_TO_NEXT
        } else {
            null
        }
        return this
    }

    internal fun build(): LevelInfoQueryParams? {
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