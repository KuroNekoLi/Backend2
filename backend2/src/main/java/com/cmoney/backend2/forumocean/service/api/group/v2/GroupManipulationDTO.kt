package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GroupManipulationDTO(
    @SerializedName("description")
    val description: String?,
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("needApproval")
    val needApproval: Boolean?
)