package com.cmoney.backend2.sample.di

import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.sample.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        MainViewModel(
            backendSetting = get(BACKEND2_SETTING),
            identityProviderWeb = get()
        )
    }
}