package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.notes.service.NotesWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class NotesServiceCase : ServiceCase {
    private val notesWeb by inject<NotesWeb>()

    override suspend fun testAll() {
        notesWeb.apply {
            this.fetchWritingPost(
                9223372036854775807,
                10,
                listOf("58836")
            ).logResponse(TAG)
            getNotes(
                9223372036854775807,
                10,
                90,
                234152,
                false
            ).logResponse(TAG)
            getNotesByTagsUsingNotesApi(
                    9223372036854775807,
                    10,
                    90,
                    listOf(70219,70220),
                    false,
                    false
            ).logResponse(TAG)
            getPopularAndPayNotes(
                blogId = 22814,
                fetchSize = 4
            ).logResponse(TAG)
            getNotesByCoAuthorIds(
                coAuthorIds = listOf(0,1),
                baseNoteId = 0,
                fetchSize = 10
            ).logResponse(TAG)

        }
    }

    companion object {
        private const val TAG = "Notes"
    }
}