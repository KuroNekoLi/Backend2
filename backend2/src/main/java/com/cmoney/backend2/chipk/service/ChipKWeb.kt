package com.cmoney.backend2.chipk.service

import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.chipk.service.api.getOfficialStockPickData.OfficialStockInfo
import com.cmoney.backend2.chipk.service.api.internationalkchart.ProductType
import com.cmoney.backend2.chipk.service.api.internationalkchart.TickInfoSet

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
     * 服務6-6. 要求大盤外資的資料(TWA00)
     * @param tickCount 根數
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getIndexForeignInvestment(
        tickCount: Int
    ): Result<DtnoData>

    /**
     * 服務6-7. 要求大盤主力的資料(TWA00)
     * @param tickCount 根數
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getIndexMain(
        tickCount: Int
    ): Result<DtnoData>

    /**
     * 服務6-8. 要求大盤資券資料(TWA00)
     * @param tickCount 根數
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getIndexFunded(
        tickCount: Int
    ): Result<DtnoData>

    /**
     * 服務6-9. 要求國際盤後資料
     *
     * @param id 商品代號
     * @param productType 商品類型
     */
    suspend fun getInternationalKChart(
        id: String,
        productType: ProductType
    ): Result<TickInfoSet>

    /**
     * 服務6-10. 取得加權指數融資維持率
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getCreditRate(): Result<DtnoData>

    /**
     * 服務6-11. 取得指數技術圖
     * @param commKey 股票代號
     * @param timeRange 1-日, 2-週, 3-月
     * @return 請使用DtnoData.toListOfSomething 轉換成自定義的物件
     */
    suspend fun getIndexCalculateRate(
        commKey: String,
        timeRange: Int
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
