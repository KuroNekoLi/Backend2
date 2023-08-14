package com.cmoney.backend2.crm.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager

interface CrmWeb {

    val manager: GlobalBackend2Manager

    /**
     * 建立客服聊天室
     *
     * @param isPro 是否為付費使用者
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createLiveChat(
        isPro: Boolean,
        domain: String = manager.getCrmSettingAdapter().getDomain(),
        url: String = "${domain}CRM/livechat"
    ): Result<String>
}