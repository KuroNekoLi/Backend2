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
            createRelayResult.logResponse(TAG)

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
            // 2023/05/12 測試機有髒資料刪除會失敗
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