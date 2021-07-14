package com.cmoney.backend2.forumocean.service.api.group.update

import com.cmoney.backend2.forumocean.service.api.variable.request.GroupJoinType
import com.google.gson.annotations.SerializedName

data class UpdateGroupRequestBody(
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("imageUrl")
    val imageUrl : String? = null,
    @SerializedName("isPublic")
    val isPublic : Boolean? = null,
    @SerializedName("searchable")
    val searchable : Boolean? = null,
    @SerializedName("joinType")
    val joinType : GroupJoinType? = null
)