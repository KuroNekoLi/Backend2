package com.cmoney.backend2.base.model.response.dtno

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.DTNO_DATA_LESS
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DtnoWithErrorTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Test
    fun toRealResponse() {
        val dtnoResponseBodyJson = context.assets.open(DTNO_DATA_LESS)
            .bufferedReader()
            .use {
                it.readText()
            }
        val dtnoWithError = gson.fromJson(dtnoResponseBodyJson, DtnoWithError::class.java)
        val dtnoData = dtnoWithError.toRealResponse()
        val expect = DtnoData(
            title = listOf(
                "股票代號",
                "股票名稱",
                "日期",
                "股票名稱1",
                "產業指數代號",
                "上市上櫃"
            ),
            data = listOf(
                listOf(
                    "0050",
                    "元大台灣50",
                    "20200512",
                    "元大台灣50",
                    "",
                    ""
                ),
                listOf(
                    "0051",
                    "元大中型100",
                    "20200512",
                    "元大中型100",
                    "",
                    ""
                )
            )
        )
        assertThat(dtnoData).isEqualTo(expect)
    }
}