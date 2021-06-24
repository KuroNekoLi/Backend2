package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.media.service.MediaWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class MediaServiceCase : ServiceCase {

    private val mediaWeb by inject<MediaWeb>()

    override suspend fun testAll() {
        mediaWeb.apply {
            this.getMediaList(0, 10, 0, emptyList()).logResponse(TAG)
            this.getLiveStreamList(0, 10, 0).logResponse(TAG)
            this.getPaidMediaList(0, 10).logResponse(TAG)
            this.getPaidLiveList(0, 10).logResponse(TAG)
            this.getPaidMediaListOfMember(0, 10).logResponse(TAG)
            this.getMediaInfo(1823).logResponse(TAG)
            this.getMediaDetail(1823).logResponse(TAG)
            this.getPaidMediaListOfMemberByAppId(0, 0).logResponse(TAG)
            this.getMediaUrl(6).logResponse(TAG)
            this.getMediaPurchaseUrl(6).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "Media"
    }
}