package com.cmoney.backend2.additioninformationrevisit.service


import com.cmoney.backend2.additioninformationrevisit.service.api.request.GetRequestParam
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.additioninformationrevisit.service.api.request.RequestIds
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import kotlinx.coroutines.withContext

class AdditionalInformationRevisitWebImpl(
    private val setting: Setting,
    private val service: AdditionalInformationRevisitService,
    private val servicePath: ServicePath = ServicePath(),
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : AdditionalInformationRevisitWeb {

    override suspend fun getAll(
        apiParam: MemberApiParam,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = getAll(columns, typeName, processSteps)

    override suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAll(
                authorization = setting.accessToken.createAuthorizationBearer(),
                service = servicePath.all,
                typeName = typeName,
                columns = columns.joinComma(),
                param = GetRequestParam(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    json = "",
                    processing = processSteps
                )
            ).checkIsSuccessful()
                .requireBody()
                .map { obj ->
                    obj.mapNotNull { value ->
                        value
                    }
                }
        }
    }

    override suspend fun getTarget(
        apiParam: MemberApiParam,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = getTarget(typeName, columns, keyNamePath, value, processSteps)

    override suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getTarget(
                authorization = setting.accessToken.createAuthorizationBearer(),
                service = servicePath.target,
                typeName = typeName,
                columns = columns.joinComma(),
                keyNamePath = keyNamePath.joinComma(),
                param = GetRequestParam(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    json = value,
                    processing = processSteps
                )
            ).checkIsSuccessful()
                .requireBody()
                .map { row ->
                    row.map {
                        it.orEmpty()
                    }
                }
        }
    }

    override suspend fun getSignal(
        apiParam: MemberApiParam,
        channels: List<String>
    ): Result<List<List<String>>> = getSignal(channels)

    override suspend fun getSignal(channels: List<String>): Result<List<List<String>>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSignal(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    service = servicePath.signal,
                    channels = channels.joinComma(),
                    param = RequestIds(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .map { row -> row.map { it.orEmpty() } }
            }
        }

    override suspend fun getMultiple(
        apiParam: MemberApiParam,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = getMultiple(typeName, columns, keyNamePath, value, processSteps)

    override suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getMultiple(
                authorization = setting.accessToken.createAuthorizationBearer(),
                service = servicePath.multiple,
                typeName = typeName,
                columns = columns.joinComma(),
                keyNamePath = keyNamePath.joinComma(),
                param = GetRequestParam(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    json = value,
                    processing = processSteps
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .map { row ->
                    row.map {
                        it.orEmpty()
                    }
                }
        }
    }

    override suspend fun getOtherQuery(
        apiParam: MemberApiParam,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> =
        getOtherQuery(requestType, responseType, columns, value, processSteps)

    override suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getOtherQuery(
                authorization = setting.accessToken.createAuthorizationBearer(),
                service = servicePath.otherQuery,
                requestType = requestType,
                responseType = responseType,
                columns = columns.joinComma(),
                param = GetRequestParam(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    json = value,
                    processing = processSteps
                )
            ).checkIsSuccessful()
                .requireBody()
                .map { row ->
                    row.map { it.orEmpty() }
                }
        }
    }

    private fun Iterable<String>.joinComma(): String {
        return this.joinToString(COMMA)
    }

    companion object {
        private const val COMMA = ","
    }
}