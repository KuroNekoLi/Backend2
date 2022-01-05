package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

class CellphoneQueryBuilder internal constructor() {
    private val queryParams: CellphoneQueryParams = CellphoneQueryParams()

    val code: CellphoneQueryBuilder
        get() {
            queryParams.code = MemberProfileField.CODE
            return this
        }

    fun isNeedCode(value: Boolean): CellphoneQueryBuilder {
        queryParams.code = if (value) {
            MemberProfileField.CODE
        } else {
            null
        }
        return this
    }

    val number: CellphoneQueryBuilder
        get() {
            queryParams.number = MemberProfileField.NUMBER
            return this
        }

    fun isNeedNumber(value: Boolean): CellphoneQueryBuilder {
        queryParams.number = if (value) {
            MemberProfileField.NUMBER
        } else {
            null
        }
        return this
    }

    internal fun build(): CellphoneQueryParams? {
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