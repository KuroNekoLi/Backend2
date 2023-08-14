package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo


interface CMTalkWeb {

    val manager: GlobalBackend2Manager

    /**
     * 批次取得影音清單
     *
     * @param mediaType 影音類型 (1:新聞直播，2:精選影音)
     * @param skipCount 從哪開始往後取(第一次傳0)
     * @param fetchCount 要拉幾則
     * @param domain 網域名稱
     * @param url 要求位址
     * @return 影音清單
     */
    suspend fun getTargetMediaList(
        mediaType: Int,
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getCMTalkSettingAdapter().getDomain(),
        url: String = "${domain}CMTalk/Ashx/media.ashx"
    ): Result<TargetMediaListInfo>
}