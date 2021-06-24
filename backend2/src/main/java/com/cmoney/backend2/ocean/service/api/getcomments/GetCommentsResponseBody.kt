package com.cmoney.backend2.ocean.service.api.getcomments
import com.cmoney.backend2.ocean.service.api.variable.Comment
import com.google.gson.annotations.SerializedName


data class GetCommentsResponseBody(
    @SerializedName("Data")
    val data: List<Comment>?
)