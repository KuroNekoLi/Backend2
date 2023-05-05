package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName

/**
 * 單一看板
 */
data class BoardSingle(
    /**
     * 權限
     */
    @SerializedName("auth")
    val auth: Auth?,
    /**
     * 專欄預設看板：ColumnistDefaultBoard
     */
    @SerializedName("boardType")
    val boardType: String?,
    /**
     *社團ID
     */
    @SerializedName("groupId")
    val groupId: Int?,
    /**
     * 是否預設
     */
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
     * 角色權限
     */
    @SerializedName("rolesAuth")
    val rolesAuth: List<RolesAuth>?,
    /**
     * 未讀資訊
     */
    @SerializedName("readInfo")
    val readInfo: BoardReadInfo?
)