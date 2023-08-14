package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody

interface NoteExtensionWeb {

    val manager: GlobalBackend2Manager

    /**
     *
     * NotesExtension API
     * 對指定網誌文章發一篇回文
     *
     * @param noteId 網誌文章ID
     * @param createTime 留言創建時間
     * @param contentText 留言內容 - 文字
     * @param contentImageUrl 留言內容 - 圖片
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun createComment(
        noteId: Long,
        createTime: Long,
        contentText: String?,
        contentImageUrl: String?,
        domain: String = manager.getNoteExtensionSettingAdapter().getDomain(),
        url: String = "${domain}NoteExtension/api/Comment/Create/$noteId"
    ): Result<CreateCommentResponseBody>

    /**
     *
     * NotesExtension API
     * 取得指定主文的回文清單
     *
     * @param noteId 網誌文章ID
     * @param commentId 起始回文ID
     * @param count 取得回文數 (從起始算起要取得幾筆，包含起始id)
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getCommentListByNoteId(
        noteId: Long,
        commentId: Long,
        count: Int,
        domain: String = manager.getNoteExtensionSettingAdapter().getDomain(),
        url: String = "${domain}NoteExtension/api/Comment/Get/$noteId"
    ): Result<List<GetCommentListByNoteIdResponseBody.Comment>>

    /**
     *
     * NotesExtension API
     * 刪除回文
     *
     * @param noteId 網誌文章ID
     * @param commentId 回文ID
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun deleteComment(
        noteId: Long,
        commentId: Long,
        domain: String = manager.getNoteExtensionSettingAdapter().getDomain(),
        url: String = "${domain}NoteExtension/api/Comment/Delete/${noteId}/${commentId}"
    ): Result<Unit>

    /**
     *
     * NotesExtension API
     * 取得網誌回文數量
     *
     * @param noteIdList 網誌ID(可以多個 ex. 0,1,2)
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getCommentCountByNoteIds(
        noteIdList: List<Long>,
        domain: String = manager.getNoteExtensionSettingAdapter().getDomain(),
        url: String = "${domain}NoteExtension/api/Note/GetNoteCommentCount/${noteIdList.joinToString(separator = ",")}"
    ): Result<List<GetCommentCountByNoteIdsResponseBody.CommentCount>>
}
