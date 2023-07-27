package com.cmoney.backend2.data.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.data.service.api.FundIdWithError
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * DataService
 *
 * @see <a href="http://192.168.99.115/link/3779#bkmrk-page-title">DOC</a>
 */
interface DataService {

    @RecordApi
    @GET
    suspend fun getFundIdData(
        @Url url: String,
        @Header("Authorization") authToken: String,
        @Query(value = "fundId") fundId: Int,
        @Query(value = "params") params: String
    ): Response<FundIdWithError>

}
