package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo
import kotlinx.coroutines.withContext

class CMTalkWebImpl(
    private val service: CMTalkService,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : CMTalkWeb {

    override suspend fun getTargetMediaList(
        mediaType: Int,
        skipCount: Int,
        fetchCount: Int
    ): Result<TargetMediaListInfo> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val response = service.getTargetMediaList(
                    mediaType = mediaType,
                    baseId = skipCount,
                    fetchSize = fetchCount
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }
}