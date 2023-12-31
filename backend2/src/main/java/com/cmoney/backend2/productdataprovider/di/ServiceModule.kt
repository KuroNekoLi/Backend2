package com.cmoney.backend2.productdataprovider.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderService
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWeb
import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val productProvider = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(ProductDataProviderService::class.java)
    }
    single<ProductDataProviderWeb> {
        ProductDataProviderWebImpl(
            manager = get(),
            gson = get(BACKEND2_GSON),
            service = get()
        )
    }
}