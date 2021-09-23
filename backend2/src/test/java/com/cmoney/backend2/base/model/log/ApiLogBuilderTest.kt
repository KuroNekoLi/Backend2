package com.cmoney.backend2.base.model.log

import com.cmoney.backend2.base.model.setting.Platform
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Before
import org.junit.Test
import java.util.regex.Pattern

class ApiLogBuilderTest {

    private lateinit var logBuilder: ApiLogBuilder
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
        logBuilder = ApiLogBuilder()
    }

    @Test
    fun parse_the_json_char_is_all_in_ASCII() {
        val log = logBuilder.create(
            appId = 99,
            platform = Platform.Android.code,
            appVersion = "1.0.0",
            manufacturer = "小米",
            model = "紅11",
            osVersion = "Android 12"
        )
        val logJson = gson.toJson(log)
        val pattern = Pattern.compile("^[\\u0000-\\u007F]*\$")
        val matcher = pattern.matcher(logJson)
        Truth.assertThat(matcher.matches())
    }
}