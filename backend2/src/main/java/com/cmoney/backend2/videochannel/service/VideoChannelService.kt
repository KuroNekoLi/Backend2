package com.cmoney.backend2.videochannel.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface VideoChannelService {

    /**
     *  取得Youtube頻道影片
     *
     *  @param amount 取得資料數 null預設數量20
     *  @param time 查詢時間 null從最新開始
     */
    @RecordApi
    @GET
    suspend fun getYoutubeVideos(
        @Url url: String,
        @Query("amount") amount: Int?,
        @Query("time") time: Long?
    ): Response<ResponseBody>
}