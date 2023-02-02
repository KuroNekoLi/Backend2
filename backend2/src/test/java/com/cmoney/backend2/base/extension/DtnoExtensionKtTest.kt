package com.cmoney.backend2.base.extension

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.DTNO_DATA_COMPLICATED
import com.cmoney.backend2.base.DTNO_DATA_LESS
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DtnoExtensionKtTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var dtnoResponseBodyJson: String
    private lateinit var dtnoWithError: DtnoWithError
    private lateinit var dtnoData: DtnoData

    @Before
    fun setUp() {
        dtnoResponseBodyJson = context.assets.open(DTNO_DATA_LESS)
            .bufferedReader()
            .use {
                it.readText()
            }
        dtnoWithError = gson.fromJson(dtnoResponseBodyJson, DtnoWithError::class.java)
        dtnoData = dtnoWithError.toRealResponse()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getTitleIndexMapping() {
        val expect = mapOf(
            "股票代號" to 0,
            "股票名稱" to 1,
            "日期" to 2,
            "股票名稱1" to 3,
            "產業指數代號" to 4,
            "上市上櫃" to 5
        )
        val actual = dtnoData.getTitleIndexMapping()
        assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun toJsonArray() {
        val expect = """
            [{"股票代號":"0050","股票名稱":"元大台灣50","日期":"20200512","股票名稱1":"元大台灣50","產業指數代號":"","上市上櫃":""},{"股票代號":"0051","股票名稱":"元大中型100","日期":"20200512","股票名稱1":"元大中型100","產業指數代號":"","上市上櫃":""}]
            """.trimIndent()
        val actual = dtnoData.toJsonArrayString()
        assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun toListOfSomething() {
        val expect = listOf(
            SomethingDao(
                id = "0050",
                name = "元大台灣50",
                date = "20200512",
                name2 = "元大台灣50",
                industryId = "",
                goPublic = ""
            ),
            SomethingDao(
                id = "0051",
                name = "元大中型100",
                date = "20200512",
                name2 = "元大中型100",
                industryId = "",
                goPublic = ""
            )
        )
        val actual = dtnoData.toListOfSomething<SomethingDao>(gson)
        assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun toListOfTypeTest() {

        val complicatedDtnoDataJson = context.assets.open(DTNO_DATA_COMPLICATED)
            .bufferedReader()
            .use {
                it.readText()
            }
        val complicatedDtnoWithError = gson.fromJson(complicatedDtnoDataJson, DtnoWithError::class.java)
        val complicatedDtnoData = complicatedDtnoWithError.toRealResponse()
        val expect = listOf(
            ComplicatedDao(
                name = "元大台灣50",
                upAndDown = 20.55,
                time = 1675331681000,
                time2 = 1675331600000,
                isShow = false
            ),
            ComplicatedDao(
                name = "元大中型100",
                upAndDown = 111.1,
                time = 1675331680000,
                time2 = 1675331610000,
                isShow = true
            )
        )
        val actual = complicatedDtnoData.toListOfType<ComplicatedDao>(gson)
        assertThat(actual).isEqualTo(expect)
    }
}