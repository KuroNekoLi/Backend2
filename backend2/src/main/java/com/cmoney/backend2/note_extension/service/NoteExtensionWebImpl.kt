package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentRequestBody
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class NoteExtensionWebImpl(
    private val service: NoteExtensionService,
    private val setting: Setting,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : NoteExtensionWeb {

    override suspend fun createComment(
        noteId: Long,
        createTime: Long,
        contentText: String?,
        contentImageUrl: String?
    ): Result<CreateCommentResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createComment(
                noteId = noteId,
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = CreateCommentRequestBody(
                    createTime = createTime,
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
        count: Int
    ): Result<List<GetCommentListByNoteIdResponseBody.Comment>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getCommentListByNoteId(
                noteId = noteId,
                commentId = commentId,
                count = count,
                authorization = setting.accessToken.createAuthorizationBearer()
            ).checkResponseBody(gson)
                ?.filterNotNull()
                .orEmpty()
        }
    }

    override suspend fun deleteComment(
        noteId: Long,
        commentId: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteComment(
                noteId = noteId,
                commentId = commentId,
                authorization = setting.accessToken.createAuthorizationBearer()
            ).handleNoContent(gson)
        }
    }

    override suspend fun getCommentCountByNoteIds(
        noteIdList: List<Long>
    ): Result<List<GetCommentCountByNoteIdsResponseBody.CommentCount>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val noteIds = noteIdList.toStringWithFormat()
                service.getCommentCountByNoteIds(
                    noteIds = noteIds,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkResponseBody(gson)
                    ?.filterNotNull()
                    .orEmpty()
            }
        }

    private fun List<Long>.toStringWithFormat(): String {
        return this.joinToString(separator = ",")
    }
}
