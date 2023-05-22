package com.cmoney.backend2.tickdata.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.tickdata.service.api.getkchartdata.KDataItem
import com.cmoney.backend2.tickdata.service.api.getmachartdata.MaDataItem
import com.cmoney.backend2.tickdata.service.api.getmultiplemovingaverage.MultipleMovingAverageData

interface TickDataWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得K線資料
     *
     * ```
     * 備註:
     * 時間刻度會於每天從頭計算
     * 當刻度為 20 時
     * 每天會從 09:20 ~ 13:30
     * 也就是 0920, 0940, 1000 ... 1320, 1330
     * ```
     *
     * @param date 時間(UnixTime)，ms
     * @param commKey 標的
     * @param minuteInterval 分時K線時間刻度 (1分填1, 5分填5以此類推)
     * @param count 資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return K線資料
     */
    suspend fun getKChartData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        domain: String = manager.getTickDataSettingAdapter().getDomain(),
        url: String = "${domain}TickDataService/api/GetKChartData"
    ): Result<List<KDataItem>>

    /**
     * 取得移動均線資料(時間, 20MA, 60MA, 240MA)
     *
     * ```
     * 備註:
     * 時間刻度會於每天從頭計算
     * 當刻度為 20 時
     * 每天會從 09:20 ~ 13:30
     * 也就是 0920, 0940, 1000 ... 1320, 1330
     * ```
     *
     * @param date 時間(UnixTime)，ms
     * @param commKey 標的
     * @param minuteInterval 分時K線時間刻度 (1分填1, 5分填5以此類推)
     * @param count 資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 移動均線資料
     */
    suspend fun getMAData(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        domain: String = manager.getTickDataSettingAdapter().getDomain(),
        url: String = "${domain}TickDataService/api/GetMAChartData"
    ): Result<List<MaDataItem>>

    /**
     * 取得指定的移動均線資料
     *
     * ```
     * 備註:
     * 時間刻度會於每天從頭計算
     * 當刻度為 20 時
     * 每天會從 09:20 ~ 13:30
     * 也就是 0920, 0940, 1000 ... 1320, 1330
     * ```
     *
     * @param date 時間(UnixTime)，ms
     * @param commKey 標的
     * @param minuteInterval 分時K線時間刻度 (1分填1, 5分填5以此類推)(均線的時間刻度)
     * @param count 資料筆數
     * @param dataPoints 以幾筆分K統計均線，20MA填20，60MA填60
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return
     */
    suspend fun getMultipleMovingAverage(
        date: Long,
        commKey: String,
        minuteInterval: Int,
        count: Int,
        dataPoints: List<Int>,
        domain: String = manager.getTickDataSettingAdapter().getDomain(),
        url: String = "${domain}TickDataService/api/GetMultipleMovingAverage"
    ): Result<List<MultipleMovingAverageData>>
}