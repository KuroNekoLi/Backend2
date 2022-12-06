package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat

import com.cmoney.backend2.forumocean.service.api.group.v2.BoardReadInfo
import com.google.gson.annotations.SerializedName

data class GetAllChatRoomResponse(
    /**
     * 社團資訊
     */
    @SerializedName("groupInfo")
    val groupInfo: GetAllChatRoomResponseGroupInfo?,
    /**
     * 看板資訊
     */
    @SerializedName("boardInfo")
    val boardInfo: GetAllChatRoomResponseBoardInfo?,
    /**
     * 未讀資訊
     */
    @SerializedName("readInfo")
    val readInfo: BoardReadInfo?,

    /**
     * 最後一則訊息資訊
     */
    @SerializedName("lastArticle")
    val lastArticle: GetAllChatRoomResponseLastArticle?
)

data class GetAllChatRoomResponseGroupInfo(
    /**
     * 社團id
     */
    @SerializedName("id")
    val groupId: Long?,
    /**
     * 社團名稱
     */
    @SerializedName("name")
    val name: String?,
    /**
     * 社團名稱
     */
    @SerializedName("imageUrl")
    val imageUrl: String?,
)

data class GetAllChatRoomResponseBoardInfo(
    /**
     * 社團id
     */
    @SerializedName("id")
    val boardId: Long?,
    /**
     * 社團名稱
     */
    @SerializedName("name")
    val name: String?,
)

/**
 * 聊天室最後一則訊息資訊
 */
data class GetAllChatRoomResponseLastArticle(
    /**
     * 訊息內文
     */
    @SerializedName("text")
    val text: String?,
    /**
     * 創建時間
     */
    @SerializedName("createTime")
    val createTime: Long?
)
