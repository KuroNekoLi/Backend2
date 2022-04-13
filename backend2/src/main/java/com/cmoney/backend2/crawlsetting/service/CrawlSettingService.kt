package com.cmoney.backend2.crawlsetting.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.crawlsetting.service.api.getcathaycastatus.GetCathayCaStatusRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * 爬蟲服務介面
 *
 * @see <a href="https://apitest-inner.cmoney.tw/CrawlSettingService/swagger/index.html">Swagger</a>
 */
interface CrawlSettingService {

    @RecordApi
    @POST
    suspend fun getCathayCaStatus(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetCathayCaStatusRequestBody
    ): Response<String>

}
