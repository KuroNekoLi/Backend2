package com.cmoney.backend2.crm.service

interface CrmWeb {

    /**
     * 建立客服聊天室
     *
     * @param isPro 是否為付費使用者
     */
    suspend fun createLiveChat(isPro: Boolean): Result<String>
}