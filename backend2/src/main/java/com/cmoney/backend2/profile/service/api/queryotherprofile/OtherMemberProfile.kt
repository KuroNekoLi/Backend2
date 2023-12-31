package com.cmoney.backend2.profile.service.api.queryotherprofile

/**
 * Other member profile
 *
 * @property badges 裝備的徽章集合
 * @property bio 自我介紹
 * @property communityRoles 官方認證的角色
 * @property id 會員編號
 * @property image 頭像(URL)
 * @property isBindingCellphone 是否已綁定手機
 * @property level 等級
 * @property nickname 暱稱
 */
class OtherMemberProfile private constructor(
    private val params: OtherMemberProfileQueryParams,
    private val _badges: List<Int>?,
    private val _bio: String?,
    private val _communityRoles: List<Int>?,
    private val _id: Long?,
    private val _image: String?,
    private val _isBindingCellphone: Boolean?,
    private val _level: Int?,
    private val _nickname: String?
) {
    internal constructor(
        params: OtherMemberProfileQueryParams,
        raw: RawOtherMemberProfile
    ): this(
        params = params,
        _badges = raw.badges,
        _bio = raw.bio,
        _communityRoles = raw.communityRoles,
        _id = raw.id,
        _image = raw.image,
        _isBindingCellphone = raw.isBindingCellphone,
        _level = raw.level,
        _nickname = raw.nickname
    )

    val badges: List<Int>?
        get() {
            requireNotNull(params.badges) {
                "OtherMemberProfileQueryBuilder.badges not request"
            }
            return _badges
        }

    val bio: String?
        get() {
            requireNotNull(params.bio) {
                "OtherMemberProfileQueryBuilder.bio not request"
            }
            return _bio
        }

    val communityRoles: List<Int>?
        get() {
            requireNotNull(params.communityRoles) {
                "OtherMemberProfileQueryBuilder.communityRoles not request"
            }
            return _communityRoles
        }

    val id: Long?
        get() {
            requireNotNull(params.id) {
                "OtherMemberProfileQueryBuilder.id not request"
            }
            return _id
        }

    val image: String?
        get() {
            requireNotNull(params.image) {
                "OtherMemberProfileQueryBuilder.image not request"
            }
            return _image
        }

    val isBindingCellphone: Boolean?
        get() {
            requireNotNull(params.isBindingCellphone) {
                "OtherMemberProfileQueryBuilder.isBindingCellphone not request"
            }
            return _isBindingCellphone
        }

    val level: Int?
        get() {
            requireNotNull(params.level) {
                "OtherMemberProfileQueryBuilder.level not request"
            }
            return _level
        }

    val nickname: String?
        get() {
            requireNotNull(params.nickname) {
                "OtherMemberProfileQueryBuilder.nickname not request"
            }
            return _nickname
        }
}