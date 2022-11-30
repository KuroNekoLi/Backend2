package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName

/**
 * 一個看板
 */
data class Board(
    /**
     * 專欄預設看板：ColumnistDefaultBoard
     */
    @SerializedName("boardType")
    val boardType: String?,

    /**
     * 是否有權限可讀
     */
    @SerializedName("canRead")
    val canRead: Boolean?,

    /**
     * 看板Id
     */
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isDefault")
    val isDefault: Boolean?,

    /**
     * 是否為聊天室
     */
    @SerializedName("isChatroom")
    val isChatroom: Boolean?,

    /**
     * 看板名稱
     */
    @SerializedName("name")
    val name: String?,

    /**
     * 有權限的角色
     */
    @SerializedName("rolesAuth")
    val rolesAuth: List<RolesAuth>?,

    /**
     * 未讀資訊
     */
    @SerializedName("readInfo")
    val readInfo: BoardReadInfo?
)