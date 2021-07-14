package com.cmoney.backend2.note_extension.service.api.getreplylistbyid

import com.google.gson.annotations.SerializedName

class GetCommentListByNoteIdResponseBody {
    /**
     * 留言內容
     *
     * @property id 回文(留言)編號
     * @property deleted 該留言有無被刪除
     * @property memberId 會員編號 (請利用此ID Call GraphQL拿取所需資料）
     * @property createTime 留言創建時間
     * @property content 留言內容（內容包含文字、圖片）
     */
    data class Comment(
        @SerializedName("id")
        val id: Long?,
        @SerializedName("deleted")
        val deleted: Any?,
        @SerializedName("memberId")
        val memberId: Long?,
        @SerializedName("createTime")
        val createTime: Long?,
        @SerializedName("content")
        val content: Content?
    ) {
        /**
         * @property contentText 留言內容-文字
         * @property contentImage 留言內容-圖片
         */
        data class Content(
            @SerializedName("text")
            val contentText: String?,
            @SerializedName("image")
            val contentImage: String?
        )
    }
}