package com.cmoney.backend2.note_extension.service.api.createreply

import com.google.gson.annotations.SerializedName

/**
 * @property createTime 留言創建時間
 * @property content 留言的內容（內有名稱及圖片）
 */

data class CreateCommentRequestBody(
    @SerializedName("createTime")
    val createTime: Long,
    @SerializedName("content")
    val content: Content
) {
    /**
     * @property text 留言內容
     * @property image 留言照片
     */
    data class Content(
        @SerializedName("text")
        val text: String?,
        @SerializedName("image")
        val image: String?
    )
}