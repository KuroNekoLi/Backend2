package com.cmoney.backend2.virtualtrading2

import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccountratio.GetAccountRatioResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate.GetAllDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail.GetDelegateDetailResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetAllInventoryResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail.GetSuccessDealDetailResponseBody
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
fun getCreateTseOtcDelegateSuccess(): CreateDelegateResponseBody =
    """
    {
        "orderNo": 2060183
    }
    """
        .let {
            gson.fromJson(it, CreateDelegateResponseBody::class.java)
        }

/**
 * 取得刪除上市上櫃委託單成功回應
 *
 */
fun getDeleteTseOtcDelegateSuccess(): DeleteDelegateResponseBody =
    """
    {
        "orderNo": 2060183
    }
    """
        .let {
            gson.fromJson(it, DeleteDelegateResponseBody::class.java)
        }

/**
 * 取得特定帳號成功回應
 *
 */
fun getAccountSuccess(): GetAccountResponseBody =
    """
{
    "data": {
        "accountInfo": {
            "account": 2076,
            "accountPayType": 0,
            "accountType": 7,
            "avgMonthOrderCount": 2,
            "borrowFunds": "10000.0000",
            "borrowLimit": "100100000.0000",
            "canWatch": true,
            "createTime": 1672336514,
            "defaultFunds": "200000000.0000",
            "extendFunds": "0.0000",
            "funds": "199837438.0000",
            "groupId": 0,
            "isDefault": false,
            "isDelete": false,
            "isEmail": true,
            "maxReadSn": 0,
            "memberId": 123347,
            "name": "小資族",
            "needFee": true,
            "needTax": true,
            "optIncomeLoss": "0.0000",
            "stockIncomeLoss": "0.0000",
            "tmxIncomeLoss": "0.0000",
            "totalPunishment": 33,
            "tradedWarrantDate": 0,
            "updateTime": 1672336514,
            "viewTime": 0,
            "warrantIncomeLoss": "0.0000"
        }
    }
}
    """
        .let {
            gson.fromJson(it, GetAccountResponseBody::class.java)
        }

/**
 * 取得所有帳號成功回應
 *
 */
fun getAllAccountSuccess(): GetAllAccountResponseBody =
    """
{
    "data": {
        "allAccountInfo": [
            {
                "account": 2088,
                "accountPayType": 0,
                "accountType": 7,
                "avgMonthOrderCount": 0,
                "borrowFunds": "0.0000",
                "borrowLimit": "1000000.0000",
                "canWatch": true,
                "createTime": 1674111295,
                "defaultFunds": "2000000.0000",
                "extendFunds": "0.0000",
                "funds": "2000000.0000",
                "groupId": 0,
                "isDefault": false,
                "isDelete": false,
                "isEmail": true,
                "maxReadSn": 0,
                "memberId": 1517203,
                "name": "小資族",
                "needFee": true,
                "needTax": true,
                "optIncomeLoss": "0.0000",
                "stockIncomeLoss": "0.0000",
                "tmxIncomeLoss": "0.0000",
                "totalPunishment": 14,
                "tradedWarrantDate": 0,
                "updateTime": 1674111295,
                "viewTime": 0,
                "warrantIncomeLoss": "0.0000"
            },
            {
                "account": 2091,
                "accountPayType": 0,
                "accountType": 7,
                "avgMonthOrderCount": 0,
                "borrowFunds": "0.0000",
                "borrowLimit": "1000000.0000",
                "canWatch": true,
                "createTime": 1675244947,
                "defaultFunds": "2000000.0000",
                "extendFunds": "0.0000",
                "funds": "2000000.0000",
                "groupId": 0,
                "isDefault": false,
                "isDelete": false,
                "isEmail": true,
                "maxReadSn": 0,
                "memberId": 1517203,
                "name": "小資族",
                "needFee": true,
                "needTax": true,
                "optIncomeLoss": "0.0000",
                "stockIncomeLoss": "0.0000",
                "tmxIncomeLoss": "0.0000",
                "totalPunishment": 0,
                "tradedWarrantDate": 0,
                "updateTime": 1675244947,
                "viewTime": 0,
                "warrantIncomeLoss": "0.0000"
            },
            {
                "account": 2092,
                "accountPayType": 0,
                "accountType": 7,
                "avgMonthOrderCount": 0,
                "borrowFunds": "0.0000",
                "borrowLimit": "1000000.0000",
                "canWatch": true,
                "createTime": 1675246640,
                "defaultFunds": "2000000.0000",
                "extendFunds": "0.0000",
                "funds": "2000000.0000",
                "groupId": 0,
                "isDefault": true,
                "isDelete": false,
                "isEmail": true,
                "maxReadSn": 0,
                "memberId": 1517203,
                "name": "小資族",
                "needFee": true,
                "needTax": true,
                "optIncomeLoss": "0.0000",
                "stockIncomeLoss": "0.0000",
                "tmxIncomeLoss": "0.0000",
                "totalPunishment": 0,
                "tradedWarrantDate": 0,
                "updateTime": 1675246640,
                "viewTime": 0,
                "warrantIncomeLoss": "0.0000"
            }
        ]
    }
}
    """
        .let {
            gson.fromJson(it, GetAllAccountResponseBody::class.java)
        }
/**
 * 取得特定帳戶報酬率成功回應
 *
 */
fun getAccountRatioSuccess(): GetAccountRatioResponseBody =
    """
{
    "data": {
        "accountRatios": [
            {
                "account": 2076,
                "dataDe": 1675209600,
                "funds": "0.0000",
                "inventoryValues": "7460.0000",
                "isWeekend": false,
                "ratio": 0
            }
        ]
    }
}
    """
        .let {
            gson.fromJson(it, GetAccountRatioResponseBody::class.java)
        }

/**
 * 取得上市上櫃所有委託單成功回應
 *
 */
fun getTseOtcAllDelegateSuccess(): GetAllDelegateResponseBody =
    """
{
    "data": {
        "tseOtcOrderByCustomPeriod": [
            {
                "ordNo": 2060115,
                "targetOrdNo": 2060115,
                "account": 2076,
                "groupId": 0,
                "tradeTime": 1673228775,
                "status": 12,
                "ordType": 73,
                "condition": 82,
                "tradeType": 1,
                "stockMarketType": 1,
                "buySellType": 66,
                "commKey": "2883",
                "ordPr": "16.0000",
                "ordQty": "1000",
                "dealAvgPr": "0.0000",
                "dealQty": "0",
                "avQty": "0",
                "cutQty": "0",
                "prePayment": "0.0000",
                "serverRcvTe": 0,
                "serverRcvNo": 0,
                "marginCredit": "0.0000",
                "marginOwn": "0.0000",
                "shortSellingCollateral": "0.0000",
                "shortSellingEntrust": "0.0000",
                "memo": "",
                "noteId": 0,
                "modifyTime": 1673257724
            },
            {
                "ordNo": 2060116,
                "targetOrdNo": 2060116,
                "account": 2076,
                "groupId": 0,
                "tradeTime": 1673229032,
                "status": 12,
                "ordType": 73,
                "condition": 82,
                "tradeType": 1,
                "stockMarketType": 1,
                "buySellType": 66,
                "commKey": "2883",
                "ordPr": "16.0000",
                "ordQty": "1000",
                "dealAvgPr": "0.0000",
                "dealQty": "0",
                "avQty": "0",
                "cutQty": "0",
                "prePayment": "0.0000",
                "serverRcvTe": 0,
                "serverRcvNo": 0,
                "marginCredit": "0.0000",
                "marginOwn": "0.0000",
                "shortSellingCollateral": "0.0000",
                "shortSellingEntrust": "0.0000",
                "memo": "",
                "noteId": 0,
                "modifyTime": 1673257981
            }
        ]
    }
}
    """
        .let {
            gson.fromJson(it, GetAllDelegateResponseBody::class.java)
        }


/**
 * 取得上市上櫃委託單細節成功回應
 *
 */
fun getTseOtcDelegateDetailSuccess(): GetDelegateDetailResponseBody =
    """
{
    "data": {
        "tseOtcOrder": {
            "ordNo": 2060107,
            "targetOrdNo": 2060107,
            "account": 2076,
            "groupId": 0,
            "tradeTime": 1673253208,
            "status": 12,
            "ordType": 73,
            "condition": 82,
            "tradeType": 1,
            "stockMarketType": 1,
            "buySellType": 66,
            "commKey": "2883",
            "ordPr": "16.0000",
            "ordQty": "1000",
            "dealAvgPr": "0.0000",
            "dealQty": "0",
            "avQty": "0",
            "cutQty": "0",
            "prePayment": "0.0000",
            "serverRcvTe": 0,
            "serverRcvNo": 0,
            "marginCredit": "0.0000",
            "marginOwn": "0.0000",
            "shortSellingCollateral": "0.0000",
            "shortSellingEntrust": "0.0000",
            "memo": "",
            "noteId": 0,
            "modifyTime": 1673112988
        }
    }
}
    """
        .let {
            gson.fromJson(it, GetDelegateDetailResponseBody::class.java)
        }

/**
 * 取得上市上櫃成交單成功回應
 *
 */
fun getTseOtcAllSuccessDealSuccess(): GetAllSuccessDealResponseBody =
    """
{
    "data": {
        "tseOtcDealByCustomPeriod": [
            {
                "te": 1673258016,
                "account": 2076,
                "ordNo": 2060117,
                "stockMarketType": 1,
                "tradeType": 1,
                "buySellType": 66,
                "commKey": "2883",
                "dealPr": "13.0000",
                "dealQty": "1000",
                "fee": "20.0000",
                "tax": "0.0000",
                "dealTno": 10005,
                "flag": true,
                "sn": 225837,
                "shortSellingFee": "0.0000",
                "memo": "",
                "actualCost": "13020.0000",
                "borrow": "0.0000",
                "bsAvgPr": "0.0000",
                "remainQty": "1000",
                "isSuccess": true
            }
        ]
    }
}
    """
        .let {
            gson.fromJson(it, GetAllSuccessDealResponseBody::class.java)
        }

/**
 * 取得上市上櫃成交單細節成功回應
 *
 */
fun getTseOtcSuccessDealDetailSuccess(): GetSuccessDealDetailResponseBody =
    """
{
    "data": {
        "tseOtcDeal": {
            "te": 1673262190,
            "account": 2076,
            "ordNo": 2060128,
            "stockMarketType": 1,
            "tradeType": 1,
            "buySellType": 66,
            "commKey": "2890",
            "dealPr": "17.2000",
            "dealQty": "1000",
            "fee": "25.0000",
            "tax": "0.0000",
            "dealTno": 10021,
            "flag": true,
            "sn": 225838,
            "shortSellingFee": "0.0000",
            "memo": "",
            "actualCost": "17225.0000",
            "borrow": "0.0000",
            "bsAvgPr": "0.0000",
            "remainQty": "1000",
            "isSuccess": true
        }
    }
}
    """
        .let {
            gson.fromJson(it, GetSuccessDealDetailResponseBody::class.java)
        }

/**
 * 取得上市上櫃庫存成功回應
 */
fun getTseOtcInventorySuccess(): GetAllInventoryResponseBody =
    """
{
    "data": {
        "tseOtcPosition": [
            {
                "account": 2076,
                "bs": 66,
                "canOrdQty": "6000",
                "commKey": "1444",
                "commName": "力麗",
                "cost": "62800.0000",
                "createTime": 1675213118,
                "dealAvgPr": "10.4700",
                "incomeLoss": "-1063",
                "incomeLossWithoutPreFee": "-789",
                "inventoryQty": "6000",
                "nowPr": "10.35",
                "ratio": "-1.69",
                "shortSellingFee": "0",
                "showCost": "62800.0000",
                "taxCost": "186",
                "todayInventoryQty": "0",
                "tradeName": "現股買進",
                "tradeType": 1
            }
        ]
    }
}
    """
        .let {
            gson.fromJson(it, GetAllInventoryResponseBody::class.java)
        }