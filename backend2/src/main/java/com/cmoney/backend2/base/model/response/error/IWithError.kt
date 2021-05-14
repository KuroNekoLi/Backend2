package com.cmoney.backend2.base.model.response.error

/**
 * Http code 為200，Body有[CMoneyError]物件．Response Body需實現IWithError的介面．
 */
interface IWithError<T> {
    fun toRealResponse(): T
}