package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.commonuse.service.api.historyevent.HistoryEvents
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreference
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
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
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class CommonUseWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: CommonUseService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: CommonUseWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = CommonUseWebImpl(
            manager = manager,
            commonUseService = service,
            gson = gson,
            dispatcherProvider = TestDispatcherProvider()
        )
        coEvery {
            manager.getCommonUseSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getRemoteConfigLabel_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}commonuse/graphql"
        val urlSlot = slot<String>()
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "remoteConfigLabel": "A"
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(url = capture(urlSlot), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        web.getRemoteConfigLabel()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getRemoteConfigLabel_success with non null response`() = testScope.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "remoteConfigLabel": "A"
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(any(), any(), any())
        } returns Response.success(response.asJsonObject)

        val result = web.getRemoteConfigLabel()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo("A")
    }

    @Test
    fun `getRemoteConfigLabel_success with null response`() = testScope.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "remoteConfigLabel": null
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        val result = web.getRemoteConfigLabel()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEmpty()
    }

    @Test
    fun `updateInvestmentPreference_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}commonuse/graphql"
        val urlSlot = slot<String>()
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "updateMember": {
                      "updateInvestmentRiskPreference": [
                        1,
                        2,
                        3
                      ]
                    }
                  }
                }
            """.trimMargin()
        )

        coEvery {
            service.query(url = capture(urlSlot), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        web.updateInvestmentPreference(investmentPreferenceType = InvestmentPreferenceType.All)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `updateInvestmentPreference success`() = testScope.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "updateMember": {
                      "updateInvestmentRiskPreference": [
                        1,
                        2,
                        3
                      ]
                    }
                  }
                }
            """.trimMargin()
        )

        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        val result =
            web.updateInvestmentPreference(investmentPreferenceType = InvestmentPreferenceType.All)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(InvestmentPreferenceType.All)
    }

    @Test
    fun `updateInvestmentPreference failure`() = testScope.runTest {
        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.error(400, "".toResponseBody())

        val result =
            web.updateInvestmentPreference(investmentPreferenceType = InvestmentPreferenceType.All)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getInvestmentPreference_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}commonuse/graphql"
        val urlSlot = slot<String>()
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "investmentRiskPreferences": [
                        {
                          "id": 1,
                          "name": "短線賺價差",
                          "isChosen": true
                        }
                      ]
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(url = capture(urlSlot), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        web.getInvestmentPreferences()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getInvestmentPreference success`() = testScope.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "member": {
                      "investmentRiskPreferences": [
                        {
                          "id": 1,
                          "name": "短線賺價差",
                          "isChosen": true
                        }
                      ]
                    }
                  }
                }
            """.trimMargin()
        )
        val responseBody = listOf(
            InvestmentPreference(
                id = 1,
                name = "短線賺價差",
                isChosen = true
            )
        )

        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        val result = web.getInvestmentPreferences()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(responseBody)
    }

    @Test
    fun `gegInvestmentPreference failure`() = testScope.runTest {
        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.error(400, "".toResponseBody())

        val result = web.getInvestmentPreferences()
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getCommodityHistoryEvent_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}commonuse/graphql"
        val urlSlot = slot<String>()
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "notif": {
                      "majorEvents": {
                        "edges": [
                            {
                                "node": {
                                  "commKey": "5880",
                                  "commName": "合庫金",
                                  "body": "合庫金(5880)因除息，2022年08月04日星期四訂為融券最後回補日，尚餘1個交易日，2022年08月10日星期三開放融券。",
                                  "link": "https://www.cmoney.tw/app/landing_page/chipk?page=stock&subpage=0&stockid=5880&stockname=合庫金",
                                  "notifyTime": 1659522320
                                }
                            }
                        ],
                        "pageInfo": {
                          "hasNextPage": true,
                          "endCursor": "1"
                        }
                      }
                    }
                  }
                }
            """.trimMargin()
        )
        coEvery {
            service.query(url = capture(urlSlot), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        web.getCommodityHistoryEvent(commodityIds = listOf("5880"))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getCommodityHistoryEvent success with endCursor null`() = testScope.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "notif": {
                      "majorEvents": {
                        "edges": [
                            {
                                "node": {
                                  "commKey": "5880",
                                  "commName": "合庫金",
                                  "body": "合庫金(5880)因除息，2022年08月04日星期四訂為融券最後回補日，尚餘1個交易日，2022年08月10日星期三開放融券。",
                                  "link": "https://www.cmoney.tw/app/landing_page/chipk?page=stock&subpage=0&stockid=5880&stockname=合庫金",
                                  "notifyTime": 1659522320
                                }
                            }
                        ],
                        "pageInfo": {
                          "hasNextPage": true,
                          "endCursor": "1"
                        }
                      }
                    }
                  }
                }
            """.trimMargin()
        )
        val expectResult = HistoryEvents(
            events = listOf(
                HistoryEvents.Event(
                    content = HistoryEvents.Event.Content(
                        commKey = "5880",
                        commName = "合庫金",
                        body = "合庫金(5880)因除息，2022年08月04日星期四訂為融券最後回補日，尚餘1個交易日，2022年08月10日星期三開放融券。",
                        link = "https://www.cmoney.tw/app/landing_page/chipk?page=stock&subpage=0&stockid=5880&stockname=合庫金",
                        notifyTime = 1659522320
                    )
                )
            ),
            pageInfo = HistoryEvents.PageInfo(
                hasNextPage = true,
                endCursor = "1"
            )
        )

        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        val result = web.getCommodityHistoryEvent(commodityIds = listOf("5880"))
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(expectResult)
    }

    @Test
    fun `getCommodityHistoryEvent success with endCursor`() = testScope.runTest {
        val response = JsonParser.parseString(
            """
                {
                  "data": {
                    "notif": {
                      "majorEvents": {
                        "edges": [
                            {
                                "node": {
                                  "commKey": "5880",
                                  "commName": "合庫金",
                                  "body": "合庫金(5880)因除息，2022年08月04日星期四訂為融券最後回補日，尚餘1個交易日，2022年08月10日星期三開放融券。",
                                  "link": "https://www.cmoney.tw/app/landing_page/chipk?page=stock&subpage=0&stockid=5880&stockname=合庫金",
                                  "notifyTime": 1659522320
                                }
                            }
                        ],
                        "pageInfo": {
                          "hasNextPage": true,
                          "endCursor": "1"
                        }
                      }
                    }
                  }
                }
            """.trimMargin()
        )
        val expectResult = HistoryEvents(
            events = listOf(
                HistoryEvents.Event(
                    content = HistoryEvents.Event.Content(
                        commKey = "5880",
                        commName = "合庫金",
                        body = "合庫金(5880)因除息，2022年08月04日星期四訂為融券最後回補日，尚餘1個交易日，2022年08月10日星期三開放融券。",
                        link = "https://www.cmoney.tw/app/landing_page/chipk?page=stock&subpage=0&stockid=5880&stockname=合庫金",
                        notifyTime = 1659522320
                    )
                )
            ),
            pageInfo = HistoryEvents.PageInfo(
                hasNextPage = true,
                endCursor = "1"
            )
        )

        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.success(response.asJsonObject)

        val result = web.getCommodityHistoryEvent(commodityIds = listOf("5880"), endCursor = "8")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(expectResult)
    }

    @Test
    fun `getCommodityHistoryEvent failure`() = testScope.runTest {
        coEvery {
            service.query(url = any(), authorization = any(), query = any())
        } returns Response.error(400, "".toResponseBody())

        val result = web.getCommodityHistoryEvent(commodityIds = listOf("5880"))
        Truth.assertThat(result.isSuccess).isFalse()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}
