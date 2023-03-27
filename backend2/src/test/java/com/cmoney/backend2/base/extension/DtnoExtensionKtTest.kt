package com.cmoney.backend2.base.extension

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.DTNO_DATA_COMPLICATED
import com.cmoney.backend2.base.DTNO_DATA_COMPLICATED_V2
import com.cmoney.backend2.base.DTNO_DATA_COMPLICATED_V3
import com.cmoney.backend2.base.DTNO_DATA_LESS
import com.cmoney.backend2.base.extension.data.ComplicatedDao
import com.cmoney.backend2.base.extension.data.ComplicatedDaoLackAnnotation
import com.cmoney.backend2.base.extension.data.ComplicatedDaoWithOtherField
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.cmoney.backend2.base.model.response.dtno.DtnoWithError
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
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
        val actual = dtnoData.toListOfType<SomethingDao>(gson = gson)
        assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun toListOfType_all_has_value_success() {
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

    @Test
    fun toListOfType_all_field_is_null_success() {
        val testDtnoData = DtnoData(
            title = listOf(
                "名稱",
                "漲跌幅",
                "時間",
                "是否顯示",
                "時間2"
            ),
            data = listOf(
                emptyList(),
                emptyList()
            )
        )
        val expected = listOf(
            ComplicatedDao(name = null, upAndDown = null, time = null, time2 = null, isShow = null),
            ComplicatedDao(name = null, upAndDown = null, time = null, time2 = null, isShow = null)
        )
        val actual = testDtnoData.toListOfType<ComplicatedDao>(gson)
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun toListOfType_raw_data_field_is_not_the_same_to_type_success() {
        val testDtnoData = DtnoData(
            title = listOf(
                "名稱",
                "漲跌幅",
                "時間",
                "時間2"
            ),
            data = listOf(
                emptyList(),
                emptyList()
            )
        )
        val expected = listOf(
            ComplicatedDao(name = null, upAndDown = null, time = null, time2 = null, isShow = null),
            ComplicatedDao(name = null, upAndDown = null, time = null, time2 = null, isShow = null)
        )
        val actual = testDtnoData.toListOfType<ComplicatedDao>(gson)
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toListOfType_field_lack_annotation_exception() {
        val testDtnoData = DtnoData(
            title = listOf(
                "名稱",
                "漲跌幅",
                "時間",
                "是否顯示",
                "時間2"
            ),
            data = listOf(
                emptyList(),
                emptyList()
            )
        )
        testDtnoData.toListOfType<ComplicatedDaoLackAnnotation>(gson)
    }

    @Test
    fun toListOfType_type_with_non_constructor_field_is_null_success() {
        val testDtnoData = DtnoData(
            title = listOf(
                "名稱",
                "漲跌幅",
                "時間",
                "是否顯示",
                "時間2"
            ),
            data = listOf(
                emptyList(),
                emptyList()
            )
        )
        val expected = listOf(
            ComplicatedDaoWithOtherField(name = null, upAndDown = null, time = null, time2 = null, isShow = null),
            ComplicatedDaoWithOtherField(name = null, upAndDown = null, time = null, time2 = null, isShow = null)
        )
        val actual = testDtnoData.toListOfType<ComplicatedDaoWithOtherField>(gson)
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun toListOfType_private_class_all_has_value_success() {
        val complicatedDtnoDataJson = context.assets.open(DTNO_DATA_COMPLICATED)
            .bufferedReader()
            .use {
                it.readText()
            }
        val complicatedDtnoWithError = gson.fromJson(complicatedDtnoDataJson, DtnoWithError::class.java)
        val complicatedDtnoData = complicatedDtnoWithError.toRealResponse()
        val expect = listOf(
            PrivateComplicatedDao(
                name = "元大台灣50",
                upAndDown = 20.55,
                time = 1675331681000,
                time2 = 1675331600000,
                isShow = false
            ),
            PrivateComplicatedDao(
                name = "元大中型100",
                upAndDown = 111.1,
                time = 1675331680000,
                time2 = 1675331610000,
                isShow = true
            )
        )
        val actual = complicatedDtnoData.toListOfType<PrivateComplicatedDao>(gson)
        assertThat(actual).isEqualTo(expect)
    }

    @Test
    fun `toListOfType property support alternate`() {
        val complicatedDtnoDataJson = context.assets.open(DTNO_DATA_COMPLICATED_V2)
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

    @Test
    fun `toListOfType property first use value to parse`() {
        val complicatedDtnoDataJson = context.assets.open(DTNO_DATA_COMPLICATED_V3)
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

    /**
     * 測試用資料
     *
     * @property name
     * @property upAndDown
     * @property time
     * @property time2
     * @property isShow
     */
    private data class PrivateComplicatedDao(
        @SerializedName("名稱")
        val name: String?,
        @SerializedName("漲跌幅")
        val upAndDown: Double?,
        @SerializedName("時間")
        val time: Long?,
        @SerializedName("時間2")
        val time2: Long?,
        @SerializedName("是否顯示")
        val isShow: Boolean?
    )
}