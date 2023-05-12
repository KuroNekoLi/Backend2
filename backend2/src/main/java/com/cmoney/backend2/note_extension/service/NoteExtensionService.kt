package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
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
    @RecordApi
    @POST
    suspend fun createComment(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: CreateCommentRequestBody
    ): Response<CreateCommentResponseBody>

    /**
     * NotesExtension API
     * 取得指定主文的回文清單
     */
    @RecordApi
    @GET
    suspend fun getCommentListByNoteId(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("commentId") commentId: Long,
        @Query("count") count: Int
    ): Response<List<GetCommentListByNoteIdResponseBody.Comment?>>

    /**
     * NotesExtension API
     * 刪除回文
     */
    @RecordApi
    @DELETE
    suspend fun deleteComment(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * NotesExtension API
     * 取得網誌回文數量
     */
    @RecordApi
    @GET
    suspend fun getCommentCountByNoteIds(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<GetCommentCountByNoteIdsResponseBody.CommentCount?>>
}
