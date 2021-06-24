package com.cmoney.backend2.note_extension.service.api.createreply

import com.google.gson.annotations.SerializedName

/**
 * 回文後回傳
 * @param commentIndex 該回文編號
 */
data class CreateCommentResponseBody(
    @SerializedName("commentIndex")
    val commentIndex: Long?
)