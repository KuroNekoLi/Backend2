package com.cmoney.backend2.testing

import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse

fun <T : Any> T.toMockResponse(
    gson: Gson,
    code: Int = 200
): MockResponse {
    return MockResponse()
        .setResponseCode(code)
        .setBody(gson.toJson(this))
}

fun Int.toMockResponse(reason: String): MockResponse {
    return MockResponse()
        .setStatus("HTTP/1.1 $this $reason")
}

fun noContentMockResponse(): MockResponse {
    return 204.toMockResponse("No Content")
}

fun <T : Any> T.toBadRequestMockResponse(gson: Gson): MockResponse {
    return this.toMockResponse(gson, 400)
}