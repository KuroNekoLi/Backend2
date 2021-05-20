package com.cmoney.backend2.chat.service.api.getallreport.request
import com.google.gson.annotations.SerializedName

data class ReportInfo(
    @SerializedName("chatroomId")
    val chatRoomId: Long?,
    @SerializedName("messageId")
    val messageId: Long?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("reporterId")
    val reporterId: Long?,
    @SerializedName("subjectId")
    val subjectId: Long?
)