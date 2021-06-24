package com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse
import com.cmoney.backend2.forumocean.service.api.variable.response.GroupJoinTypeInfo
import com.google.gson.annotations.SerializedName


data class GroupResponseBody(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("isPublic")
    val isPublic: Boolean?,
    @SerializedName("joinType")
    val joinType: GroupJoinTypeInfo?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ownerId")
    val ownerId: Long?,
    @SerializedName("searchable")
    val searchable: Boolean?
)