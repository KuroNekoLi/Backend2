package com.cmoney.backend2.data.di

import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.data.service.DataService
import com.cmoney.backend2.data.service.DataWeb
import com.cmoney.backend2.data.service.DataWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

typealias RetrofitProvider = () -> Retrofit

fun dataServiceModule(retrofitProvider: RetrofitProvider) = module {
    single<DataService> {
        retrofitProvider.invoke().create(DataService::class.java)
    }
    single<DataWeb> {
        DataWebImpl(
            service = get(),
            setting = get(BACKEND2_SETTING)
        )
    }
}
