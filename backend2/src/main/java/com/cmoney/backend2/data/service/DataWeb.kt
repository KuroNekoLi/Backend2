package com.cmoney.backend2.data.service

import com.cmoney.backend2.data.service.api.FundIdData

interface DataWeb {

    /**
     * 取得 FundId 資料
     *
     * @param fundId fundId 編號
     * @param params fundId 對應參數
     */
    suspend fun getFundIdData(
        fundId: Int,
        params: String,
    ): Result<FundIdData>

}
