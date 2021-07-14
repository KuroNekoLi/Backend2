package com.cmoney.backend2.base.model.calladapter

/**
 * 紀錄Api的參數，在[RecordApiLogCallAdapterFactory]中使用。
 *
 * @property cmoneyAction 紀錄mobile service的Request Action，
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RecordApi(
    val cmoneyAction: String = ""
)
