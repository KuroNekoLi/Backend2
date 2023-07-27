package com.cmoney.backend2.videochannel.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.videochannel.service.VideoChannelService
import com.cmoney.backend2.videochannel.service.VideoChannelWeb
import com.cmoney.backend2.videochannel.service.VideoChannelWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val videoChannelServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(VideoChannelService::class.java)
    }
    single<VideoChannelWeb> {
        VideoChannelWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}
