package com.cmoney.backend2.note_extension.service.api.getnotecommentcount

import com.google.gson.annotations.SerializedName

class GetCommentCountByNoteIdsResponseBody {
    /**
     * 回文數量
     *
     * @param noteId 文章編號
     * @param count 回文數量
     * @param deletedCount 已刪除回文的數量
     */

    data class CommentCount(
        @SerializedName("id")
        val noteId: Long?,
        @SerializedName("count")
        val count: Int?,
        @SerializedName("deletedCount")
        val deletedCount: Int?
    )
}