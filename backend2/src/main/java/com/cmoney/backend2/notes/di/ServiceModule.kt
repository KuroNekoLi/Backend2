package com.cmoney.backend2.notes.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT_V2
import com.cmoney.backend2.notes.service.NotesService
import com.cmoney.backend2.notes.service.NotesWeb
import com.cmoney.backend2.notes.service.NotesWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val notesServiceModule = module {
    single {
        get<Retrofit>(BACKEND2_RETROFIT_V2).create(NotesService::class.java)
    }
    single<NotesWeb> {
        NotesWebImpl(
            manager = get(),
            service = get(),
            gson = get(BACKEND2_GSON),
        )
    }
}