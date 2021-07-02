package com.cmoney.backend2.media.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.media.service.api.getmediadetail.GetMediaDetailResponseWithError
import com.cmoney.backend2.media.service.api.getmediainfo.MediaInfoWithError
import com.cmoney.backend2.media.service.api.getmediaurl.GetMediaUrlResponseBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MediaService {
    /**
     * 服務2.取得影音詳細資訊(有單元課程的影音才會用)-已廢除
     */
    @RecordApi
    @GET("MobileService/ashx/Media/Media.ashx")
    suspend fun getMediaDetail(
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "GetMediaDetail",
        @Query("appId") appId: Int,
        @Query("guid") guid: String,
        @Query("mediaId") mediaId: Long,
        @Query("device") device: Int
    ): Response<GetMediaDetailResponseWithError>

    /**
     * 服務3.取得網頁付費影音清單
     */
    @RecordApi
    @GET("MobileService/ashx/Media/Media.ashx")
    suspend fun getPaidMediaList(
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "getpaidmedialist",
        @Query("appid") appId: Int,
        @Query("guid") guid: String,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<ResponseBody>

    /**
     * 服務4.取得網頁付費直播清單
     */
    @RecordApi
    @GET("MobileService/ashx/Media/Media.ashx")
    suspend fun getPaidLiveList(
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "getpaidlivevideolist",
        @Query("appid") appId: Int,
        @Query("guid") guid: String,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<ResponseBody>

    /**
     * 服務5.取得影音詳情內容
     */
    @RecordApi
    @POST("MobileService/ashx/Media/Media.ashx")
    @FormUrlEncoded
    suspend fun getMediaInfo(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmediainfo",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("mediaId") mediaId: Long
    ): Response<MediaInfoWithError>

    /**
     * 服務6.取得會員已購買的影音清單
     * @return The raw json element which may be array or error object.
     */
    @RecordApi
    @GET("MobileService/ashx/Media/Media.ashx")
    suspend fun getPaidMediaListOfMember(
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "getpaidmedialistofmember",
        @Query("appid") appId: Int,
        @Query("guid") guid: String,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<ResponseBody>

    /**
     * 服務7.取得影音購買網址
     */
    @RecordApi
    @POST("MobileService/ashx/Media/Media.ashx")
    @FormUrlEncoded
    suspend fun getMediaPurchaseUrl(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmediapurchaseurl",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("mediaId") mediaId: Long
    ): Response<GetMediaUrlResponseBody>

    /**
     * 服務8.取得影音播放網址
     */
    @RecordApi
    @POST("MobileService/ashx/Media/Media.ashx")
    @FormUrlEncoded
    suspend fun getMediaUrl(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmediaplayurl",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("mediaId") mediaId: Long
    ): Response<GetMediaUrlResponseBody>

    /**
     * 服務9.1.取得手機影音清單
     * 20210615 新增 tagIdList
     * @param chargeType 0: All, 1: Paid, 2: Free
     * @return The raw json element which may be array or error object.
     */
    @RecordApi
    @POST("MobileService/ashx/Media/Media.ashx")
    @FormUrlEncoded
    suspend fun getMediaList(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmedialistbyappid",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("skipCount") skipCount: Int,
        @Field("fetchCount") fetchCount: Int,
        @Field("chargeType") chargeType: Int,
        @Field("tagIdList") tagIdList : String
    ): Response<ResponseBody>

    /**
     * 服務10.1.取得手機直播清單
     * @param chargeType 0: All, 1: Paid, 2: Free
     * @return The raw json element which may be array or error object.
     */
    @RecordApi
    @POST("MobileService/ashx/Media/Media.ashx")
    @FormUrlEncoded
    suspend fun getLiveStreamList(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getlivevideolistbyappid",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("skipCount") skipCount: Int,
        @Field("fetchCount") fetchCount: Int,
        @Field("chargeType") chargeType: Int
    ): Response<ResponseBody>

    /**
     * 服務11.取得指定app的會員已購買的影音清單
     */
    @RecordApi
    @GET("MobileService/ashx/Media/Media.ashx")
    suspend fun getPaidMediaListOfMemberByAppId(
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "getpaidmedialistofmemberbyappid",
        @Query("guid") guid: String,
        @Query("appId") appId: Int,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<ResponseBody>
}
