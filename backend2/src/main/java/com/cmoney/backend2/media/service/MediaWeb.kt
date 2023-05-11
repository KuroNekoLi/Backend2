package com.cmoney.backend2.media.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.media.service.api.getlivevideolistbyappid.LiveStreamInfo
import com.cmoney.backend2.media.service.api.getmediadetail.GetMediaDetailResponse
import com.cmoney.backend2.media.service.api.getmediainfo.MediaInfo
import com.cmoney.backend2.media.service.api.getmedialistbyappid.VideoInfo
import com.cmoney.backend2.media.service.api.getpaidlivelist.PaidLiveListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialist.PaidMediaListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialistofmember.BoughtMediaListInfo
import com.cmoney.backend2.media.service.api.getpaidmedialistofmemberbyappid.Media

interface MediaWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得手機影音清單
     *
     * @param skipCount 略過數量(曾經要求過的數量)
     * @param fetchCount 要求數量
     * @param chargeType 0: All, 1: Paid, 2: Free
     * @param tagIdList 標籤集合
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 手機影音清單
     */
    suspend fun getMediaList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        tagIdList : List<Int> = emptyList(),
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<List<VideoInfo>>

    /**
     * 取得影音購買網址
     *
     * @param mediaId 影音編號
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 影音購買網址
     */
    suspend fun getMediaPurchaseUrl(
        mediaId: Long,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<String>

    /**
     * 取得影音播放網址
     *
     * @param mediaId 影音編號
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 影音播放網址
     */
    suspend fun getMediaUrl(
        mediaId: Long,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<String>

    /**
     * 取得手機直播清單
     *
     * @param skipCount 略過數量(曾經要求過的數量)
     * @param fetchCount 要求數量
     * @param chargeType 0: All, 1: Paid, 2: Free
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 手機直播清單
     */
    suspend fun getLiveStreamList(
        skipCount: Int,
        fetchCount: Int,
        chargeType: Int,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<List<LiveStreamInfo>>

    /**
     * 取得會員已購買的影音清單
     *
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 購買的影音清單的result
     */
    suspend fun getPaidMediaListOfMember(
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<List<BoughtMediaListInfo>>

    /**
     * 取得付費影音清單
     *
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 付費影音清單的result
     */
    suspend fun getPaidMediaList(
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<List<PaidMediaListInfo>>

    /**
     * 取得付費直播清單
     *
     * @param skipCount 略過筆數
     * @param fetchCount 欲取得的資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 付費直播清單的result
     */
    suspend fun getPaidLiveList(
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<List<PaidLiveListInfo>>

    /**
     * 取得影音詳情內容
     *
     * @param mediaId 影音編號
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 影音詳情內容
     */
    suspend fun getMediaInfo(
        mediaId: Long,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<MediaInfo>

    /**
     * 取得影音詳細資訊(有單元課程的影音才會用)-已廢除
     *
     * @param mediaId 影音編號
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 影音詳細資訊
     */
    @Deprecated(message = "改為使用 getMediaInfo()")
    suspend fun getMediaDetail(
        mediaId: Long,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<GetMediaDetailResponse>

    /**
     * 取得指定app的會員已購買的影音清單
     *
     * @param skipCount 略過數量(曾經要求過的數量)
     * @param fetchCount 要求數量
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 影音清單
     */
    suspend fun getPaidMediaListOfMemberByAppId(
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMediaSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/Media/Media.ashx"
    ): Result<List<Media>>
}
