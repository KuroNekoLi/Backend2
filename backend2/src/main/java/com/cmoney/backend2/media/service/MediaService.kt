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
    @RecordApi(cmoneyAction = "getmediadetail")
    @GET
    suspend fun getMediaDetail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "getmediadetail",
        @Query("appId") appId: Int,
        @Query("guid") guid: String,
        @Query("mediaId") mediaId: Long,
        @Query("device") device: Int
    ): Response<GetMediaDetailResponseWithError>

    /**
     * 服務3.取得網頁付費影音清單
     */
    @RecordApi(cmoneyAction = "getpaidmedialist")
    @GET
    suspend fun getPaidMediaList(
        @Url url: String,
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
    @RecordApi(cmoneyAction = "getpaidlivevideolist")
    @GET
    suspend fun getPaidLiveList(
        @Url url: String,
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
    @RecordApi(cmoneyAction = "getmediainfo")
    @POST
    @FormUrlEncoded
    suspend fun getMediaInfo(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmediainfo",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("mediaId") mediaId: Long
    ): Response<MediaInfoWithError>

    /**
     * 服務6.取得會員已購買的影音清單
     *
     * @return The raw json element which may be array or error object.
     */
    @RecordApi(cmoneyAction = "getpaidmedialistofmember")
    @GET
    suspend fun getPaidMediaListOfMember(
        @Url url: String,
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
    @RecordApi(cmoneyAction = "getmediapurchaseurl")
    @POST
    @FormUrlEncoded
    suspend fun getMediaPurchaseUrl(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmediapurchaseurl",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("mediaId") mediaId: Long
    ): Response<GetMediaUrlResponseBody>

    /**
     * 服務8.取得影音播放網址
     */
    @RecordApi(cmoneyAction = "getmediaplayurl")
    @POST
    @FormUrlEncoded
    suspend fun getMediaUrl(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getmediaplayurl",
        @Field("appId") appId: Int,
        @Field("guid") guid: String,
        @Field("mediaId") mediaId: Long
    ): Response<GetMediaUrlResponseBody>

    /**
     * 服務9.1.取得手機影音清單
     * 20210615 新增 tagIdList
     *
     * @param chargeType 0: All, 1: Paid, 2: Free
     * @return The raw json element which may be array or error object.
     */
    @RecordApi(cmoneyAction = "getmedialistbyappid")
    @POST
    @FormUrlEncoded
    suspend fun getMediaList(
        @Url url: String,
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
     *
     * @param chargeType 0: All, 1: Paid, 2: Free
     * @return The raw json element which may be array or error object.
     */
    @RecordApi(cmoneyAction = "getlivevideolistbyappid")
    @POST
    @FormUrlEncoded
    suspend fun getLiveStreamList(
        @Url url: String,
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
    @RecordApi(cmoneyAction = "getpaidmedialistofmemberbyappid")
    @GET
    suspend fun getPaidMediaListOfMemberByAppId(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("action") action: String = "getpaidmedialistofmemberbyappid",
        @Query("guid") guid: String,
        @Query("appId") appId: Int,
        @Query("skipCount") skipCount: Int,
        @Query("fetchCount") fetchCount: Int
    ): Response<ResponseBody>
}
