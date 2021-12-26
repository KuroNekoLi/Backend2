package com.cmoney.backend2.profile.service.api.queryprofile

/**
 * Facebook資訊
 *
 * @property id FacebookId
 * @property email Facebook上信箱
 * @property name Facebook上名稱
 */
class Facebook private constructor(
    private val params: FacebookQueryParams,
    private val _id: String?,
    private val _email: String?,
    private val _name: String?
) {
    internal constructor(
        params: FacebookQueryParams,
        raw: RawFacebook
    ) : this(
        params = params,
        _id = raw.id,
        _email = raw.email,
        _name = raw.name
    )

    val id: String?
        get() {
            requireNotNull(params.fbId) {
                "FacebookQueryBuilder.fbId not request"
            }
            return _id
        }

    val email: String?
        get() {
            requireNotNull(params.email) {
                "FacebookQueryBuilder.email not request"
            }
            return _email
        }

    val name: String?
        get() {
            requireNotNull(params.name) {
                "FacebookQueryBuilder.name not request"
            }
            return _name
        }
}
