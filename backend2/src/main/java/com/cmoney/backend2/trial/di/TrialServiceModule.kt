package com.cmoney.backend2.trial.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.trial.service.TrialService
import com.cmoney.backend2.trial.service.TrialWeb
import com.cmoney.backend2.trial.service.TrialWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val trialServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(TrialService::class.java)
    }
    single<TrialWeb> {
        TrialWebImpl(
            manager = get(),
            trialService = get(),
            gson = get(BACKEND2_GSON),
        )
    }
}