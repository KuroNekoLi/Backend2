package com.cmoney.backend2.chat.extension

import org.json.JSONException
import org.json.JSONObject

internal inline fun <T> JSONObject.safeRun(block: JSONObject.() -> T): T? {
    return try {
        block.invoke(this)
    } catch (e: JSONException) {
        null
    }
}