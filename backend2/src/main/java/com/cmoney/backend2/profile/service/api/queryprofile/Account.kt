package com.cmoney.backend2.profile.service.api.queryprofile

/**
 * 帳號資訊
 *
 * @property appleId AppleID
 * @property cellphone 手機資訊
 * @property email 電子信箱
 * @property facebook Facebook資訊
 * @property guestId FirebaseID
 */
class Account private constructor(
    private val params: AccountQueryParams,
    private val _appleId: String?,
    private val _cellphone: Cellphone?,
    private val _email: String?,
    private val _facebook: Facebook?,
    private val _guestId: String?
) {
    internal constructor(
        params: AccountQueryParams,
        raw: RawAccount
    ) : this(
        params = params,
        _appleId = raw.appleId,
        _cellphone = params.cellphone?.let { cellphoneQueryParams ->
            if (raw.cellphone != null) {
                Cellphone(params = cellphoneQueryParams, raw = raw.cellphone)
            } else {
                null
            }
        },
        _email = raw.email,
        _facebook = params.facebook?.let { facebookQueryParams ->
            if (raw.facebook != null) {
                Facebook(params = facebookQueryParams, raw = raw.facebook)
            } else {
                null
            }
        },
        _guestId = raw.guestId
    )

    val appleId: String?
        get() {
            requireNotNull(params.appleId) {
                "AccountQueryBuilder.appleId not request"
            }
            return _appleId
        }

    val cellphone: Cellphone?
        get() {
            requireNotNull(params.cellphone) {
                "AccountQueryBuilder.cellphone not request"
            }
            return _cellphone
        }

    val email: String?
        get() {
            requireNotNull(params.email) {
                "AccountQueryBuilder.email not request"
            }
            return _email
        }

    val facebook: Facebook?
        get() {
            requireNotNull(params.facebook) {
                "AccountQueryBuilder.facebook not request"
            }
            return _facebook
        }

    val guestId: String?
        get() {
            requireNotNull(params.guestId) {
                "AccountQueryBuilder.guestId not request"
            }
            return _guestId
        }
}
