package com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody

data class SubmitAdviceParam(

    /**
     * 發文內容
     */
    val content: String,

    /**
     * 裝置OS版本
     */
    val osVersion: String,

    /**
     * 裝置APP應用程式版本
     */
    val appVersion: String,

    /**
     * 裝置簡略名稱
     */
    val deviceName: String
)
