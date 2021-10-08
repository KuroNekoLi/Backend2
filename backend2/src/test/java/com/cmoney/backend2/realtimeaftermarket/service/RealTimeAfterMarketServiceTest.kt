package com.cmoney.backend2.realtimeaftermarket.service

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

class RealTimeAfterMarketServiceTest {

    private lateinit var webServer: MockWebServer
    private lateinit var service: RealTimeAfterMarketService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

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
    fun `getNewTickInfo 成交量超過 Int 範圍時能正確運作`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("{\"IsMarketClosed\":false,\"TickInfoSet\":[{\"RefPrice\":16713.86,\"TotalVolume\":178374067000,\"SingleVolume\":178374067000,\"LimitDown\":\"0\",\"LimitUp\":\"0\",\"TickTime\":1633663310,\"BuyOrSell\":0,\"OpenPrice\":0.0,\"HighestPrice\":0.0,\"LowestPrice\":0.0,\"InvestorStatus\":0,\"PackageDataType\":2,\"Commkey\":\"TWA00\",\"DealPrice\":16688.98,\"PriceChange\":-24.88,\"QuoteChange\":-0.15,\"StatusCode\":3406}],\"IsSuccess\":true,\"ResponseCode\":0,\"ResponseMsg\":\"\"}")
        webServer.enqueue(mockResponse)

        val response = service.getNewTickInfo("", "", "", "", 0, "")
        Truth.assertThat(response.isSuccessful).isTrue()

        val newTickInfo = response.body()!!
        val tickInfoSet = newTickInfo.tickInfoSet!!.single()
        val intRange = Int.MIN_VALUE..Int.MAX_VALUE
        val longRange = Long.MIN_VALUE..Long.MAX_VALUE

        val singleVolume = tickInfoSet.singleVolume!!
        Truth.assertThat(singleVolume in intRange).isFalse()
        Truth.assertThat(singleVolume in longRange).isTrue()

        val totalVolume = tickInfoSet.totalVolume!!
        Truth.assertThat(totalVolume in intRange).isFalse()
        Truth.assertThat(totalVolume in longRange).isTrue()
    }

}
