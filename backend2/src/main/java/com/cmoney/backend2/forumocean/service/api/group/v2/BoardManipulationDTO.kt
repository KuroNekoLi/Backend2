package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class BoardManipulationDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("rolesAuth")
    val rolesAuth: List<RolesAuth>?
)