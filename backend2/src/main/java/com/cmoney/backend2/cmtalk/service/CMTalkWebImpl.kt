package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext

class CMTalkWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: CMTalkService,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : CMTalkWeb {

    override suspend fun getTargetMediaList(
        mediaType: Int,
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<TargetMediaListInfo> =
        withContext(dispatcherProvider.io()) {
            kotlin.runCatching {
                val response = service.getTargetMediaList(
                    url = url,
                    mediaType = mediaType,
                    baseId = skipCount,
                    fetchSize = fetchCount
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }
}