package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.cmtalk.service.CMTalkWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CMTalkServiceCase : ServiceCase {

    private val cmtalkImpl by inject<CMTalkWeb>()

    override suspend fun testAll() {
        cmtalkImpl.getTargetMediaList(VIDEO, 0, 20).logResponse(TAG)
    }

    companion object {
        private const val TAG = "CMTalk"
        private const val LIVE = 1
        private const val VIDEO = 2
    }
}