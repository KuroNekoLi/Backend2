package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.api.request.GetRequestParam
import com.cmoney.backend2.additioninformationrevisit.service.api.request.RequestIds
import com.cmoney.backend2.base.model.calladapter.RecordApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface AdditionalInformationRevisitService {

    @RecordApi
    @POST
    suspend fun getAll(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getTarget(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyNamePath") keyNamePath: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getMultiple(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Query("keyNamePath") keyNamePath: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getOtherQuery(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getSignal(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body param: RequestIds
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getYesterdayAll(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getYesterdayTarget(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyNamePath") keyNamePath: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getYesterdayMultiple(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Query("keyNamePath") keyNamePath: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getYesterdayOtherQuery(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>
}