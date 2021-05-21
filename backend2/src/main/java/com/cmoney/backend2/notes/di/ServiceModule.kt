package com.cmoney.backend2.notes.di

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.notes.service.NotesService
import com.cmoney.backend2.notes.service.NotesWeb
import com.cmoney.backend2.notes.service.NotesWebImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val notesServiceModule = module {
    single<NotesService> {
        get<Retrofit>(BACKEND2_RETROFIT).create(NotesService::class.java)
    }
    single<NotesWeb> {
        NotesWebImpl(
            service = get(),
            gson = get(BACKEND2_GSON),
            setting = get(BACKEND2_SETTING)
        )
    }
}