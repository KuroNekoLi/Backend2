package com.cmoney.backend2.notification2.service.api.gethistorynotifyall

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName

data class GetNotifyAllResponseBody(
    @SerializedName("sn", alternate = ["Sn"])
    val sn: Long?,
    @SerializedName("notificationId", alternate = ["NotificationId"])
    val notificationId: Long?,
    @SerializedName("body", alternate = ["Body"])
    val body: String?,
    @SerializedName("parameter", alternate = ["Parameter"])
    val parameter: Any?,
    @SerializedName("createTime", alternate = ["CreateTime"])
    val createTime: Long?
)

@Throws(JsonSyntaxException::class, JsonParseException::class)
fun <T> List<GetNotifyAllResponseBody>.formatCustomParameter(
    gson: Gson,
    parameterClass: Class<T>
): List<GetNotifyAllResponseBody> {
    return this.map {
        val realParameter = gson.fromJson<T>(it.parameter.toString(), parameterClass)
        it.copy(parameter = realParameter)
    }
}