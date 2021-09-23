package com.cmoney.backend2.base.model.log

import com.google.gson.Gson
import java.net.URLEncoder

/**
 * 將[ApiLog]轉為字串的物件
 */
class ApiLogParser(private val gson: Gson) {

    private val utf8Name = Charsets.UTF_8.name()

    /**
     * 轉換[ApiLog]物件為字串，由於用途可能為Header需要經過URL Encode
     *
     * @param log Log物件
     * @return 字串
     */
    fun parse(log: ApiLog): String {
        val apiLogJson = gson.toJson(log)
        return URLEncoder.encode(apiLogJson, utf8Name)
    }
}