package com.cmoney.backend2.note_extension.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.note_extension.service.NoteExtensionService
import com.cmoney.backend2.note_extension.service.NoteExtensionWeb
import com.cmoney.backend2.note_extension.service.NoteExtensionWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val noteExtensionServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(NoteExtensionService::class.java)
    }
    single<NoteExtensionWeb> {
        NoteExtensionWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON)
        )
    }
}