package com.cmoney.backend2.notes.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsRequestBody
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsResponseBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.GetNotesByTagsRequestBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.Note
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesRequestBody
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotes.GetNotesResponseBody
import com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags.GetNotesByTagsResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class NotesWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: NotesService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : NotesWeb {

    override suspend fun fetchWritingPost(
        noteId: Long,
        fetchCount: Int,
        tags: List<String>,
        domain: String,
        url: String
    ): Result<List<Note>> = withContext(dispatcher.io()) {
        runCatching {
            service.getNotesByTags(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetNotesByTagsRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        blogId: Int,
        hasPayNotes: Boolean,
        domain: String,
        url: String
    ): Result<GetNotesResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotes(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
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
        noteId: Long,
        fetchSize: Int,
        fetchDay: Int,
        tags: List<Long>,
        hasPayNotes: Boolean,
        isShowAllFree: Boolean,
        domain: String,
        url: String
    ): Result<GetNotesByTagsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotesByTagsUsingNotesApi(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
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
        fetchSize: Int,
        domain: String,
        url: String
    ): Result<List<GetPopularAndPayNotesResponseBodyWithError.Note>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPopularAndPayNotes(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = GetPopularAndPayNotesRequestBody(
                        appId = manager.getAppId(),
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
        fetchSize: Int,
        domain: String,
        url: String
    ): Result<List<GetNotesByCoAuthorIdsResponseBody.Note>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getNotesByCoAuthorIds(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
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
