package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.videochannel.service.VideoChannelWeb
import org.koin.core.component.inject

class VideoChannelServiceCase : ServiceCase {
    private val videoChannelWeb by inject<VideoChannelWeb>()

    override suspend fun testAll() {
        videoChannelWeb.apply {
            this.getYoutubeVideos(
                youtubeChannelId = "UCHIIKntN90iABl8jAFUJjtg",
                amount = null,
                time = null
            )
        }
    }
}