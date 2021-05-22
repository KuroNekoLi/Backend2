package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfo

interface ChipKWeb {

    /**
     * 服務6-2. 籌碼K統一要求Dtno的方法
     * @param commKey 股票代號
     * @param type 1-KD, 2-MACD, 3-外資成本線, 4-持股比率, 5-月營收, 6-獲利
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getData(
        commKey: String,
        type: Int
    ): Result<DtnoData>

    /**
     * 服務6-12. 取得K圖資料
     * @param commKey 股票代號
     * @param timeRange 1-日, 2-週, 3-月
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getIndexKData(
        commKey: String,
        timeRange: Int
    ): Result<DtnoData>

    /**
     * 服務4-1. 向國鳴用封包要服務(已無使用)
     * @param fundId 服務編號 (參見 資料輸出入SPEC_20XXXXXX.doc)
     * @param params 服務的參數 (用底線來分開)
     */
    suspend fun getChipKData(
        fundId: Int,
        params: String
    ): Result<DtnoData>

    /**
     * 取得官方資料
     */
    suspend fun getOfficialStockPickData(
        index: Int,
        pageType: Int
    ): Result<OfficialStockInfo>

    /**
     * 取得官方標題
     */
    suspend fun getOfficialStockPickTitle(
        type: Int
    ): Result<List<String>>
}
