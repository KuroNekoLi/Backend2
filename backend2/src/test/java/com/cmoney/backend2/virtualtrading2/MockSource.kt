package com.cmoney.backend2.virtualtrading2

import android.content.Context
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import com.google.gson.GsonBuilder

private const val PARENT = "virtualtrading2"
const val CREATE_ACCOUNT_SUCCESS_RESPONSE = "${PARENT}/createaccount/account_type_is_stock_response.json"
private val gson = GsonBuilder().setLenient().setPrettyPrinting().create()

/**
 * 取得建立帳號類型為Stock成功回應
 *
 */
fun Context.getCreateAccountTypeIsStockSuccess() = assets.open(CREATE_ACCOUNT_SUCCESS_RESPONSE)
    .bufferedReader()
    .use {
        it.readText()
    }
    .let {
        gson.fromJson(it, CreateAccountResponseBody::class.java)
    }