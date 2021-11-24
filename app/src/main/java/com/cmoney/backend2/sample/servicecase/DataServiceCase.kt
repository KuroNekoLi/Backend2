package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.di.BACKEND2_GSON
import com.cmoney.backend2.data.di.RetrofitProvider
import com.cmoney.backend2.data.di.dataServiceModule
import com.cmoney.backend2.data.service.DataWeb
import com.cmoney.backend2.data.extension.toListOfSomething
import com.cmoney.backend2.sample.extension.logResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.inject
import org.koin.core.module.Module
import java.util.concurrent.TimeUnit

class DataServiceCase : ServiceCase {

    private val dataWeb by inject<DataWeb>()
    private val gson by inject<Gson>(BACKEND2_GSON)

    override suspend fun testAll() {
        dataWeb.getFundIdData(190, "1")
            .mapCatching { data ->
                data.toListOfSomething<FundId190Response>(gson)
            }
            .logResponse(TAG)
    }

    private data class FundId190Response(
        @SerializedName("日期")
        val date: String = ""
    )

    companion object {
        private const val TAG = "Data"

        fun getDataWebModule(baseUrl: String, retrofitProvider: RetrofitProvider): Module {
            return dataServiceModule(
                retrofitProvider = {
                    val retrofit = retrofitProvider.invoke()

                    val logInterceptor = HttpLoggingInterceptor()
                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    val okHttpClient = OkHttpClient.Builder()
                        .connectionSpecs(
                            listOf(
                                ConnectionSpec.COMPATIBLE_TLS,
                                ConnectionSpec.CLEARTEXT
                            )
                        )
                        .addInterceptor(logInterceptor)
                        .connectTimeout(30L, TimeUnit.SECONDS)
                        .callTimeout(30L, TimeUnit.SECONDS)
                        .readTimeout(30L, TimeUnit.SECONDS)
                        .writeTimeout(30L, TimeUnit.SECONDS)
                        .build()

                    retrofit.newBuilder()
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .build()
                }
            )
        }
    }

}
