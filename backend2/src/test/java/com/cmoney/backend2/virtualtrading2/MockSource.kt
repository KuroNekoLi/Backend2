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
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetInventoryResponseBody
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

/**
 * 取得刪除上市上櫃委託單成功回應
 *
 */
fun getDeleteTseOtcDelegateSuccess() =
    """
    {
        "orderNo": 2060183,
        "message": "",
        "isSuccess": true
    }
    """
        .let {
            gson.fromJson(it, DeleteDelegateResponseBody::class.java)
        }

/**
 * 取得特定帳號成功回應
 *
 */
fun getAccountSuccess() =
    """
{
    "data": {
        "accountInfo": {
            "account": 2076,
            "accountPayType": 0,
            "accountType": 7,
            "avgMonthOrderCount": 1.3,
            "borrowFunds": 0.0000,
            "borrowLimit": 100100000.0000,
            "canWatch": true,
            "createTime": "2022-12-29T17:55:14.998Z",
            "defaultFunds": 200000000.0000,
            "extendFunds": 0.0000,
            "funds": 199944122.0000,
            "groupId": 0,
            "isDefault": false,
            "isDelete": false,
            "isEmail": true,
            "maxReadSn": 0,
            "memberId": 123347,
            "name": "小資族",
            "needFee": true,
            "needTax": true,
            "optIncomeLoss": 0.0000,
            "stockIncomeLoss": 0.0000,
            "tmxIncomeLoss": 0.0000,
            "totalPunishment": 18,
            "tradedWarrantDate": 0,
            "updateTime": "2022-12-29T17:55:14.998Z",
            "viewTime": null,
            "warrantIncomeLoss": 0.0000
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
fun getAllAccountSuccess() =
    """
{
    "data": {
        "allAccountInfo": [
            {
                "account": 2076,
                "accountPayType": 0,
                "accountType": 7,
                "avgMonthOrderCount": 1.3,
                "borrowFunds": 0.0000,
                "borrowLimit": 100100000.0000,
                "canWatch": true,
                "createTime": "2022-12-29T17:55:14.998Z",
                "defaultFunds": 200000000.0000,
                "extendFunds": 0.0000,
                "funds": 199944122.0000,
                "groupId": 0,
                "isDefault": false,
                "isDelete": false,
                "isEmail": true,
                "maxReadSn": 0,
                "memberId": 123347,
                "name": "小資族",
                "needFee": true,
                "needTax": true,
                "optIncomeLoss": 0.0000,
                "stockIncomeLoss": 0.0000,
                "tmxIncomeLoss": 0.0000,
                "totalPunishment": 18,
                "tradedWarrantDate": 0,
                "updateTime": "2022-12-29T17:55:14.998Z",
                "viewTime": null,
                "warrantIncomeLoss": 0.0000
            },
            {
                "account": 2078,
                "accountPayType": 0,
                "accountType": 7,
                "avgMonthOrderCount": 0,
                "borrowFunds": 0.0000,
                "borrowLimit": 100000000.0000,
                "canWatch": true,
                "createTime": "2023-01-07T08:13:32.287Z",
                "defaultFunds": 200000000.0000,
                "extendFunds": 0.0000,
                "funds": 200000000.0000,
                "groupId": 0,
                "isDefault": false,
                "isDelete": false,
                "isEmail": true,
                "maxReadSn": 0,
                "memberId": 123347,
                "name": "小資族",
                "needFee": true,
                "needTax": true,
                "optIncomeLoss": 0.0000,
                "stockIncomeLoss": 0.0000,
                "tmxIncomeLoss": 0.0000,
                "totalPunishment": 10,
                "tradedWarrantDate": 0,
                "updateTime": "2023-01-07T08:13:32.287Z",
                "viewTime": null,
                "warrantIncomeLoss": 0.0000
            },
            {
                "account": 2079,
                "accountPayType": 0,
                "accountType": 7,
                "avgMonthOrderCount": 0,
                "borrowFunds": 0.0000,
                "borrowLimit": 100000000.0000,
                "canWatch": true,
                "createTime": "2023-01-09T06:06:23.821Z",
                "defaultFunds": 200000000.0000,
                "extendFunds": 0.0000,
                "funds": 200000000.0000,
                "groupId": 0,
                "isDefault": true,
                "isDelete": false,
                "isEmail": true,
                "maxReadSn": 0,
                "memberId": 123347,
                "name": "小資族",
                "needFee": true,
                "needTax": true,
                "optIncomeLoss": 0.0000,
                "stockIncomeLoss": 0.0000,
                "tmxIncomeLoss": 0.0000,
                "totalPunishment": 9,
                "tradedWarrantDate": 0,
                "updateTime": "2023-01-09T06:06:23.821Z",
                "viewTime": null,
                "warrantIncomeLoss": 0.0000
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
fun getAccountRatioSuccess() =
    """
{
    "data": {
        "accountRatios": [
            {
                "account": 2076,
                "dataDe": "2023-01-17T00:00:00.000Z",
                "funds": 0.0000,
                "inventoryValues": -906.0000,
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
fun getTseOtcAllDelegateSuccess() =
    """
{
    "data": {
        "tseOtcOrderByCustomPeriod": [
            {
                "ordNo": 2060115,
                "targetOrdNo": 2060115,
                "account": 2076,
                "groupId": 0,
                "tradeTime": "2023-01-09T01:46:15.583Z",
                "status": 12,
                "ordType": 73,
                "condition": 0,
                "tradeType": 1,
                "stockMarketType": 1,
                "buySellType": 66,
                "commKey": "2883",
                "ordPr": 16.0000,
                "ordQty": 1000,
                "dealAvgPr": 0.0000,
                "dealQty": 0,
                "avQty": 0,
                "cutQty": 0,
                "prePayment": 0.0000,
                "serverRcvTe": "0001-01-01T00:00:00.000Z",
                "serverRcvNo": 0,
                "marginCredit": 0.0000,
                "marginOwn": 0.0000,
                "shortSellingCollateral": 0.0000,
                "shortSellingEntrust": 0.0000,
                "memo": "",
                "noteId": 0,
                "modifyTime": "2023-01-09T09:48:44.403Z"
            },
            {
                "ordNo": 2060116,
                "targetOrdNo": 2060116,
                "account": 2076,
                "groupId": 0,
                "tradeTime": "2023-01-09T01:50:32.484Z",
                "status": 12,
                "ordType": 73,
                "condition": 0,
                "tradeType": 1,
                "stockMarketType": 1,
                "buySellType": 66,
                "commKey": "2883",
                "ordPr": 16.0000,
                "ordQty": 1000,
                "dealAvgPr": 0.0000,
                "dealQty": 0,
                "avQty": 0,
                "cutQty": 0,
                "prePayment": 0.0000,
                "serverRcvTe": "0001-01-01T00:00:00.000Z",
                "serverRcvNo": 0,
                "marginCredit": 0.0000,
                "marginOwn": 0.0000,
                "shortSellingCollateral": 0.0000,
                "shortSellingEntrust": 0.0000,
                "memo": "",
                "noteId": 0,
                "modifyTime": "2023-01-09T09:53:01.183Z"
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
fun getTseOtcDelegateDetailSuccess() =
    """
{
    "data": {
        "tseOtcOrder": {
            "ordNo": 2060107,
            "targetOrdNo": 2060107,
            "account": 2076,
            "groupId": 0,
            "tradeTime": "2023-01-09T08:33:28.229Z",
            "status": 12,
            "ordType": 73,
            "condition": 0,
            "tradeType": 1,
            "stockMarketType": 1,
            "buySellType": 66,
            "commKey": "2883",
            "ordPr": 16.0000,
            "ordQty": 1000,
            "dealAvgPr": 0.0000,
            "dealQty": 0,
            "avQty": 0,
            "cutQty": 0,
            "prePayment": 0.0000,
            "serverRcvTe": "0001-01-01T00:00:00.000Z",
            "serverRcvNo": 0,
            "marginCredit": 0.0000,
            "marginOwn": 0.0000,
            "shortSellingCollateral": 0.0000,
            "shortSellingEntrust": 0.0000,
            "memo": "",
            "noteId": 0,
            "modifyTime": "2023-01-07T17:36:28.807Z"
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
fun getTseOtcAllSuccessDealSuccess() =
    """
{
    "data": {
        "tseOtcDealByCustomPeriod": [
            {
                "te": "2023-01-09T09:53:36.000Z",
                "account": 2076,
                "ordNo": 2060117,
                "stockMarketType": 1,
                "tradeType": 1,
                "buySellType": 66,
                "commKey": "2883",
                "dealPr": 13.0000,
                "dealQty": 1000,
                "fee": 20.0000,
                "tax": 0.0000,
                "dealTno": 10005,
                "flag": true,
                "sn": 225837,
                "shortSellingFee": 0.0000,
                "memo": "",
                "actualCost": 13020.0000,
                "borrow": 0.0000,
                "bsAvgPr": 0.0000,
                "remainQty": 1000,
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
fun getTseOtcSuccessDealDetailSuccess() =
    """
{
    "data": {
        "tseOtcDeal": {
            "te": "2023-01-09T11:03:10.000Z",
            "account": 2076,
            "ordNo": 2060128,
            "stockMarketType": 1,
            "tradeType": 1,
            "buySellType": 66,
            "commKey": "2890",
            "dealPr": 17.2000,
            "dealQty": 1000,
            "fee": 25.0000,
            "tax": 0.0000,
            "dealTno": 10021,
            "flag": true,
            "sn": 225838,
            "shortSellingFee": 0.0000,
            "memo": "",
            "actualCost": 17225.0000,
            "borrow": 0.0000,
            "bsAvgPr": 0.0000,
            "remainQty": 1000,
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
fun getTseOtcInventorySuccess() =
    """
{
    "data": {
        "tseOtcPosition": [
            {
                "account": 2076,
                "bs": 0,
                "canOrdQty": 2000,
                "commKey": "2303",
                "commName": "聯電",
                "cost": 90200.0000,
                "createTime": "2023-01-12T13:41:00.818Z",
                "dealAvgPr": 45.1000,
                "incomeLoss": -926,
                "incomeLossWithoutPreFee": -529,
                "inventoryQty": 2000,
                "nowPr": 44.9,
                "ratio": -1.03,
                "shortSellingFee": 0,
                "showCost": 90200.0000,
                "taxCost": 269,
                "todayInventoryQty": 0,
                "tradeName": "現股買進",
                "tradeType": 1
            },
            {
                "account": 2076,
                "bs": 66,
                "canOrdQty": 2000,
                "commKey": "2883",
                "commName": "開發金",
                "cost": 13000.0000,
                "createTime": "2023-01-09T01:55:07.724Z",
                "dealAvgPr": 13.0000,
                "incomeLoss": -78,
                "incomeLossWithoutPreFee": -19,
                "inventoryQty": 1000,
                "nowPr": 13,
                "ratio": -0.6,
                "shortSellingFee": 0,
                "showCost": 13000.0000,
                "taxCost": 39,
                "todayInventoryQty": 0,
                "tradeName": "現股買進",
                "tradeType": 1
            },
            {
                "account": 2076,
                "bs": 66,
                "canOrdQty": 11000,
                "commKey": "2890",
                "commName": "永豐金",
                "cost": 17200.0000,
                "createTime": "2023-01-09T03:00:52.960Z",
                "dealAvgPr": 17.2000,
                "incomeLoss": 98,
                "incomeLossWithoutPreFee": 175,
                "inventoryQty": 1000,
                "nowPr": 17.4,
                "ratio": 0.57,
                "shortSellingFee": 0,
                "showCost": 17200.0000,
                "taxCost": 52,
                "todayInventoryQty": 0,
                "tradeName": "現股買進",
                "tradeType": 1
            }
        ]
    }
}
    """
        .let {
            gson.fromJson(it, GetInventoryResponseBody::class.java)
        }