package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.commonuse.service.CommonUseWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class CommonUseServiceCase : ServiceCase {

    private val web by inject<CommonUseWeb>()

    override suspend fun testAll() {
        web.getRemoteConfigLabel()
            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "CommonUse"
    }
}