package com.cmoney.backend2.note_extension.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.note_extension.service.NoteExtensionService
import com.cmoney.backend2.note_extension.service.NoteExtensionWeb
import com.cmoney.backend2.note_extension.service.NoteExtensionWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val noteExtensionServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT).create(NoteExtensionService::class.java)
    }
    single<NoteExtensionWeb> {
        NoteExtensionWebImpl(
            service = get(),
            setting = get(BACKEND2_SETTING),
            gson = get(BACKEND2_GSON)
        )
    }
}