package com.cmoney.backend2.ocean.service.api.createclub


import com.google.gson.annotations.SerializedName

data class CreateClubRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("JoinMethod")
    val joinMethod: Int
)