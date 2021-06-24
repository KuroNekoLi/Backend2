package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody

interface NoteExtensionWeb {

    /**
     *
     * NotesExtension API
     * 對指定網誌文章發一篇回文
     * @param noteId 網誌文章ID
     * @param createTime 留言創建時間
     * @param contentText 留言內容 - 文字
     * @param contentImageUrl 留言內容 - 圖片
     *
     */
    suspend fun createComment(
        noteId: Long,
        createTime: Long,
        contentText: String?,
        contentImageUrl: String?
    ): Result<CreateCommentResponseBody>

    /**
     *
     * NotesExtension API
     * 取得指定主文的回文清單
     * @param noteId 網誌文章ID
     * @param commentId 起始回文ID
     * @param count 取得回文數 (從起始算起要取得幾筆，包含起始id)
     *
     */
    suspend fun getCommentListByNoteId(
        noteId: Long,
        commentId: Long,
        count: Int
    ): Result<List<GetCommentListByNoteIdResponseBody.Comment>>

    /**
     *
     * NotesExtension API
     * 刪除回文
     * @param noteId 網誌文章ID
     * @param commentId 回文ID
     *
     */
    suspend fun deleteComment(
        noteId: Long,
        commentId: Long
    ): Result<Unit>

    /**
     *
     * NotesExtension API
     * 取得網誌回文數量
     * @param noteIdList 網誌ID(可以多個 ex. 0,1,2)
     *
     */
    suspend fun getCommentCountByNoteIds(
        noteIdList: List<Long>
    ): Result<List<GetCommentCountByNoteIdsResponseBody.CommentCount>>
}
