package com.cmoney.backend2.videochannel.service

import com.cmoney.backend2.videochannel.service.api.YoutubeVideo

interface VideoChannelWeb {
    /**
     *  取得Youtube頻道影片
     *  @param youtubeChannelId 頻道id
     *  @param amount 取得資料數
     *  @param time 查詢時間
     */
    suspend fun getYoutubeVideos(
        youtubeChannelId: String,
        amount: Int?,
        time: Long?
    ): Result<List<YoutubeVideo>>
}