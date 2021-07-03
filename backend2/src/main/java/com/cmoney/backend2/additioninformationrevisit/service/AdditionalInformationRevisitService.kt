package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.api.request.GetRequestParam
import com.cmoney.backend2.additioninformationrevisit.service.api.request.RequestIds
import com.cmoney.backend2.base.model.calladapter.RecordApi
import retrofit2.Response
import retrofit2.http.*

interface AdditionalInformationRevisitService {

    @RecordApi
    @POST("{service}/api/GetAll/{typeName}")
    suspend fun getAll(
        @Header("Authorization") authorization: String,
        @Path("service") service: String,
        @Path("typeName") typeName: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST("{service}/api/GetTarget/{typeName}")
    suspend fun getTarget(
        @Header("Authorization") authorization: String,
        @Path("service") service: String,
        @Path("typeName") typeName: String,
        @Query("keyNamePath") keyNamePath: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST("{service}/api/GetMultiple/{typeName}")
    suspend fun getMultiple(
        @Header("Authorization") authorization: String,
        @Path("service") service: String,
        @Path("typeName") typeName: String,
        @Query("columns") columns: String,
        @Query("keyNamePath") keyNamePath: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST("{service}/api/GetOtherQuery/{requestTypeString}/{responseTypeString}")
    suspend fun getOtherQuery(
        @Header("Authorization") authorization: String,
        @Path("service") service: String,
        @Path("requestTypeString") requestType: String,
        @Path("responseTypeString") responseType: String,
        @Query("columns") columns: String,
        @Body param: GetRequestParam
    ): Response<List<List<String?>>>

    @RecordApi
    @POST("{service}/api/Signal/Get/{channels}")
    suspend fun getSignal(
        @Header("Authorization") authorization: String,
        @Path("service") service: String,
        @Path("channels") channels: String,
        @Body param: RequestIds
    ): Response<List<List<String?>>>
}