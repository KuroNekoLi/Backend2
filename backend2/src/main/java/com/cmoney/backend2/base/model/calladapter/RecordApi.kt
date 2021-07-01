package com.cmoney.backend2.base.model.calladapter

/**
 * 紀錄Api的參數，在[RecordApiLogCallAdapterFactory]
 *
 * @property isLogRequestBody 是否要紀錄RequestBody，預設為true。在特定情況是不需要紀錄Body的，例如：登入API的帳號密碼。
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RecordApi(
    val isLogRequestBody: Boolean = true
)
