package com.cmoney.backend2.notes.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsRequestBody
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsResponseBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.GetNotesByTagsRequestBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.Note
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesRequestBody
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotes.GetNotesResponseBody
import com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags.GetNotesByTagsResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class NotesWebImpl(
    private val service: NotesService,
    private val setting: Setting,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : NotesWeb {

    override suspend fun fetchWritingPost(
        apiParam: MemberApiParam,
        noteId: Long,
        fetchCount: Int,
        tags: List<String>
    ): Result<List<Note>> = fetchWritingPost(noteId, fetchCount, tags)

    override suspend fun fetchWritingPost(
        noteId: Long,
        fetchCount: Int,
        tags: List<String>
    ): Result<List<Note>> = withContext(dispatcher.io()) {
        runCatching {
            service.getNotesByTags(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetNotesByTagsRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    tagIds = tags.map { it.toInt() },
                    baseNoteId = noteId,
                    fetchSize = fetchCount
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getNotes(
        apiParam: MemberApiParam,
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        blogId: Int,
        hasPayNotes: Boolean
    ): Result<GetNotesResponseBody> = getNotes(noteId, fetchSize, fetchDay, blogId, hasPayNotes)

    override suspend fun getNotes(
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        blogId: Int,
        hasPayNotes: Boolean
    ): Result<GetNotesResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotes(
                authorization = setting.accessToken.createAuthorizationBearer(),
                action = "GetNotes",
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                noteId = noteId,
                fetchSize = fetchSize,
                fetchDay = fetchDay,
                blogId = blogId,
                hasPayNotes = hasPayNotes
            )
                .checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getNotesByTagsUsingNotesApi(
        apiParam: MemberApiParam,
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        tags: List<Long>,
        hasPayNotes: Boolean,
        isShowAllFree: Boolean
    ): Result<GetNotesByTagsResponseBody> = getNotesByTagsUsingNotesApi(
        noteId, fetchSize, fetchDay, tags, hasPayNotes, isShowAllFree
    )

    override suspend fun getNotesByTagsUsingNotesApi(
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        tags: List<Long>,
        hasPayNotes: Boolean,
        isShowAllFree: Boolean
    ): Result<GetNotesByTagsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotesByTagsUsingNotesApi(
                authorization = setting.accessToken.createAuthorizationBearer(),
                action = "GetNotesByTags",
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                noteId = noteId,
                fetchSize = fetchSize,
                fetchDay = fetchDay,
                tags = tags.joinToString(separator = ","),
                hasPayNotes = hasPayNotes,
                isShowAllFree = isShowAllFree
            )
                .checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getPopularAndPayNotes(
        blogId: Long,
        fetchSize: Int
    ): Result<List<GetPopularAndPayNotesResponseBodyWithError.Note>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPopularAndPayNotes(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = GetPopularAndPayNotesRequestBody(
                        appId = setting.appId,
                        blogId = blogId,
                        fetchSize = fetchSize
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
                    ?.filterNotNull()
                    .orEmpty()
            }
        }

    override suspend fun getNotesByCoAuthorIds(
        coAuthorIds: List<Long>,
        baseNoteId: Long,
        fetchSize: Int
    ): Result<List<GetNotesByCoAuthorIdsResponseBody.Note>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotesByCoAuthorIds(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetNotesByCoAuthorIdsRequestBody(
                    fetchSize = fetchSize,
                    coAuthorIds = coAuthorIds,
                    baseNoteId = baseNoteId
                )
            ).checkResponseBody(gson = gson)
                .notes?.filterNotNull()
                .orEmpty()
        }
    }
}
