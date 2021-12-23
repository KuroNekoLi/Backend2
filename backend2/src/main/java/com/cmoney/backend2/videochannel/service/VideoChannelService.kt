package com.cmoney.backend2.videochannel.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoChannelService {

    /**
     *  取得Youtube頻道影片
     *  @param id 頻道id
     *  @param amount 取得資料數 null預設數量20
     *  @param time 查詢時間 null從最新開始
     */
    @RecordApi
    @GET("api/v1/VideoChannel/{id}/videos")
    suspend fun getYoutubeVideos(
        @Path("id") id: String,
        @Query("amount") amount: Int?,
        @Query("time") time: Long?
    ): Response<ResponseBody>
}