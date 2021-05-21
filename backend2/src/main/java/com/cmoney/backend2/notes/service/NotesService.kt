package com.cmoney.backend2.notes.service

import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsRequestBody
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsResponseBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.GetNotesByTagsRequestBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.GetNotesByTagsResponseWithError
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesRequestBody
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotes.GetNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags.GetNotesByTagsResponseBodyWithError
import retrofit2.Response
import retrofit2.http.*

/**
 * 網誌 - Notes API 服務
 * 需在Swagger上查看
 */
interface NotesService {


    /**
     * Notes API
     * 服務1. 取得網誌文章
     */
    @FormUrlEncoded
    @POST("notes/ashx/chipkapi.ashx")
    suspend fun getNotes(
        @Header("Authorization") authorization: String,
        @Field("action") action: String,
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("noteid") noteId: Long,
        @Field("fetchsize") fetchSize: Int,
        @Field("fetchday") fetchDay: Int,
        @Field("blogid") blogId: Int,
        @Field("hasPayNotes") hasPayNotes: Boolean
    ): Response<GetNotesResponseBodyWithError>

    /**
     * Notes API
     * 服務2. 取得網誌文章ByTag分類
     */
    @FormUrlEncoded
    @POST("notes/ashx/chipkapi.ashx")
    suspend fun getNotesByTagsUsingNotesApi(
        @Header("Authorization") authorization: String,
        @Field("action") action: String,
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("noteid") noteId: Long,
        @Field("fetchsize") fetchSize: Int,
        @Field("fetchday") fetchDay: Int,
        @Field("tags") tags: String,
        @Field("hasPayNotes") hasPayNotes: Boolean,
        @Field("isShowAllFree") isShowAllFree: Boolean
    ): Response<GetNotesByTagsResponseBodyWithError>

    /**
     *
     * Notes Services
     * 取得指定標籤的網誌文章清單
     *
     */
    @POST("NotesService/Notes/GetNotesByTags")
    suspend fun getNotesByTags(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetNotesByTagsRequestBody
    ): Response<GetNotesByTagsResponseWithError>
    /**
     *
     * Notes Services
     * 取得網誌近三天觀看次數達5000以上以及近一日付費文章清單
     */
    @POST("NotesService/Notes/GetPopularAndPayNotes")
    suspend fun getPopularAndPayNotes(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetPopularAndPayNotesRequestBody
    ): Response<GetPopularAndPayNotesResponseBodyWithError>

    /**
     *
     * Notes Services
     * 取得指定網誌作者(撰文者)的網誌文章清單
     */
    @POST("NotesService/Notes/GetNotesByCoAuthorIds")
    suspend fun getNotesByCoAuthorIds(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetNotesByCoAuthorIdsRequestBody
    ): Response<GetNotesByCoAuthorIdsResponseBody>
}
