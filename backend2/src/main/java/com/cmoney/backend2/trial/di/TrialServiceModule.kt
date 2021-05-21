package com.cmoney.backend2.trial.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.trial.service.TrialService
import com.cmoney.backend2.trial.service.TrialWeb
import com.cmoney.backend2.trial.service.TrialWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val trialServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(TrialService::class.java)
    }
    single<TrialWeb> {
        TrialWebImpl(get(BACKEND2_GSON), get(BACKEND2_SETTING), get())
    }
}