package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("chatroomId", alternate = ["ChatroomId"])
    val chatroomId: Long?,
    @SerializedName("content", alternate = ["Content"])
    val content: Map<String, String>?,
    @SerializedName("id", alternate = ["Id"])
    val id: Long?,
    @SerializedName("senderId", alternate = ["SenderId"])
    val senderId: Long?,
    @SerializedName("timestamp", alternate = ["Timestamp"])
    val timestamp: Long?,
    @SerializedName("type", alternate = ["Type"])
    val type: String?
)