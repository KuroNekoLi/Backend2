package com.cmoney.backend2.profile.service.api.queryprofile


/**
 * 徽章資訊
 *
 * @property id 編號
 * @property isEquipped 是否裝備，true為正在裝備中，false為未裝備
 * @property hasRead 是否已讀，true為已讀，false為未讀
 */
class Badges private constructor(
    private val params: BadgesQueryParams,
    private val _id: Long?,
    private val _isEquipped: Boolean?,
    private val _hasRead: Boolean?
) {
    internal constructor(
        params: BadgesQueryParams,
        raw: RawBadges
    ) : this(
        params = params,
        _id = raw.id,
        _isEquipped = raw.isEquipped,
        _hasRead = raw.hasRead
    )

    val id: Long?
        get() {
            requireNotNull(params.badgeId) {
                "BadgesQueryBuilder.badgeId not request"
            }
            return _id
        }

    val isEquipped: Boolean?
        get() {
            requireNotNull(params.isEquipped) {
                "BadgesQueryBuilder.isEquipped not request"
            }
            return _isEquipped
        }

    val hasRead: Boolean?
        get() {
            requireNotNull(params.hasRead) {
                "BadgesQueryBuilder.hasRead not request"
            }
            return _hasRead
        }
}
