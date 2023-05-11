package com.cmoney.backend2.imagerecognition.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionService
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionWeb
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val imageRecognitionServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ImageRecognitionService::class.java)
    }
    single<ImageRecognitionWeb> {
        ImageRecognitionWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON),
        )
    }
}
