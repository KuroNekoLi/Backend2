package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentRequestBody
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class NoteExtensionWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: NoteExtensionService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : NoteExtensionWeb {

    override suspend fun createComment(
        noteId: Long,
        createTime: Long,
        contentText: String?,
        contentImageUrl: String?,
        domain: String,
        url: String
    ): Result<CreateCommentResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createComment(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = CreateCommentRequestBody(
                    createTime = TimeUnit.MILLISECONDS.toSeconds(createTime),
                    content = CreateCommentRequestBody.Content(
                        text = contentText,
                        image = contentImageUrl
                    )
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getCommentListByNoteId(
        noteId: Long,
        commentId: Long,
        count: Int,
        domain: String,
        url: String
    ): Result<List<GetCommentListByNoteIdResponseBody.Comment>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommentListByNoteId(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                commentId = commentId,
                count = count
            ).checkResponseBody(gson)
                .filterNotNull()
        }
    }

    override suspend fun deleteComment(
        noteId: Long,
        commentId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteComment(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).handleNoContent(gson)
        }
    }

    override suspend fun getCommentCountByNoteIds(
        noteIdList: List<Long>,
        domain: String,
        url: String
    ): Result<List<GetCommentCountByNoteIdsResponseBody.CommentCount>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getCommentCountByNoteIds(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
                    .filterNotNull()
            }
        }
}
