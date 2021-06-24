package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentRequestBody
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NoteExtensionService {

    /**
     * NotesExtension API
     * 對指定網誌文章發一篇回文
     */
    @POST("NoteExtension/api/Comment/Create/{noteId}")
    suspend fun createComment(
        @Path("noteId") noteId: Long,
        @Header("Authorization") authorization: String,
        @Body requestBody: CreateCommentRequestBody
    ): Response<CreateCommentResponseBody>

    /**
     * NotesExtension API
     * 取得指定主文的回文清單
     */
    @GET("NoteExtension/api/Comment/Get/{noteId}")
    suspend fun getCommentListByNoteId(
        @Path("noteId") noteId: Long,
        @Query("commentId") commentId: Long,
        @Query("count") count: Int,
        @Header("Authorization") authorization: String
    ): Response<List<GetCommentListByNoteIdResponseBody.Comment?>?>

    /**
     * NotesExtension API
     * 刪除回文
     */
    @DELETE("NoteExtension/api/Comment/Delete/{noteId}/{commentId}")
    suspend fun deleteComment(
        @Path("noteId") noteId: Long,
        @Path("commentId") commentId: Long,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * NotesExtension API
     * 取得網誌回文數量
     */
    @GET("NoteExtension/api/Note/GetNoteCommentCount/{noteIds}")
    suspend fun getCommentCountByNoteIds(
        @Path("noteIds") noteIds: String,
        @Header("Authorization") authorization: String
    ): Response<List<GetCommentCountByNoteIdsResponseBody.CommentCount?>?>
}
