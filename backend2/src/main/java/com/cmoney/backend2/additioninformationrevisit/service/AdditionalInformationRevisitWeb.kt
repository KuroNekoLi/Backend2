package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager

interface AdditionalInformationRevisitWeb {

    /**
     * Backend2管理者
     */
    val globalBackend2Manager: GlobalBackend2Manager

    /**
     * 取得GetAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/GetAll/$typeName"
    ): Result<List<List<String>>>

    /**
     * 取得GetTarget類型資料。
     * 白話文一點就是根據[value]來過濾[getAll]的資料。
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/GetTarget/$typeName"
    ): Result<List<List<String>>>

    /**
     * 取得GetSignal類型資料。
     *
     * @param channels 目標頻道
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSignal(
        channels: List<String>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/Signal/Get/${channels.joinToString(",")}"
    ): Result<List<List<String>>>

    /**
     * 取得GetMultiple類型資料。
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/GetMultiple/$typeName"
    ): Result<List<List<String>>>

    /**
     * 取得GetOtherQuery類型資料
     *
     * @param requestType 請求類型
     * @param responseType 回應類型
     * @param columns 需要查詢的欄位
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/GetOtherQuery/$requestType/$responseType"
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPreviousAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/PreviousData/GetAll/$typeName"
    ): Result<List<List<String>>>

    /**
     * 取得getPreviousTarget類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPreviousTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/PreviousData/GetTarget/$typeName"
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousMultiple類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPreviousMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/PreviousData/GetMultiple/$typeName"
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousOtherQuery類型資料
     *
     * @param domain 呼叫的server網域
     * @param requestType 請求類型
     * @param responseType 回應類型
     * @param columns 需要查詢的欄位
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getPreviousOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String = globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getDomain(),
        url: String = "${domain}${globalBackend2Manager.getAdditionInformationRevisitSettingAdapter().getPathName()}/api/PreviousData/GetOtherQuery/$requestType/$responseType"
    ): Result<List<List<String>>>
}