package com.cmoney.backend2.base.di

import android.content.Context
import com.cmoney.backend2.base.model.calladapter.RecordApiLogCallAdapterFactory
import com.cmoney.backend2.base.model.setting.BackendSettingSharedPreference
import com.cmoney.backend2.base.model.setting.DefaultSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.base.model.typeadapter.ULongTypeAdapter
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.ConnectionSpec.Companion.COMPATIBLE_TLS
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * DI的模組定義
 */
private const val DEFAULT_URL = "https://www.cmoney.tw/"
val BACKEND2_OKHTTP = named("backend2_okhttp")
val BACKEND2_GSON = named("backend2_gson")
val BACKEND2_GSON_NON_SERIALIZE_NULLS = named("backend2_gson_non_serialize_nulls")
val BACKEND2_RETROFIT = named("backend2_retrofit")
val BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS =
    named("backend2_retrofit_with_gson_non_serialize_nulls")
val BACKEND2_SETTING = named("backend2_setting")

val backendBaseModule = module {
    single {
        BackendSettingSharedPreference(
            androidContext().getSharedPreferences(
                BackendSettingSharedPreference.TAG,
                Context.MODE_PRIVATE
            )
        )
    }
    single<Setting>(BACKEND2_SETTING) {
        DefaultSetting(backendSettingSharedPreference = get())
    }
    single(BACKEND2_OKHTTP) {
        OkHttpClient.Builder()
            .connectionSpecs(listOf(COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
            .addChangeDomainInterceptor()
            .addCMoneyApiTraceContextInterceptor()
            .addLogInterceptor()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .callTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .build()
    }
    single<Gson>(BACKEND2_GSON) {
        GsonBuilder()
            .serializeNulls()
            .setLenient()
            .registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
            .create()
    }
    single<Gson>(BACKEND2_GSON_NON_SERIALIZE_NULLS) {
        GsonBuilder()
            .setLenient()
            .registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
            .create()
    }
    single<Retrofit>(BACKEND2_RETROFIT) {
        Retrofit.Builder()
            .baseUrl(DEFAULT_URL)
            .client(get(BACKEND2_OKHTTP))
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON)))
            .addCallAdapterFactory(
                RecordApiLogCallAdapterFactory(
                    setting = get(BACKEND2_SETTING),
                    logDataRecorder = LogDataRecorder.getInstance()
                )
            )
            .build()
    }
    single<Retrofit>(BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS) {
        Retrofit.Builder()
            .baseUrl(DEFAULT_URL)
            .client(get(BACKEND2_OKHTTP))
            .addConverterFactory(GsonConverterFactory.create(get(BACKEND2_GSON_NON_SERIALIZE_NULLS)))
            .addCallAdapterFactory(
                RecordApiLogCallAdapterFactory(
                    setting = get(BACKEND2_SETTING),
                    logDataRecorder = LogDataRecorder.getInstance()
                )
            )
            .build()
    }
}