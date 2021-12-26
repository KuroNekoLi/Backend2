package com.cmoney.backend2.profile.service.api.queryprofile

/**
 * 手機資訊
 *
 * @property code 國碼
 * @property number 號碼
 */
class Cellphone private constructor(
    private val params: CellphoneQueryParams,
    private val _code: String?,
    private val _number: String?
) {
    internal constructor(
        params: CellphoneQueryParams,
        raw: RawCellphone
    ): this(
        params = params,
        _code = raw.code,
        _number = raw.number
    )

    val code: String?
        get() {
            requireNotNull(params.code) {
                "CellphoneQueryBuilder.code not request"
            }
            return _code
        }

    val number: String?
        get() {
            requireNotNull(params.number) {
                "CellphoneQueryBuilder.number not request"
            }
            return _number
        }
}
