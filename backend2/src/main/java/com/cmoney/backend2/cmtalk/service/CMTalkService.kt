package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CMTalkService {
    /**
     * 批次取得影音清單
     *
     * @param url 要求位址
     * @param mediaType 影音類型 (1:新聞直播，2:精選影音)
     * @param baseId 從哪開始往後取(第一次傳0)
     * @param fetchSize 要拉幾則
     */
    @RecordApi(cmoneyAction = "gettargetmedialist")
    @GET
    suspend fun getTargetMediaList(
        @Url url: String,
        @Query("action") action: String = "gettargetmedialist",
        @Query("mediatype") mediaType: Int,
        @Query("baseid") baseId: Int,
        @Query("fetchsize") fetchSize:Int
    ):Response<TargetMediaListInfo>
}