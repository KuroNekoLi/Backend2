package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 新增完成物件
 */
data class InsertedId(
    /**
     * 對象物件ID
     */
    @SerializedName("id")
    val id: Int?
)