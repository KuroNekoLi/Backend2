package com.cmoney.backend2.base.model.log

import com.google.gson.annotations.SerializedName

/**
 * x-cmapi-trace-context的使用紀錄，之後會拔掉。目前只有需要在登入和註冊使用。
 *
 * @property appId App的Id
 * @property platform 平台
 * @property mode App版本，登入或註冊方式, 1.Email 2.FB 3.手機 4.AppleId 5.Firebase匿名 6. Refresh Token 7. Google 8. CMoneyThirdParty 9. authorize_code
 */
data class XApiLog(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Platform")
    val platform: Int,
    @SerializedName("Mode")
    val mode: Int
)