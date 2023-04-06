package com.cmoney.backend2.additioninformationrevisit.service

import com.cmoney.backend2.additioninformationrevisit.service.api.request.GetRequestParam
import com.cmoney.backend2.additioninformationrevisit.service.api.request.ProcessStep
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext

class AdditionalInformationRevisitWebImpl(
    override val globalBackend2Manager: GlobalBackend2Manager,
    private val service: AdditionalInformationRevisitService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : AdditionalInformationRevisitWeb {

    override suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String,
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAll(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                param = GetRequestParam(
                    json = "",
                    processing = processSteps
                )
            ).checkIsSuccessful()
                .requireBody()
                .map { row ->
                    row.mapNotNull { value ->
                        value
                    }
                }
        }
    }

    override suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String,
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getTarget(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                keyNamePath = keyNamePath.joinComma(),
                param = GetRequestParam(
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
        channels: List<String>,
        domain: String,
        url: String,
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getSignal(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                param = Any()
            ).checkIsSuccessful()
                .requireBody()
                .map { row -> row.map { it.orEmpty() } }
        }
    }

    override suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getMultiple(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                keyNamePath = keyNamePath.joinComma(),
                param = GetRequestParam(
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
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getOtherQuery(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                param = GetRequestParam(
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
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPreviousAll(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                param = GetRequestParam(
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
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPreviousTarget(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                keyNamePath = keyNamePath.joinComma(),
                param = GetRequestParam(
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
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getPreviousMultiple(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                keyNamePath = keyNamePath.joinComma(),
                param = GetRequestParam(
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
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
        domain: String,
        url: String
    ): Result<List<List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPreviousOtherQuery(
                url = url,
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                columns = columns.joinComma(),
                param = GetRequestParam(
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