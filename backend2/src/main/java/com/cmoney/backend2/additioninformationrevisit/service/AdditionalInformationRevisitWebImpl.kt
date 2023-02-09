package com.cmoney.backend2.additioninformationrevisit.service


import com.cmoney.backend2.additioninformationrevisit.service.api.request.GetRequestParam
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.additioninformationrevisit.service.api.request.RequestIds
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext

class AdditionalInformationRevisitWebImpl(
    override val setting: Setting,
    private val service: AdditionalInformationRevisitService,
    override val servicePath: ServicePath = ServicePath(),
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
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
    ): Result<List<List<String>>> =
        getAll(setting.domainUrl, servicePath.all, columns, typeName, processSteps)

    override suspend fun getAll(
        domain: String,
        serviceParam: String,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAll(
                url = "$domain$serviceParam/api/GetAll/$typeName",
                authorization = setting.accessToken.createAuthorizationBearer(),
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
    ): Result<List<List<String>>> = getTarget(
        setting.domainUrl,
        servicePath.target,
        typeName,
        columns,
        keyNamePath,
        value,
        processSteps
    )

    override suspend fun getTarget(
        domain: String,
        serviceParam: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getTarget(
                url = "$domain$serviceParam/api/GetTarget/$typeName",
                authorization = setting.accessToken.createAuthorizationBearer(),
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
        getSignal(setting.domainUrl, servicePath.signal, channels)

    override suspend fun getSignal(
        domain: String,
        serviceParam: String,
        channels: List<String>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getSignal(
                url = "$domain$serviceParam/api/Signal/Get/${channels.joinComma()}",
                authorization = setting.accessToken.createAuthorizationBearer(),
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
    ): Result<List<List<String>>> = getMultiple(
        setting.domainUrl,
        servicePath.multiple,
        typeName,
        columns,
        keyNamePath,
        value,
        processSteps
    )

    override suspend fun getMultiple(
        domain: String,
        serviceParam: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getMultiple(
                url = "$domain$serviceParam/api/GetMultiple/$typeName",
                authorization = setting.accessToken.createAuthorizationBearer(),
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
    ): Result<List<List<String>>> = getOtherQuery(
        setting.domainUrl,
        servicePath.otherQuery,
        requestType,
        responseType,
        columns,
        value,
        processSteps
    )

    override suspend fun getOtherQuery(
        domain: String,
        serviceParam: String,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getOtherQuery(
                url = "$domain$serviceParam/api/GetOtherQuery/$requestType/$responseType",
                authorization = setting.accessToken.createAuthorizationBearer(),
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

    override suspend fun getPreviousAll(
        domain: String,
        serviceParam: String,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPreviousAll(
                url = "$domain$serviceParam/api/PreviousData/GetAll/$typeName",
                authorization = setting.accessToken.createAuthorizationBearer(),
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

    override suspend fun getPreviousTarget(
        domain: String,
        serviceParam: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPreviousTarget(
                url = "$domain$serviceParam/api/PreviousData/GetTarget/$typeName",
                authorization = setting.accessToken.createAuthorizationBearer(),
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

    override suspend fun getPreviousMultiple(
        domain: String,
        serviceParam: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getPreviousMultiple(
                url = "$domain$serviceParam/api/PreviousData/GetMultiple/$typeName",
                authorization = setting.accessToken.createAuthorizationBearer(),
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

    override suspend fun getPreviousOtherQuery(
        domain: String,
        serviceParam: String,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPreviousOtherQuery(
                url = "$domain$serviceParam/api/PreviousData/GetOtherQuery/$requestType/$responseType",
                authorization = setting.accessToken.createAuthorizationBearer(),
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