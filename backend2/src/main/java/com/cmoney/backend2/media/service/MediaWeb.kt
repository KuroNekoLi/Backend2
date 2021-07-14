package com.cmoney.backend2.media.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.media.service.api.getlivevideolistbyappid.LiveStreamInfo
import com.cmoney.backend2.media.service.api.getmediadetail.GetMediaDetailResponse
import com.cmoney.backend2.media.service.api.getmediainfo.MediaInfo
import com.cmoney.backend2.media.service.api.getmedialistbyappid.VideoInfo
import com.cmoney.backend2.media.service.api.getpaidlivelist.PaidLiveListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialist.PaidMediaListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialistofmember.BoughtMediaListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialistofmemberbyappid.Media

interface MediaWeb {
    @Deprecated("ApiParam no longer required")
    suspend fun getMediaList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        tagIdList : List<Int> = emptyList()
    ): Result<List<VideoInfo>>

    suspend fun getMediaList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        tagIdList : List<Int> = emptyList()
    ): Result<List<VideoInfo>>

    @Deprecated("ApiParam no longer required")
    suspend fun getMediaPurchaseUrl(apiParam: MemberApiParam, mediaId: Long): Result<String>

    suspend fun getMediaPurchaseUrl(mediaId: Long): Result<String>

    @Deprecated("ApiParam no longer required")
    suspend fun getMediaUrl(apiParam: MemberApiParam, mediaId: Long): Result<String>
    suspend fun getMediaUrl(mediaId: Long): Result<String>

    @Deprecated("ApiParam no longer required")
    suspend fun getLiveStreamList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int
    ): Result<List<LiveStreamInfo>>

    suspend fun getLiveStreamList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int
    ): Result<List<LiveStreamInfo>>

    /**
     * 取得會員已購買的影音清單
     * @param apiParam guid,authToken,appId
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @return 購買的影音清單的result
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getPaidMediaListOfMember(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int
    ): Result<List<BoughtMediaListInfo>>

    /**
     * 取得會員已購買的影音清單
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @return 購買的影音清單的result
     */
    suspend fun getPaidMediaListOfMember(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<BoughtMediaListInfo>>

    /**
     * 取得付費影音清單
     * @param apiParam guid,authToken,appId
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @return 付費影音清單的result
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getPaidMediaList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidMediaListInfo>>

    /**
     * 取得付費影音清單
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @return 付費影音清單的result
     */
    suspend fun getPaidMediaList(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidMediaListInfo>>

    /**
     * 取得付費直播清單
     * @param apiParam guid,authToken,appId
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @return 付費直播清單的result
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getPaidLiveList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidLiveListInfo>>


    /**
     * 取得付費直播清單
     * @param apiParam guid,authToken,appId
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @return 付費直播清單的result
     */
    suspend fun getPaidLiveList(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<PaidLiveListInfo>>

    @Deprecated("ApiParam no longer required.")
    suspend fun getMediaInfo(
        apiParam: MemberApiParam,
        mediaId: Long
    ): Result<MediaInfo>

    /**
     * 取得影音詳情內容
     */
    suspend fun getMediaInfo(
        mediaId: Long
    ): Result<MediaInfo>

    suspend fun getMediaDetail(mediaId: Long): Result<GetMediaDetailResponse>

    suspend fun getPaidMediaListOfMemberByAppId(
        skipCount: Int,
        fetchCount: Int
    ): Result<List<Media>>
}
