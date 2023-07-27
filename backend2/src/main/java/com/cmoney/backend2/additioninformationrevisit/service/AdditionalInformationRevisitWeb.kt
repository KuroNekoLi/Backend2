package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep

interface AdditionalInformationRevisitWeb {

    /**
     * 取得GetAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得GetAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     * @param domain 網域名稱
     *
     */
    suspend fun getAll(
        domain: String,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
    ): Result<List<List<String>>>

    /**
     * 取得GetAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     *
     */
    suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        url: String
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
     *
     */
    suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
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
     *
     */
    suspend fun getTarget(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
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
     * @param url 完整的Url
     *
     */
    suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得GetSignal類型資料。
     *
     * @param channels 目標頻道
     *
     */
    suspend fun getSignal(
        channels: List<String>,
    ): Result<List<List<String>>>

    /**
     * 取得GetSignal類型資料。
     *
     * @param channels 目標頻道
     * @param domain 網域名稱
     *
     */
    suspend fun getSignal(
        domain: String,
        channels: List<String>
    ): Result<List<List<String>>>

    /**
     * 取得GetSignal類型資料。
     *
     * @param channels 目標頻道
     * @param url 完整的Url
     *
     */
    suspend fun getSignal(
        channels: List<String>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得GetMultiple類型資料。
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
     *
     */
    suspend fun getMultiple(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得GetMultiple類型資料。
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     *
     */
    suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得GetOtherQuery類型資料
     *
     * @param requestType 請求類型
     * @param responseType 回應類型
     * @param columns 需要查詢的欄位
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
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
     *
     */
    suspend fun getOtherQuery(
        domain: String,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得GetOtherQuery類型資料
     *
     * @param requestType 請求類型
     * @param responseType 回應類型
     * @param columns 需要查詢的欄位
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     *
     */
    suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getPreviousAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousAll類型資料
     *
     * @param domain 網域名稱
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getPreviousAll(
        domain: String,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousAll類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     *
     */
    suspend fun getPreviousAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得getPreviousTarget類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getPreviousTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
     *
     */
    suspend fun getPreviousTarget(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得getPreviousTarget類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     *
     */
    suspend fun getPreviousTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousMultiple類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getPreviousMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
     *
     */
    suspend fun getPreviousMultiple(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousMultiple類型資料
     *
     * @param typeName 資料型態名稱
     * @param columns 需要查詢的欄位
     * @param keyNamePath 關鍵字的路徑
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     *
     */
    suspend fun getPreviousMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousOtherQuery類型資料
     *
     * @param requestType 請求類型
     * @param responseType 回應類型
     * @param columns 需要查詢的欄位
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     *
     */
    suspend fun getPreviousOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
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
     */
    suspend fun getPreviousOtherQuery(
        domain: String,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
    ): Result<List<List<String>>>

    /**
     * 取得GetPreviousOtherQuery類型資料
     *
     * @param requestType 請求類型
     * @param responseType 回應類型
     * @param columns 需要查詢的欄位
     * @param value 指定的參數
     * @param processSteps 附加的處理行為
     * @param url 完整的Url
     */
    suspend fun getPreviousOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        url: String
    ): Result<List<List<String>>>

    enum class MarketType {
        TW,
        US
    }
}