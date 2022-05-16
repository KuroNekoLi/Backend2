package com.cmoney.backend2.product.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.product.service.ProductDataProviderService
import com.cmoney.backend2.product.service.ProductDataProviderWeb
import com.cmoney.backend2.product.service.ProductDataProviderWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val productProvider = module {
    single<ProductDataProviderService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(ProductDataProviderService::class.java)
    }
    single<ProductDataProviderWeb> {
        ProductDataProviderWebImpl(get(BACKEND2_GSON), get(), get(BACKEND2_SETTING))
    }
}