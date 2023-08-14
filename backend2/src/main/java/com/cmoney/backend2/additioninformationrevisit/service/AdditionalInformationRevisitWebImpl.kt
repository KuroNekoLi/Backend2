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
    private val globalBackend2Manager: GlobalBackend2Manager,
    private val service: AdditionalInformationRevisitService,
    private val marketType: AdditionalInformationRevisitWeb.MarketType,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : AdditionalInformationRevisitWeb {

    override suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetAll/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetAll/$typeName"
                url
            }
        }
        return getAll(
            columns = columns,
            typeName = typeName,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getAll(
        domain: String,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetAll/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetAll/$typeName"
                url
            }
        }
        return getAll(
            columns = columns,
            typeName = typeName,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
        url: String
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetTarget/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetTarget/$typeName"
                url
            }
        }
        return getTarget(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getTarget(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetTarget/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetTarget/$typeName"
                url
            }
        }
        return getTarget(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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

    override suspend fun getSignal(channels: List<String>): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/Signal/Get/${channels.joinComma()}"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/Signal/Get/${channels.joinComma()}"
                url
            }
        }
        return getSignal(
            channels = channels,
            url = url
        )
    }

    override suspend fun getSignal(
        domain: String,
        channels: List<String>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/Signal/Get/${channels.joinComma()}"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/Signal/Get/${channels.joinComma()}"
                url
            }
        }
        return getSignal(
            channels = channels,
            url = url
        )
    }

    override suspend fun getSignal(
        channels: List<String>,
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetMultiple/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetMultiple/$typeName"
                url
            }
        }
        return getMultiple(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getMultiple(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetMultiple/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetMultiple/$typeName"
                url
            }
        }
        return getMultiple(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetOtherQuery/$requestType/$responseType"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/GetOtherQuery/$requestType/$responseType"
                url
            }
        }
        return getOtherQuery(
            requestType = requestType,
            responseType = responseType,
            columns = columns,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getOtherQuery(
        domain: String,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetOtherQuery/$requestType/$responseType"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/GetOtherQuery/$requestType/$responseType"
                url
            }
        }
        return getOtherQuery(
            requestType = requestType,
            responseType = responseType,
            columns = columns,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetAll/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetAll/$typeName"
                url
            }
        }
        return getPreviousAll(
            columns = columns,
            typeName = typeName,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousAll(
        domain: String,
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetAll/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetAll/$typeName"
                url
            }
        }
        return getPreviousAll(
            columns = columns,
            typeName = typeName,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousAll(
        columns: List<String>,
        typeName: String,
        processSteps: List<ProcessStep>,
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetTarget/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetTarget/$typeName"
                url
            }
        }
        return getPreviousTarget(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousTarget(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetTarget/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetTarget/$typeName"
                url
            }
        }
        return getPreviousTarget(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousTarget(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetMultiple/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetMultiple/$typeName"
                url
            }
        }
        return getPreviousMultiple(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousMultiple(
        domain: String,
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetMultiple/$typeName"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetMultiple/$typeName"
                url
            }
        }
        return getPreviousMultiple(
            typeName = typeName,
            columns = columns,
            keyNamePath = keyNamePath,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousMultiple(
        typeName: String,
        columns: List<String>,
        keyNamePath: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetOtherQuery/$requestType/$responseType"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val domain = adapter.getDomain()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetOtherQuery/$requestType/$responseType"
                url
            }
        }
        return getPreviousOtherQuery(
            requestType = requestType,
            responseType = responseType,
            columns = columns,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousOtherQuery(
        domain: String,
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>
    ): Result<List<List<String>>> {
        val url = when (marketType) {
            AdditionalInformationRevisitWeb.MarketType.TW -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitTwSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetOtherQuery/$requestType/$responseType"
                url
            }
            AdditionalInformationRevisitWeb.MarketType.US -> {
                val adapter = globalBackend2Manager.getAdditionInformationRevisitUsSettingAdapter()
                val url = "${domain}${adapter.getPathName()}api/PreviousData/GetOtherQuery/$requestType/$responseType"
                url
            }
        }
        return getPreviousOtherQuery(
            requestType = requestType,
            responseType = responseType,
            columns = columns,
            value = value,
            processSteps = processSteps,
            url = url
        )
    }

    override suspend fun getPreviousOtherQuery(
        requestType: String,
        responseType: String,
        columns: List<String>,
        value: String,
        processSteps: List<ProcessStep>,
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