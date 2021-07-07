package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CMTalkService {
    /**
     * 批次取得影音清單
     * @param mediaType 影音類型 (1:新聞直播，2:精選影音)
     * @param baseId 從哪開始往後取(第一次傳0)
     * @param fetchSize 要拉幾則
     */
    @RecordApi(cmoneyAction = "gettargetmedialist")
    @GET("CMTalk/Ashx/media.ashx")
    suspend fun getTargetMediaList(
        @Query("action") action: String = "gettargetmedialist",
        @Query("mediatype") mediaType: Int,
        @Query("baseid") baseId: Int,
        @Query("fetchsize") fetchSize:Int
    ):Response<TargetMediaListInfo>
}