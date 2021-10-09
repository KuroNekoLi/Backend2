package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.note_extension.service.NoteExtensionWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class NoteExtensionServiceCase : ServiceCase {
    private val web by inject<NoteExtensionWeb>()

    override suspend fun testAll() {
        web.apply {
            val createRelayResult = createComment(
                noteId = NOTE_ID,
                createTime = System.currentTimeMillis(),
                contentText = "test content Text!!",
                contentImageUrl = "test Image"
            )

            val commentIndex = createRelayResult.getOrNull()?.commentIndex ?: return
            getCommentListByNoteId(
                noteId = NOTE_ID,
                //取前20
                commentId = (commentIndex - 20).coerceAtLeast(0),
                count = 20
            ).logResponse(TAG)
            getCommentCountByNoteIds(
                noteIdList = listOf(NOTE_ID)
            ).logResponse(TAG)
            deleteComment(
                noteId = NOTE_ID,
                commentId = commentIndex
            ).logResponse(TAG)
        }
    }

    companion object {
        private const val TAG = "NoteExtension"
        private const val NOTE_ID = 2L
    }
}