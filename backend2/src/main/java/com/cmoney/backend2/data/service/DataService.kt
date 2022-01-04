package com.cmoney.backend2.data.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.data.service.api.FundIdWithError
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

/**
 * DataService
 *
 * @see <a href="http://192.168.99.115/books/service-%E8%82%A1%E5%B8%82%EF%BC%88%E5%8D%B3%E7%9B%A4%EF%BC%89/page/%E7%B1%8C%E7%A2%BCk%E7%9B%A4%E5%BE%8C%E8%B3%87%E6%96%99%E6%9F%A5%E8%A9%A2%E6%9C%8D%E5%8B%99">DOC</a>
 */
interface DataService {

    @RecordApi
    @GET
    suspend fun getFundIdData(
        @Header("Authorization") authToken: String,
        @Url url: String
    ): Response<FundIdWithError>

}
