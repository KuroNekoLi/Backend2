package com.cmoney.backend2.base.model.exception

import java.io.IOException

/**
 * Server回傳的錯誤訊息
 *
 * Note: Extends [IOException] for using this exception in okHttp interceptor.
 * @property code 錯誤碼
 * @property message 錯誤訊息
 */
data class ServerException(val code: Int, override val message: String) : IOException()