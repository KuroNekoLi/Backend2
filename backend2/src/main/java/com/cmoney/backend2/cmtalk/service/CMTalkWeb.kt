package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo


interface CMTalkWeb {
    suspend fun getTargetMediaList( mediaType: Int, skipCount: Int, fetchCount: Int): Result<TargetMediaListInfo>
}