package com.cmoney.backend2.forumocean.service.api.group.v2

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * 社團成員
 */
@Keep
data class GroupMember(
    /**
     * 社團圖片
     */
    @SerializedName("image")
    val image: String?,
    /**
     * 使用者id
     */
    @SerializedName("memberId")
    val memberId: Int?,
    /**
     * 暱稱
     */
    @SerializedName("nickName")
    val nickName: String?,
    /**
     * 角色ID
     */
    @SerializedName("roleIds")
    val roleIds: List<Int>?
)