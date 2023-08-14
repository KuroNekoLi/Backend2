package com.cmoney.backend2.brokerdatatransmission.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.brokerdatatransmission.service.api.BrokerAccount
import com.cmoney.backend2.brokerdatatransmission.service.api.Country
import com.cmoney.backend2.brokerdatatransmission.service.api.brokers.BrokerResponseWithError
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.TradeType
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionData
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionResponse
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.imagerecognition.ImageRecognitionResponseBody
import com.cmoney.backend2.brokerdatatransmission.service.api.brokerstockdata.put.BrokerData
import com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey.GetEncryptionKeyResponseWithError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class BrokerDataTransmissionWebImplTest {

    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: BrokerDataTransmissionService
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: BrokerDataTransmissionWeb
    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = BrokerDataTransmissionWebImpl(
            gson = gson,
            service = service,
            manager = manager,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getBrokerDataTransmissionSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getBrokers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/brokers"
        val responseBody = BrokerResponseWithError(
            brokers = emptyList()
        )
        val urlSlot = slot<String>()
        coEvery {
            service.getBrokers(
                url = capture(urlSlot),
                code = any(),
                authToken = any()
            )
        } returns Response.success(responseBody)
        web.getBrokers(Country.TW)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getBrokers 成功`() = testScope.runTest {
        val responseBody = BrokerResponseWithError(
            brokers = emptyList()
        )
        coEvery {
            service.getBrokers(
                url = any(),
                code = any(),
                authToken = any()
            )
        } returns Response.success(responseBody)

        val result = web.getBrokers(Country.TW)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.brokers).isEmpty()
    }

    @Test
    fun `getBrokers 失敗`() = testScope.runTest {
        val response: Response<BrokerResponseWithError> =
            Response.error(400, """{"message": "參數錯誤"}""".toResponseBody())
        coEvery {
            service.getBrokers(
                url = any(),
                code = any(),
                authToken = any()
            )
        } returns response

        val result = web.getBrokers(Country.TW)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        Truth.assertThat((exception as ServerException).code)
            .isEqualTo(Constant.SERVICE_NOT_SUPPORT_ERROR_CODE)
    }

    @Test
    fun `getEncryptionKey_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/encryptionkey"
        val responseBody = GetEncryptionKeyResponseWithError("")
        val urlSlot = slot<String>()
        coEvery {
            service.getEncryptionKey(
                url = capture(urlSlot),
                code = any(),
                authToken = any()
            )
        } returns Response.success(responseBody)
        web.getEncryptionKey(Country.TW)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getEncryptionKey 成功`() = testScope.runTest {
        val responseBody =
            GetEncryptionKeyResponseWithError("""-----BEGIN PUBLIC KEY-----\r\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi5f+LhvlxB32a3AOeIno\r\n/+dhdu92P9IR0P1in6GUNW+vEgzZAZdBNF+EPgsEPRi0tGLYXrx9BJUIHah1ORoY\r\nUgU0PD1ydyJ2cDp/kP8IQ3cDIvXKSYyKNQ2erxFvvOFFvrqoB7QxLQgP+xKkFoz/\r\nbdAAQAjT/4dtRHGd82wZETWcXHqv7mL9KZj1TEvNDu77yu90PhodGtByCmvJjXd8\r\nYi2Nr7esIapsQafFOyyOAYFXE3UtFiHDf19SAVqC4TS7WpVDeBn/+PPNeSrkApVs\r\n0nxXNDpCumuXkqVtcbih3pKF5mrfPaTSlVClNBTXaj2UdQfrjfFCcqIyyWIdnkEc\r\nVQIDAQAB\r\n-----END PUBLIC KEY-----\r\n""")
        coEvery {
            service.getEncryptionKey(
                url = any(),
                code = any(),
                authToken = any()
            )
        } returns Response.success(responseBody)

        val result = web.getEncryptionKey(Country.TW)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.publicKeyCryptography).isNotEmpty()
    }

    @Test
    fun `getEncryptionKey 失敗`() = testScope.runTest {
        val response: Response<GetEncryptionKeyResponseWithError> =
            Response.error(400, """{"message": "參數錯誤"}""".toResponseBody())
        coEvery {
            service.getEncryptionKey(
                url = any(),
                code = any(),
                authToken = any()
            )
        } returns response

        val result = web.getEncryptionKey(Country.TW)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        Truth.assertThat((exception as ServerException).code)
            .isEqualTo(Constant.SERVICE_NOT_SUPPORT_ERROR_CODE)
    }

    @Test
    fun `fetchTransactionHistory_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/fetch/transactionhistory"
        val urlSlot = slot<String>()
        coEvery {
            service.fetchTransactionHistory(
                url = capture(urlSlot),
                body = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)
        web.fetchTransactionHistory(BrokerAccount("", "", "", "", "", "", ""))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `fetchTransactionHistory 成功`() = testScope.runTest {
        coEvery {
            service.fetchTransactionHistory(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)

        val result = web.fetchTransactionHistory(BrokerAccount("", "", "", "", "", "", ""))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `fetchTransactionHistory 失敗`() = testScope.runTest {
        coEvery {
            service.fetchTransactionHistory(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.error(
            400,
            """{"body":{"code":400400,"message":"請確認是否所有資料皆有填寫，填寫完畢後重新匯入或同步庫存。"}}""".toResponseBody()
        )

        val result = web.fetchTransactionHistory(BrokerAccount("", "", "", "", "", "", ""))
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        Truth.assertThat((exception as ServerException).code).isEqualTo(400400)
    }

    @Test
    fun `getUserAgreesImportRecord_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/useragreesimportrecord"
        val urlSlot = slot<String>()
        coEvery {
            service.getUserAgreesImportRecord(
                url = capture(urlSlot),
                authToken = any()
            )
        } returns Response.success(true)
        web.getUserAgreesImportRecord()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getUserAgreesImportRecord 成功`() = testScope.runTest {
        val expect = true

        coEvery {
            service.getUserAgreesImportRecord(
                url = any(),
                authToken = any()
            )
        } returns Response.success(expect)

        val result = web.getUserAgreesImportRecord()
        Truth.assertThat(result.isSuccess).isTrue()

        val actual = result.getOrThrow()
        Truth.assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun `getBrokerStockData_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/brokerstockdata"
        val urlSlot = slot<String>()
        val responseBody = gson.fromJson(
            "[]",
            JsonElement::class.java
        )
        coEvery {
            service.getBrokerStockData(
                url = capture(urlSlot),
                body = any(),
                authToken = any()
            )
        } returns Response.success(responseBody)
        web.getBrokerStockData(Country.TW)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getBrokerStockData 成功`() = testScope.runTest {
        val body = gson.fromJson(
            """[{"brokerId":"9800","brokerShortName":"元大","subBrokerId":"","updateTimeOfUnixTimeSeconds":1640167278,"inStockData":[{"stockID":"2330","stockInfos":[{"tradeType":0,"amount":1000,"tradeTotalCost":600000.0000}]}]}]""",
            JsonElement::class.java
        )

        coEvery {
            service.getBrokerStockData(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.success(body)

        val result = web.getBrokerStockData(Country.TW)
        Truth.assertThat(result.isSuccess).isTrue()

        val responses = result.getOrThrow()
        Truth.assertThat(responses).hasSize(1)

        val response = responses.single()
        Truth.assertThat(response.brokerId).isEqualTo("9800")
        Truth.assertThat(response.subBrokerId).isEmpty()
        Truth.assertThat(response.brokerShortName).isEqualTo("元大")
        Truth.assertThat(response.updateTimeOfUnixTimeSeconds).isEqualTo(1640167278)

        val inStockData = response.inStockData
        Truth.assertThat(inStockData).isNotNull()
        Truth.assertThat(inStockData).hasSize(1)

        val stockData = inStockData!!.single()
        Truth.assertThat(stockData.stockId).isEqualTo("2330")

        val stockInfos = stockData.stockInfos
        Truth.assertThat(stockInfos).isNotNull()
        Truth.assertThat(stockInfos).hasSize(1)

        val stockInfo = stockInfos!!.single()
        Truth.assertThat(stockInfo.tradeType).isEqualTo(TradeType.Spot)
        Truth.assertThat(stockInfo.amount).isEqualTo(1000)
        Truth.assertThat(stockInfo.tradeTotalCost).isWithin(0.00001).of(600000.0)
    }

    @Test
    fun `getBrokerStockData 失敗`() = testScope.runTest {
        coEvery {
            service.getBrokerStockData(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.error(500, """{"message":"UnknowError"}""".toResponseBody())

        val result = web.getBrokerStockData(Country.TW)
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        Truth.assertThat((exception as HttpException).code()).isEqualTo(500)
    }

    @Test
    fun `getBrokerStockDataByImageRecognition_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/brokerstockdata/imagerecongnition"
        val urlSlot = slot<String>()
        val responseBody = ImageRecognitionResponseBody(
            brokerId = null,
            brokerShortName = null,
            subBrokerId = null,
            updateTimeOfUnixTimeSeconds = null,
            inStockData = listOf()
        )
        coEvery {
            service.getBrokerStockDataByImageRecognition(
                url = capture(urlSlot),
                body = any(),
                authToken = any()
            )
        } returns Response.success(200, responseBody)
        web.getBrokerStockDataByImageRecognition(
            country = Country.TW,
            imageRecognitionData = ImageRecognitionData(
                brokerId = "",
                subBrokerId = "",
                encryptedStockDataImages = emptyList(),
                encryptedAesKey = "",
                encryptedAesIv = ""
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getBrokerStockDataByImageRecognition 全部解析成功`() = testScope.runTest {
        val body = gson.fromJson(
            """{"BrokerId":"9800","BrokerShortName":"元大","SubBrokerId":"","UpdateTimeOfUnixTimeSeconds":1640167278,"InStockData":[{"StockID":"2330","StockInfos":[{"TradeType":0,"Amount":1000,"TradeTotalCost":600000.0000}]}]}""",
            ImageRecognitionResponseBody::class.java
        )

        coEvery {
            service.getBrokerStockDataByImageRecognition(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.success(200, body)

        val result = web.getBrokerStockDataByImageRecognition(
            country = Country.TW,
            imageRecognitionData = ImageRecognitionData(
                brokerId = "",
                subBrokerId = "",
                encryptedStockDataImages = emptyList(),
                encryptedAesKey = "",
                encryptedAesIv = ""
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val response = result.getOrThrow()
        Truth.assertThat(response).isInstanceOf(ImageRecognitionResponse.AllRecognition::class.java)

        val data = response.stockData
        Truth.assertThat(data.brokerId).isEqualTo("9800")
        Truth.assertThat(data.brokerShortName).isEqualTo("元大")
        Truth.assertThat(data.subBrokerId).isEmpty()
        Truth.assertThat(data.updateTimeOfUnixTimeSeconds).isEqualTo(1640167278)

        val inStockData = data.inStockData
        Truth.assertThat(inStockData).isNotNull()
        Truth.assertThat(inStockData).hasSize(1)

        val stockData = inStockData!!.single()
        Truth.assertThat(stockData.stockId).isEqualTo("2330")

        val stockInfos = stockData.stockInfos
        Truth.assertThat(stockInfos).isNotNull()
        Truth.assertThat(stockInfos).hasSize(1)

        val stockInfo = stockInfos!!.single()
        Truth.assertThat(stockInfo.tradeType).isEqualTo(TradeType.Spot)
        Truth.assertThat(stockInfo.amount).isEqualTo(1000)
        Truth.assertThat(stockInfo.tradeTotalCost).isWithin(0.00001).of(600000.0)
        Truth.assertThat(stockInfo.cashDividend).isNull()
        Truth.assertThat(stockInfo.stockDividend).isNull()
    }

    @Test
    fun `getBrokerStockDataByImageRecognition 部分解析成功`() = testScope.runTest {
        val body = gson.fromJson(
            """{"BrokerId":"9800","BrokerShortName":"元大","SubBrokerId":"","UpdateTimeOfUnixTimeSeconds":1640167278,"InStockData":[{"StockID":"2330","StockInfos":[{"TradeType":0,"Amount":1000,"TradeTotalCost":600000.0000}]}]}""",
            ImageRecognitionResponseBody::class.java
        )

        coEvery {
            service.getBrokerStockDataByImageRecognition(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.success(206, body)

        val result = web.getBrokerStockDataByImageRecognition(
            country = Country.TW,
            imageRecognitionData = ImageRecognitionData(
                brokerId = "",
                subBrokerId = "",
                encryptedStockDataImages = emptyList(),
                encryptedAesKey = "",
                encryptedAesIv = ""
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val response = result.getOrThrow()
        Truth.assertThat(response).isInstanceOf(ImageRecognitionResponse.PartialRecognition::class.java)

        val data = response.stockData
        Truth.assertThat(data.brokerId).isEqualTo("9800")
        Truth.assertThat(data.brokerShortName).isEqualTo("元大")
        Truth.assertThat(data.subBrokerId).isEmpty()
        Truth.assertThat(data.updateTimeOfUnixTimeSeconds).isEqualTo(1640167278)

        val inStockData = data.inStockData
        Truth.assertThat(inStockData).isNotNull()
        Truth.assertThat(inStockData).hasSize(1)

        val stockData = inStockData!!.single()
        Truth.assertThat(stockData.stockId).isEqualTo("2330")

        val stockInfos = stockData.stockInfos
        Truth.assertThat(stockInfos).isNotNull()
        Truth.assertThat(stockInfos).hasSize(1)

        val stockInfo = stockInfos!!.single()
        Truth.assertThat(stockInfo.tradeType).isEqualTo(TradeType.Spot)
        Truth.assertThat(stockInfo.amount).isEqualTo(1000)
        Truth.assertThat(stockInfo.tradeTotalCost).isWithin(0.00001).of(600000.0)
        Truth.assertThat(stockInfo.cashDividend).isNull()
        Truth.assertThat(stockInfo.stockDividend).isNull()
    }

    @Test
    fun `getBrokerStockDataByImageRecognition 失敗`() = testScope.runTest {
        coEvery {
            service.getBrokerStockDataByImageRecognition(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.error(406, "".toResponseBody())

        val result = web.getBrokerStockDataByImageRecognition(
            country = Country.TW,
            imageRecognitionData = ImageRecognitionData(
                brokerId = "",
                subBrokerId = "",
                encryptedStockDataImages = emptyList(),
                encryptedAesKey = "",
                encryptedAesIv = ""
            )
        )
        Truth.assertThat(result.isSuccess).isFalse()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        Truth.assertThat((exception as HttpException).code()).isEqualTo(406)
    }

    @Test
    fun `putBrokerStockData_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/brokerstockdata"
        val urlSlot = slot<String>()
        coEvery {
            service.putBrokerStockData(
                url = capture(urlSlot),
                body = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)
        web.putBrokerStockData(Country.TW, BrokerData("", "", emptyList()))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `putBrokerStockData 成功`() = testScope.runTest {
        coEvery {
            service.putBrokerStockData(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)

        val result = web.putBrokerStockData(Country.TW, BrokerData("", "", emptyList()))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `putBrokerStockData 失敗`() = testScope.runTest {
        coEvery {
            service.putBrokerStockData(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.error(500, """{"message":"UnknowError"}""".toResponseBody())

        val result = web.putBrokerStockData(Country.TW, BrokerData("", "", emptyList()))
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        Truth.assertThat((exception as HttpException).code()).isEqualTo(500)
    }

    @Test
    fun `deleteBrokerStockData_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/api/brokerstockdata"
        val urlSlot = slot<String>()
        coEvery {
            service.deleteBrokerStockData(
                url = capture(urlSlot),
                body = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteBrokerStockData(Country.TW, emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `deleteBrokerStockData 成功`() = testScope.runTest {
        coEvery {
            service.deleteBrokerStockData(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)

        val result = web.deleteBrokerStockData(Country.TW, emptyList())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `deleteBrokerStockData 失敗`() = testScope.runTest {
        coEvery {
            service.deleteBrokerStockData(
                url = any(),
                body = any(),
                authToken = any()
            )
        } returns Response.error(500, """{"message":"未知的錯誤"}""".toResponseBody())

        val result = web.deleteBrokerStockData(Country.TW, emptyList())
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        Truth.assertThat((exception as HttpException).code()).isEqualTo(500)
    }

    @Test
    fun `getConsents_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/Consent"
        val urlSlot = slot<String>()
        val responseBody = gson.fromJson(
            """[{"brokerId":"9800","hasSigned":true}]""",
            JsonElement::class.java
        )
        coEvery {
            service.getConsents(
                url = capture(urlSlot),
                code = any(),
                authToken = any()
            )
        } returns Response.success(responseBody)
        web.getConsents(Country.TW)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getConsents 成功`() = testScope.runTest {
        val body = gson.fromJson(
            """[{"brokerId":"9800","hasSigned":true}]""",
            JsonElement::class.java
        )
        coEvery {
            service.getConsents(
                url = any(),
                code = any(),
                authToken = any()
            )
        } returns Response.success(body)

        val result = web.getConsents(Country.TW)
        Truth.assertThat(result.isSuccess).isTrue()

        val consents = result.getOrThrow()
        Truth.assertThat(consents).isNotEmpty()

        val item = consents.single()
        Truth.assertThat(item.brokerId).isEqualTo("9800")
        Truth.assertThat(item.hasSigned).isEqualTo(true)
    }

    @Test
    fun `getConsents 失敗`() = testScope.runTest {
        val response: Response<JsonElement> =
            Response.error(400, """{"message": "參數錯誤"}""".toResponseBody())
        coEvery {
            service.getConsents(
                url = any(),
                code = any(),
                authToken = any()
            )
        } returns response

        val result = web.getConsents(Country.TW)
        Truth.assertThat(result.isFailure).isTrue()

        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        Truth.assertThat((exception as ServerException).code)
            .isEqualTo(Constant.SERVICE_NOT_SUPPORT_ERROR_CODE)
    }

    @Test
    fun `signConsent_check url`() = testScope.runTest {
        val brokerId = "9800"
        val expect = "${EXCEPT_DOMAIN}BrokerDataTransmission/Consent/$brokerId"
        val urlSlot = slot<String>()
        coEvery {
            service.signConsent(
                url = capture(urlSlot),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)
        web.signConsent(brokerId = brokerId)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `signConsent 成功`() = testScope.runTest {
        coEvery {
            service.signConsent(
                url = any(),
                authToken = any()
            )
        } returns Response.success<Void>(204, null)

        val result = web.signConsent("9800")
        Truth.assertThat(result.isSuccess).isTrue()
    }

}
