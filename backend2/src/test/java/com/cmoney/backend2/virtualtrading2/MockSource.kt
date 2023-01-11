package com.cmoney.backend2.virtualtrading2

import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateResponseBody
import com.google.gson.GsonBuilder

private val gson = GsonBuilder().setLenient().setPrettyPrinting().create()

/**
 * 取得建立帳號成功
 */
fun getCreateAccountSuccess(accountPayType: Int = 0) = CreateAccountResponseBody(
    accountId = null,
    name = null,
    groupId = null,
    memberId = null,
    defaultFunds = null,
    funds = null,
    isNeedFee = null,
    isNeedTax = null,
    canWatch = null,
    isDefault = null,
    isDelete = null,
    accountType = null,
    createTime = null,
    updateTime = null,
    viewTime = null,
    accountPayType = accountPayType,
    maxReadSn = null,
    isEmail = null,
    averageTradingCountInMonth = null,
    totalPunishment = null,
    tradedWarrantDate = null,
    extendFunds = null,
    stockIncomeLoss = null,
    warrantIncomeLoss = null,
    futureIncomeLoss = null,
    optionIncomeLoss = null,
    borrowFunds = null,
    borrowLimit = null
)

/**
 * 取得建立上市上櫃委託單成功回應
 *
 */
fun getCreateTseOtcDelegateSuccess() =
    """
    {
        "orderNo": 2060183,
        "message": "",
        "isSuccess": true
    }
    """
        .let {
            gson.fromJson(it, CreateDelegateResponseBody::class.java)
        }