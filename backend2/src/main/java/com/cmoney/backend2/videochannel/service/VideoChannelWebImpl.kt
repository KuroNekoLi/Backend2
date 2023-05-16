package com.cmoney.backend2.videochannel.service

import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toJsonArrayWithErrorResponse
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.videochannel.service.api.YoutubeVideo
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.withContext

class VideoChannelWebImpl(
    private val service: VideoChannelService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : VideoChannelWeb {
    override suspend fun getYoutubeVideos(
        youtubeChannelId: String,
        amount: Int?,
        time: Long?
    ): Result<List<YoutubeVideo>> = withContext(dispatcher.io()) {
        runCatching {
            solvedJsonArrayResponseQuestion<List<YoutubeVideo>>(
                service.getYoutubeVideos(
                    id = youtubeChannelId,
                    amount = amount,
                    time = time
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    /**
     * 解決回傳是JsonArray的方法
     */
    @Throws(
        ServerException::class,
        JsonSyntaxException::class
    )
    private inline fun <reified T> solvedJsonArrayResponseQuestion(
        body: String
    ): T {
        val jsonBody = JsonParser.parseString(body)
        return jsonBody.toJsonArrayWithErrorResponse(gson = gson)
    }
}