package com.cmoney.backend2.data.service

import com.cmoney.backend2.data.service.api.FundIdData

interface DataWeb {

    /**
     * 預設服務網域
     */
    val baseHost: String

    /**
     * 取得 FundId 資料
     *
     * @param host 服務網域
     * @param fundId fundId 編號
     * @param params fundId 對應參數
     */
    suspend fun getFundIdData(
        host: String = baseHost,
        fundId: Int,
        params: String,
    ): Result<FundIdData>

}
