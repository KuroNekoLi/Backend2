package com.cmoney.backend2.data.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.data.service.api.FundIdData

interface DataWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得 FundId 資料
     *
     * @param fundId fundId 編號
     * @param params fundId 對應參數
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getFundIdData(
        fundId: Int,
        params: String,
        domain: String = manager.getDataSettingAdapter().getDomain(),
        url: String = "${domain}api/ChipK"
    ): Result<FundIdData>

}
