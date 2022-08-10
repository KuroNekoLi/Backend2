package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 社團修改
 */
@Keep
data class GroupManipulation(
    /**
     * 描述
     */
    @SerializedName("description")
    val description: String?,
    /**
     * 社團圖片
     */
    @SerializedName("imgUrl")
    val imgUrl: String?,
    /**
     * 社團名稱
     */
    @SerializedName("name")
    val name: String?,
    /**
     * 是否需要審核
     */
    @SerializedName("needApproval")
    val needApproval: Boolean?
)