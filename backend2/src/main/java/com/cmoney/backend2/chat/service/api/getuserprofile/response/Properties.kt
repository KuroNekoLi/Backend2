package com.cmoney.backend2.chat.service.api.getuserprofile.response

import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("userImageUrl")
    val userImageUrl: String?,
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("@nameIdentifiers")
    val nameIdentifiers: List<NameIdentifier>?
)