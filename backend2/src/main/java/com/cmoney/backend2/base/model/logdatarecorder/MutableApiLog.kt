package com.cmoney.backend2.base.model.logdatarecorder

import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogError
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogRequest
import com.cmoney.domain_logdatarecorder.data.api.log.ApiLogResponse

internal data class MutableApiLog(
    var domain: String = "",
    var path: String = "",
    var userId: String? = null,
    var apiLogRequest: ApiLogRequest? = null,
    var apiLogResponse: ApiLogResponse? = null,
    var apiLogError: ApiLogError? = null
)