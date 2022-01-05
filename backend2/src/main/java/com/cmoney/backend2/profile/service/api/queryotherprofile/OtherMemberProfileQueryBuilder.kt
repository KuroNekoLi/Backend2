package com.cmoney.backend2.profile.service.api.queryotherprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

/**
 * 其他會員資訊欄位請求設定物件
 * 提供設定[OtherMemberProfileQueryParams]的方法
 *
 * 不提供isNeedId的設定
 */
class OtherMemberProfileQueryBuilder internal constructor() {
    private val queryParams = OtherMemberProfileQueryParams()

    val id: OtherMemberProfileQueryBuilder
        get() {
            return this
        }

    val bio: OtherMemberProfileQueryBuilder
        get() {
            queryParams.bio = MemberProfileField.BIO
            return this
        }

    fun isNeedBio(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.bio = if (value) {
            MemberProfileField.BIO
        } else {
            null
        }
        return this
    }

    val nickname: OtherMemberProfileQueryBuilder
        get() {
            queryParams.nickname = MemberProfileField.NICKNAME
            return this
        }

    fun isNeedNickName(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.nickname = if (value) {
            MemberProfileField.NICKNAME
        } else {
            null
        }
        return this
    }

    val image: OtherMemberProfileQueryBuilder
        get() {
            queryParams.image = MemberProfileField.IMAGE
            return this
        }

    fun isNeedImage(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.image = if (value) {
            MemberProfileField.IMAGE
        } else {
            null
        }
        return this
    }

    val isBindingCellphone: OtherMemberProfileQueryBuilder
        get() {
            queryParams.isBindingCellphone = MemberProfileField.IS_BINDING_CELLPHONE
            return this
        }

    fun isNeedIsBindingCellphone(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.isBindingCellphone = if (value) {
            MemberProfileField.IS_BINDING_CELLPHONE
        } else {
            null
        }
        return this
    }

    val communityRoles: OtherMemberProfileQueryBuilder
        get() {
            queryParams.communityRoles = MemberProfileField.COMMUNITY_ROLES
            return this
        }

    fun isNeedCommunityRoles(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.communityRoles = if (value) {
            MemberProfileField.COMMUNITY_ROLES
        } else {
            null
        }
        return this
    }

    val level: OtherMemberProfileQueryBuilder
        get() {
            queryParams.level = MemberProfileField.LEVEL
            return this
        }

    fun isNeedLevel(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.level = if (value) {
            MemberProfileField.LEVEL
        } else {
            null
        }
        return this
    }

    val badges: OtherMemberProfileQueryBuilder
        get() {
            queryParams.badges = MemberProfileField.BADGES
            return this
        }

    fun isNeedBadges(value: Boolean): OtherMemberProfileQueryBuilder {
        queryParams.badges = if (value) {
            MemberProfileField.BADGES
        } else {
            null
        }
        return this
    }

    internal fun build(): OtherMemberProfileQueryParams {
        return queryParams
    }
}