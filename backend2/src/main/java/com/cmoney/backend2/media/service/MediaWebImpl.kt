package com.cmoney.backend2.media.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toJsonArrayWithErrorResponse
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.media.service.api.getlivevideolistbyappid.LiveStreamInfo
import com.cmoney.backend2.media.service.api.getmediadetail.GetMediaDetailResponse
import com.cmoney.backend2.media.service.api.getmediainfo.MediaInfo
import com.cmoney.backend2.media.service.api.getmedialistbyappid.VideoInfo
import com.cmoney.backend2.media.service.api.getpaidlivelist.PaidLiveListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialist.PaidMediaListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialistofmember.BoughtMediaListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialistofmemberbyappid.Media
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.withContext

class MediaWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: MediaService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : MediaWeb {

    override suspend fun getMediaList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        tagIdList : List<Int>,
        domain: String,
        url: String
    ): Result<List<VideoInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val tagIdListString = tagIdList.joinToString(separator = ",")
            solvedJsonArrayResponseQuestion<List<VideoInfo>>(
                service.getMediaList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount,
                    chargeType = chargeType,
                    tagIdList = tagIdListString
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getMediaPurchaseUrl(
        mediaId: Long,
        domain: String,
        url: String
    ): Result<String> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMediaPurchaseUrl(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                mediaId = mediaId
            ).checkIsSuccessful()
                .requireBody()
                .url.orEmpty()
        }
    }

    override suspend fun getMediaUrl(
        mediaId: Long,
        domain: String,
        url: String
    ): Result<String> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMediaUrl(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                mediaId = mediaId
            ).checkIsSuccessful()
                .requireBody()
                .url.orEmpty()
        }
    }

    override suspend fun getLiveStreamList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        domain: String,
        url: String
    ): Result<List<LiveStreamInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<LiveStreamInfo>>(
                service.getLiveStreamList(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    skipCount = skipCount,
                    fetchCount = fetchCount,
                    chargeType = chargeType
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getPaidMediaListOfMember(
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<List<BoughtMediaListInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<BoughtMediaListInfo>>(
                service.getPaidMediaListOfMember(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getPaidMediaList(
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<List<PaidMediaListInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<PaidMediaListInfo>>(
                service.getPaidMediaList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getMediaInfo(
        mediaId: Long,
        domain: String,
        url: String
    ): Result<MediaInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMediaInfo(
                    action = "getmediainfo",
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    mediaId = mediaId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getPaidLiveList(
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<List<PaidLiveListInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<PaidLiveListInfo>>(
                service.getPaidLiveList(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    @Deprecated(message = "See interface comment.")
    override suspend fun getMediaDetail(
        mediaId: Long,
        domain: String,
        url: String
    ): Result<GetMediaDetailResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMediaDetail(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                mediaId = mediaId,
                device = manager.getPlatform().code
            ).checkIsSuccessful()
                .checkResponseBody(gson)
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getPaidMediaListOfMemberByAppId(
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<List<Media>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<Media>>(
                service.getPaidMediaListOfMemberByAppId(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
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
