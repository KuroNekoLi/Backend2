package com.cmoney.backend2.additioninformationrevisit.service

/**
 * 為了設定五道API的服務路徑名稱的暫存
 *
 * @property all String [AdditionalInformationRevisitService.getAll]的service參數
 * @property target String [AdditionalInformationRevisitService.getTarget]的service參數
 * @property multiple String [AdditionalInformationRevisitService.getMultiple]的service參數
 * @property otherQuery String [AdditionalInformationRevisitService.getOtherQuery]的service參數
 * @property signal String [AdditionalInformationRevisitService.getSignal]的service參數
 * @constructor
 */
data class ServicePath(
    val all: String,
    val target: String,
    val multiple: String,
    val otherQuery: String,
    val signal: String
) {
    constructor(serviceName: String = PRODUCT_SERVICE_NAME) : this(
        all = serviceName,
        target = serviceName,
        multiple = serviceName,
        otherQuery = serviceName,
        signal = serviceName
    )

    companion object {
        const val PRODUCT_SERVICE_NAME = "AdditionInformationRevisit"
    }
}