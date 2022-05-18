package com.cmoney.backend2.additioninformationrevisit.service


import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting

interface AdditionalInformationRevisitWeb {
    /**
     * 網路基本設定
     */
    val setting: Setting

    /**
     * 服務名稱設定
     */
    val servicePath: ServicePath

    /**
     * 根據參數提供資料
     *
     * 範例請參考連結: http://outpost.cmoney.net.tw/AdditionInformationRevisit/swagger/index.html
     *
     * @param apiParam MemberApiParam
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @return Result<List<List<String>>>
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAll(
        apiParam: MemberApiParam,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得片面最新資料
     *
     * ※轉呼getAll(domain, serviceParam)，代入[setting]的[Setting.domainUrl]及[ServicePath.all]
     *
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @return Result<List<List<String>>>
     */
    suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得片面最新資料
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param typeName 資料型態名稱
     * @param columns 資料輸出欄位
     * @param processSteps
     * @return table資料
     */
    suspend fun getAll(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.all,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 根據參數提供資料
     *
     * 範例請參考連結: http://outpost.cmoney.net.tw/AdditionInformationRevisit/swagger/index.html
     *
     * @param apiParam MemberApiParam
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param value String 用於Filter的參數，請參閱文件提供與TypeName對應的字串
     * @return Result<List<String>>
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getTarget(
        apiParam: MemberApiParam,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得以[value]篩選過的資料
     *
     * ※轉呼 getTarget(domain, serviceParam)，代入[setting]的[Setting.domainUrl]及[ServicePath.target]
     *
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param value String
     * @return Result<List<String>>
     */
    suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得以[value]篩選過的資料
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param value String
     * @return Result<List<String>>
     */
    suspend fun getTarget(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.target,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得Channel Signal資料
     *
     * @param apiParam MemberApiParam
     * @param channels List<String>
     * @return Result<List<List<String>>>
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getSignal(
        apiParam: MemberApiParam,
        channels: List<String>
    ): Result<List<List<String>>>

    /**
     * 取得Channel Signal資料
     *
     * ※轉呼 getSignal(domain, serviceParam)，代入[setting]的[Setting.domainUrl]及[ServicePath.signal]
     *
     * @param channels List<String>
     * @return Result<List<List<String>>>
     */
    suspend fun getSignal(channels: List<String>): Result<List<List<String>>>

    /**
     * 取得Channel Signal資料
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param channels List<String>
     * @return Result<List<List<String>>>
     */
    suspend fun getSignal(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.signal,
        channels: List<String>
    ): Result<List<List<String>>>

    /**
     * 根據參數提供資料
     *
     * 範例請參考連結: http://outpost.cmoney.net.tw/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * @param apiParam MemberApiParam
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param value String 用於Filter的參數，請參閱文件提供與TypeName對應的字串
     * @return Result<List<List<String>>>
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getMultiple(
        apiParam: MemberApiParam,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 可用於股票交易明細、N分K
     *
     * 範例請參考連結: http://192.168.99.148/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * ※轉呼 getMultiple(domain, serviceParam)，代入[setting]的[Setting.domainUrl]及[ServicePath.multiple]
     *
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param value
     * @param processSteps
     * @return Result<List<List<String>>>
     */
    suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 可用於股票交易明細、N分K
     *
     * 範例請參考連結: http://192.168.99.148/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @return Result<List<List<String>>>
     */
    suspend fun getMultiple(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.multiple,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 根據參數提供資料
     *
     * 範例請參考連結: http://outpost.cmoney.net.tw/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * @param apiParam MemberApiParam
     * @param requestType String
     * @param responseType String
     * @param columns List<String>
     * @param value String 用於Filter的參數，請參閱文件提供對應的字串
     * @return List<List<String>>
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getOtherQuery(
        apiParam: MemberApiParam,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 目前可用於 部分交易明細查詢
     *
     * 範例請參考連結: http://192.168.99.148/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * @param requestType String
     * @param responseType String
     * @param columns List<String>
     * @param value String
     * @param processSteps List<ProcessingStep>
     * @return List<List<String>>
     */
    suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 目前可用於 部分交易明細查詢
     *
     * 範例請參考連結: http://192.168.99.148/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * ※轉呼 getOtherQuery(domain, serviceParam)，代入[setting]的[Setting.domainUrl]及[ServicePath.otherQuery]
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param requestType String
     * @param responseType String
     * @param columns List<String>
     * @param value String
     * @param processSteps List<ProcessingStep>
     * @return List<List<String>>
     */
    suspend fun getOtherQuery(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.otherQuery,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得前次交易片面最新資料
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param typeName 資料型態名稱
     * @param columns 資料輸出欄位
     * @param processSteps
     * @return table資料
     */
    suspend fun getPreviousAll(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.previousAll,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 取得前次交易以[value]篩選過的資料
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param value String
     * @return Result<List<String>>
     */
    suspend fun getPreviousTarget(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.previousTarget,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 前次交易，可用於股票交易明細、N分K
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @return Result<List<List<String>>>
     */
    suspend fun getPreviousMultiple(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.previousMultiple,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>

    /**
     * 前次交易，目前可用於 部分交易明細查詢
     *
     * 範例請參考連結: http://192.168.99.148/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * @param domain 呼叫的server網域
     * @param serviceParam Service名稱
     * @param requestType String
     * @param responseType String
     * @param columns List<String>
     * @param value String
     * @param processSteps List<ProcessingStep>
     * @return List<List<String>>
     */
    suspend fun getPreviousOtherQuery(
        domain: String = setting.domainUrl,
        serviceParam: String = servicePath.previousOtherQuery,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>
}