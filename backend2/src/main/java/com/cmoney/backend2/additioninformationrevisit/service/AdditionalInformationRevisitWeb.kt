package com.cmoney.backend2.additioninformationrevisit.service


import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.model.request.MemberApiParam

interface AdditionalInformationRevisitWeb {

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
     * @param channels List<String>
     * @return Result<List<List<String>>>
     */
    suspend fun getSignal(channels: List<String>): Result<List<List<String>>>

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
     * 可以使用內建物件提供參數值[IGetMultipleValue]
     *
     * 範例請參考連結: http://192.168.99.148/AdditionInformationRevisit/swagger/index.html
     * GetServiceList 目前主機提供之服務
     *
     * @param typeName String
     * @param columns List<String> 資料輸出欄位
     * @param keyNamePath List<String>
     * @param iGetMultipleValue String
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
     * @param iGetOtherQueryValue IGetOtherQueryValue
     * @return List<List<String>>
     */
    suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>>
}