package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.media.service.MediaWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class MediaServiceCase : ServiceCase {

    private val mediaWeb by inject<MediaWeb>()

    override suspend fun testAll() {
        mediaWeb.apply {
            getPaidMediaList(0, 10).logResponse(TAG)
            getMediaList(0, 10, 0, emptyList()).logResponse(TAG)
            getMediaInfo(1823).logResponse(TAG)
            getLiveStreamList(0, 10, 0).logResponse(TAG)
            getPaidLiveList(0, 10).logResponse(TAG)
            getPaidMediaListOfMember(0, 10).logResponse(TAG)

//            getMediaDetail(1823).logResponse(TAG) //棄用
            getPaidMediaListOfMemberByAppId(0, 0).logResponse(TAG)
            getMediaUrl(6).logResponse(TAG)
            getMediaPurchaseUrl(6).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "Media"
    }
}