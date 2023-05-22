package com.cmoney.backend2.realtimeaftermarket.service

import com.cmoney.backend2.base.model.typeadapter.ULongTypeAdapter
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalUnsignedTypes
class RealTimeAfterMarketServiceTest {

    private lateinit var webServer: MockWebServer
    private lateinit var service: RealTimeAfterMarketService

    private val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .setPrettyPrinting()
        .registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
        .create()

    @Before
    fun setup() {
        webServer = MockWebServer()
        webServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(webServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        service = retrofit.create(RealTimeAfterMarketService::class.java)
    }

    @After
    fun teardown() {
        webServer.shutdown()
    }

    @Test
    fun `getNewTickInfo 成交量長度為 ULong 時能正確運作`() = runBlocking {
        val testValue = ULong.MAX_VALUE
        val mockResponse = MockResponse()
            .setBody("{\"IsMarketClosed\":false,\"TickInfoSet\":[{\"RefPrice\":16713.86,\"TotalVolume\":$testValue,\"SingleVolume\":$testValue,\"LimitDown\":\"0\",\"LimitUp\":\"0\",\"TickTime\":1633663310,\"BuyOrSell\":0,\"OpenPrice\":0.0,\"HighestPrice\":0.0,\"LowestPrice\":0.0,\"InvestorStatus\":0,\"PackageDataType\":2,\"Commkey\":\"TWA00\",\"DealPrice\":16688.98,\"PriceChange\":-24.88,\"QuoteChange\":-0.15,\"StatusCode\":3406}],\"IsSuccess\":true,\"ResponseCode\":0,\"ResponseMsg\":\"\"}")
        webServer.enqueue(mockResponse)

        val response = service.getNewTickInfo("","", "", "", "", 0, "")
        Truth.assertThat(response.isSuccessful).isTrue()

        val newTickInfo = response.body()!!
        val tickInfoSet = newTickInfo.tickInfoSet!!.single()

        val singleVolume = tickInfoSet.singleVolume!!
        Truth.assertThat(singleVolume == testValue).isTrue()

        val totalVolume = tickInfoSet.totalVolume!!
        Truth.assertThat(totalVolume == testValue).isTrue()
    }

}
