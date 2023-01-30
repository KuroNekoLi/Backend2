package com.cmoney.backend2.media.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
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
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

class MediaWebImpl(
    private val setting: Setting,
    private val service: MediaService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : MediaWeb {

    override suspend fun getMediaList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        tagIdList : List<Int>
    ): Result<List<VideoInfo>> = getMediaList(skipCount, fetchCount, chargeType , tagIdList)

    override suspend fun getMediaList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        tagIdList : List<Int>
    ): Result<List<VideoInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val tagIdListString = tagIdList.joinToString(separator = ",")
            solvedJsonArrayResponseQuestion<List<VideoInfo>>(
                service.getMediaList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
        apiParam: MemberApiParam,
        mediaId: Long
    ): Result<String> = getMediaPurchaseUrl(mediaId)

    override suspend fun getMediaPurchaseUrl(mediaId: Long): Result<String> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMediaPurchaseUrl(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                mediaId = mediaId
            ).checkIsSuccessful()
                .requireBody()
                .url.orEmpty()
        }
    }

    override suspend fun getMediaUrl(
        apiParam: MemberApiParam,
        mediaId: Long
    ): Result<String> = getMediaUrl(mediaId)

    override suspend fun getMediaUrl(mediaId: Long): Result<String> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMediaUrl(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                mediaId = mediaId
            ).checkIsSuccessful()
                .requireBody()
                .url.orEmpty()
        }
    }

    override suspend fun getLiveStreamList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int
    ): Result<List<LiveStreamInfo>> = getLiveStreamList(skipCount, fetchCount, chargeType)

    override suspend fun getLiveStreamList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int
    ): Result<List<LiveStreamInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<LiveStreamInfo>>(
                service.getLiveStreamList(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    authorization = setting.accessToken.createAuthorizationBearer(),
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
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int
    ): Result<List<BoughtMediaListInfo>> = getPaidMediaListOfMember(skipCount, fetchCount)

    override suspend fun getPaidMediaListOfMember(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<BoughtMediaListInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<BoughtMediaListInfo>>(
                service.getPaidMediaListOfMember(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getPaidMediaList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidMediaListInfo>> = getPaidMediaList(skipCount, fetchCount)

    override suspend fun getPaidMediaList(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidMediaListInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<PaidMediaListInfo>>(
                service.getPaidMediaList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getMediaInfo(
        apiParam: MemberApiParam,
        mediaId: Long
    ): Result<MediaInfo> = getMediaInfo(mediaId)

    override suspend fun getMediaInfo(mediaId: Long): Result<MediaInfo> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMediaInfo(
                    action = "getmediainfo",
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    mediaId = mediaId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getPaidLiveList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidLiveListInfo>> = getPaidLiveList(skipCount, fetchCount)

    override suspend fun getPaidLiveList(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidLiveListInfo>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<PaidLiveListInfo>>(
                service.getPaidLiveList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount
                ).checkIsSuccessful()
                    .requireBody()
                    .string()
            )
        }
    }

    override suspend fun getMediaDetail(
        mediaId: Long
    ): Result<GetMediaDetailResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMediaDetail(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                mediaId = mediaId,
                device = setting.platform.code
            ).checkIsSuccessful()
                .checkResponseBody(gson)
                .checkIWithError()
                .toRealResponse()
        }
    }


    override suspend fun getPaidMediaListOfMemberByAppId(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<Media>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            solvedJsonArrayResponseQuestion<List<Media>>(
                service.getPaidMediaListOfMemberByAppId(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
    @Throws(ServerException::class)
    private inline fun <reified T> solvedJsonArrayResponseQuestion(
        body: String
    ): T {
        val jsonBody = JsonParser.parseString(body)
        return if (jsonBody.isJsonArray) {
            gson.fromJson<T>(jsonBody, object : TypeToken<T>() {}.type)
        } else {
            val error = gson.fromJson<CMoneyError>(
                jsonBody,
                object : TypeToken<CMoneyError>() {}.type
            )
            throw ServerException(
                error?.detail?.code ?: Constant.SERVICE_ERROR_CODE,
                error?.detail?.message.orEmpty()
            )
        }
    }
}
