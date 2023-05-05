package com.cmoney.backend2.sample.di

import com.cmoney.backend2.sample.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sampleViewModule = module {
    viewModel {
        MainViewModel(
            manager = get(),
            identityProviderWeb = get()
        )
    }
}