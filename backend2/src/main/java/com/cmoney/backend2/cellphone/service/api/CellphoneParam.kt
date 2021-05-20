package com.cmoney.backend2.cellphone.service.api

/**
 * 電話參數
 *
 * @property countryCode 國碼
 * @property cellphoneNumber 註冊的手機號碼(09開頭或9開頭都可以)
 */
data class CellphoneParam(val countryCode: String, val cellphoneNumber: String)