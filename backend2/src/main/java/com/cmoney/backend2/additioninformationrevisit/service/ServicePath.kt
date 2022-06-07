package com.cmoney.backend2.additioninformationrevisit.service

/**
 * 為了設定五道API的服務路徑名稱的暫存
 *
 * @property all String [AdditionalInformationRevisitService.getAll]的service參數
 * @property target String [AdditionalInformationRevisitService.getTarget]的service參數
 * @property multiple String [AdditionalInformationRevisitService.getMultiple]的service參數
 * @property otherQuery String [AdditionalInformationRevisitService.getOtherQuery]的service參數
 * @property signal String [AdditionalInformationRevisitService.getSignal]的service參數
 * @property previousAll String [AdditionalInformationRevisitService.getPreviousAll]的service參數
 * @property previousTarget String [AdditionalInformationRevisitService.getPreviousTarget]的service參數
 * @property previousMultiple String [AdditionalInformationRevisitService.getPreviousMultiple]的service參數
 * @property previousOtherQuery String [AdditionalInformationRevisitService.getPreviousOtherQuery]的service參數
 * @constructor
 */
data class ServicePath(
    val all: String,
    val target: String,
    val multiple: String,
    val otherQuery: String,
    val signal: String,
    val previousAll: String,
    val previousTarget: String,
    val previousMultiple: String,
    val previousOtherQuery: String
) {
    constructor(serviceName: String = PRODUCT_SERVICE_NAME) : this(
        all = serviceName,
        target = serviceName,
        multiple = serviceName,
        otherQuery = serviceName,
        signal = serviceName,
        previousAll = serviceName,
        previousTarget = serviceName,
        previousMultiple = serviceName,
        previousOtherQuery = serviceName
    )

    companion object {
        const val PRODUCT_SERVICE_NAME = "AdditionInformationRevisit"
    }
}