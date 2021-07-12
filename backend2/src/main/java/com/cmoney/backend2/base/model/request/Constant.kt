package com.cmoney.backend2.base.model.request

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant.SERVICE_ERROR_CODE
import com.cmoney.backend2.base.model.request.Constant.SERVICE_NOT_SUPPORT_ERROR_CODE

/**
 * @property SERVICE_ERROR_CODE API預設的錯誤碼，用在[ServerException]的code，如果在要求有code的API沒有給，會使用此錯誤碼。
 * @property SERVICE_NOT_SUPPORT_ERROR_CODE API不支援的錯誤碼的形式。
 */
object Constant {
    const val SERVICE_ERROR_CODE = -1
    const val SERVICE_NOT_SUPPORT_ERROR_CODE = -2
}