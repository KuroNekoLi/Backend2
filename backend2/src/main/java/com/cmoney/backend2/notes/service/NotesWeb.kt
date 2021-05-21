package com.cmoney.backend2.notes.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsResponseBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.Note
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotes.GetNotesResponseBody
import com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags.GetNotesByTagsResponseBody

interface NotesWeb {

    /**
     * 服務2. 取得網誌文章ByTag分類
     *
     */
    @Deprecated("ApiParam no longer required")
    suspend fun fetchWritingPost(
        apiParam: MemberApiParam,
        noteId: Long,
        fetchCount: Int,
        tags: List<String>
    ): Result<List<Note>>

    /**
     * 服務2. 取得網誌文章ByTag分類
     */
    suspend fun fetchWritingPost(
        noteId: Long,
        fetchCount: Int,
        tags: List<String>
    ): Result<List<Note>>

    @Deprecated("ApiParam no longer required")
    suspend fun getNotes(
        apiParam: MemberApiParam,
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        blogId: Int,
        hasPayNotes: Boolean
    ): Result<GetNotesResponseBody>

    /**
     * Notes API
     * 服務1. 取得網誌文章
     * @param noteId 從哪個文章編號以後開始取(預設:long.Max)
     * @param fetchSize 一次取最多多少篇文章(預設:int.Max)
     * @param fetchDay 撈多久天以前的資料(預設:7)
     * @param blogId 只撈取目標只訂網誌Blog的文章(預設:22814)
     * @param hasPayNotes 是否含付費文章(預設:false)
     */
    suspend fun getNotes(
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        blogId: Int,
        hasPayNotes: Boolean
    ): Result<GetNotesResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun getNotesByTagsUsingNotesApi(
        apiParam: MemberApiParam,
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        tags: List<Long>,
        hasPayNotes: Boolean,
        isShowAllFree: Boolean
    ): Result<GetNotesByTagsResponseBody>

    /**
     * Notes API
     * 服務2. 取得網誌文章ByTag分類
     * @param noteId 從哪個文章編號以後開始取(預設:long.Max)
     * @param fetchSize 一次取最多多少篇文章(預設:int.Max)
     * @param fetchDay 撈多久天以前的資料(預設:7)
     * @param tags 只撈取目標指定網誌tags的文章(用 , 分隔)
     * @param hasPayNotes 是否含付費文章(預設:false)
     * @param isShowAllFree 免費文章是否無視Tags限制 (預設false)
     */
    suspend fun getNotesByTagsUsingNotesApi(
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        tags: List<Long>,
        hasPayNotes: Boolean,
        isShowAllFree: Boolean
    ): Result<GetNotesByTagsResponseBody>


    /**
     *
     * Notes Service API
     * 取得網誌近三天觀看次數達5000以上以及近一日付費文章清單
     * 目前只能在正式機環境取到資料
     *
     * @param blogId 部落格編號
     * @param fetchSize 取得幾筆
     */
    suspend fun getPopularAndPayNotes(
        blogId: Long,
        fetchSize: Int
    ): Result<List<GetPopularAndPayNotesResponseBodyWithError.Note>>


    /**
     *
     * Notes Service API
     * 取得指定網誌作者(撰文者)的網誌文章清單
     * @param coAuthorIds 撰文者編號清單(可多位)
     * @param baseNoteId 上次讀取過的網誌編號
     * @param fetchSize 取得幾筆(多位作者加總筆數)
     */
    suspend fun getNotesByCoAuthorIds(
        coAuthorIds: List<Long>,
        baseNoteId: Long,
        fetchSize: Int
    ): Result<List<GetNotesByCoAuthorIdsResponseBody.Note>>
}
