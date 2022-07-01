package com.cmoney.backend2.commonuse.service

interface CommonUseWeb {

    val baseHost: String

    /**
     * get remoteConfigLabel from [host], return empty string if response is null
     */
    suspend fun getRemoteConfigLabel(host: String = baseHost): Result<String>
}
