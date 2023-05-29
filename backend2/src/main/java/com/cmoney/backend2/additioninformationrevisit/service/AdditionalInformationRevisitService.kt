package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.api.request.GetRequestParam
import com.cmoney.backend2.base.model.calladapter.RecordApi
import retrofit2.Response
import retrofit2.http.*

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
        @Body param: Any
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getPreviousAll(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getPreviousTarget(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("keyNamePath") keyNamePath: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getPreviousMultiple(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Query("keyNamePath") keyNamePath: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST
    suspend fun getPreviousOtherQuery(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>
}