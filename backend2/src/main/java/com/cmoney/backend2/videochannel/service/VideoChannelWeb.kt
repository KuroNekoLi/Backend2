package com.cmoney.backend2.videochannel.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.videochannel.service.api.YoutubeVideo

interface VideoChannelWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得Youtube頻道影片
     *
     * @param youtubeChannelId 頻道id
     * @param amount 取得資料數
     * @param time 查詢時間
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getYoutubeVideos(
        youtubeChannelId: String,
        amount: Int?,
        time: Long?,
        domain: String = manager.getVideoChannelSettingAdapter().getDomain(),
        url: String = "${domain}api/v1/VideoChannel/${youtubeChannelId}/videos"
    ): Result<List<YoutubeVideo>>
}