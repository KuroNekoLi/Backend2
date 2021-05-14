package com.cmoney.backend2.base.model.response.error

import com.cmoney.backend2.base.model.request.Constant

/**
 * 以ResponseCode為判斷基準，決定Api的成功與失敗
 */
interface ISuccess {

    fun getErrorMessage(): String

    fun getErrorCode(): Int

    fun isResponseSuccess(): Boolean

    companion object {

        const val DEFAULT_ERROR_MESSAGE = "Default Message"

        const val DEFAULT_ERROR_CODE = Constant.SERVICE_ERROR_CODE
    }
}