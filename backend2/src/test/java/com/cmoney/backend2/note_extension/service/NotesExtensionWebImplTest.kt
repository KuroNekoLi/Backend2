package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class NotesExtensionWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var noteExtensionService: NoteExtensionService
    private lateinit var noteExtensionWeb: NoteExtensionWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        noteExtensionWeb = NoteExtensionWebImpl(
            manager = manager,
            service = noteExtensionService,
            dispatcher = TestDispatcherProvider(),
            gson = gson
        )
        coEvery {
            manager.getNoteExtensionSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `createComment_check url`() =
        testScope.runTest {
            val expect = "${EXCEPT_DOMAIN}NoteExtension/api/Comment/Create/0"
            val urlSlot = slot<String>()
            val responseBody = CreateCommentResponseBody(1)
            coEvery {
                noteExtensionService.createComment(
                    url = capture(urlSlot),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(responseBody)

            noteExtensionWeb.createComment(
                noteId = 0,
                createTime = 0,
                contentText = "",
                contentImageUrl = ""
            )
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }

    @Test
    fun createComment_success() =
        testScope.runTest {
            val responseBody = CreateCommentResponseBody(1)
            coEvery {
                noteExtensionService.createComment(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(responseBody)

            val result = noteExtensionWeb
                .createComment(
                    noteId = 0,
                    createTime = 0,
                    contentText = "",
                    contentImageUrl = ""
                )
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrThrow()
            Truth.assertThat(data.commentIndex).isEqualTo(1)
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun createComment_failure() =
        testScope.runTest {
            coEvery {
                noteExtensionService.createComment(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(null)

            val result = noteExtensionWeb
                .createComment(
                    noteId = 0,
                    createTime = 0,
                    contentText = "",
                    contentImageUrl = ""
                )
            Truth.assertThat(result.isSuccess).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNotNull()
        }

    @Test
    fun `getCommentListByNoteId_check url`() =
        testScope.runTest {
            val expect = "${EXCEPT_DOMAIN}NoteExtension/api/Comment/Get/0"
            val urlSlot = slot<String>()
            val response =
                listOf(
                    GetCommentListByNoteIdResponseBody.Comment(
                        memberId = 0,
                        id = 0,
                        deleted = null,
                        createTime = null,
                        content = null
                    )
                )

            coEvery {
                noteExtensionService.getCommentListByNoteId(
                    url = capture(urlSlot),
                    authorization = any(),
                    commentId = any(),
                    count = any()
                )
            } returns Response.success(response)

            noteExtensionWeb.getCommentListByNoteId(
                noteId = 0,
                commentId = 0,
                count = 0
            )
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }

    @Test
    fun getCommentListByNoteId_success() =
        testScope.runTest {
            val response =
                listOf(
                    GetCommentListByNoteIdResponseBody.Comment(
                        memberId = 0,
                        id = 0,
                        deleted = null,
                        createTime = null,
                        content = null
                    )
                )

            coEvery {
                noteExtensionService.getCommentListByNoteId(
                    url = any(),
                    authorization = any(),
                    commentId = any(),
                    count = any()
                )
            } returns Response.success(response)

            val result = noteExtensionWeb
                .getCommentListByNoteId(
                    noteId = 0,
                    commentId = 0,
                    count = 0
                )
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrThrow()
            Truth.assertThat(data.isEmpty()).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun getCommentListByNoteId_failure() =
        testScope.runTest {
            coEvery {
                noteExtensionService.getCommentListByNoteId(
                    url = any(),
                    authorization = any(),
                    commentId = any(),
                    count = any()
                )
            } returns Response.success(null)

            val result = noteExtensionWeb
                .getCommentListByNoteId(
                    noteId = 0,
                    commentId = 0,
                    count = 0
                )
            Truth.assertThat(result.isSuccess).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNotNull()
        }

    @Test
    fun `getCommentCountByNoteIds_check url input=(1,2,3)`() =
        testScope.runTest {
            val expect = "${EXCEPT_DOMAIN}NoteExtension/api/Note/GetNoteCommentCount/0,1,2"
            val urlSlot = slot<String>()
            val response =
                listOf(
                    GetCommentCountByNoteIdsResponseBody.CommentCount(
                        noteId = 1,
                        count = 1,
                        deletedCount = 1
                    )
                )

            coEvery {
                noteExtensionService.getCommentCountByNoteIds(
                    url = capture(urlSlot),
                    authorization = any()
                )
            } returns Response.success(response)

            noteExtensionWeb.getCommentCountByNoteIds(
                noteIdList = listOf(0, 1, 2)
            )
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }

    @Test
    fun `getCommentCountByNoteIds_check url input(1)`() =
        testScope.runTest {
            val expect = "${EXCEPT_DOMAIN}NoteExtension/api/Note/GetNoteCommentCount/0"
            val urlSlot = slot<String>()
            val response =
                listOf(
                    GetCommentCountByNoteIdsResponseBody.CommentCount(
                        noteId = 1,
                        count = 1,
                        deletedCount = 1
                    )
                )

            coEvery {
                noteExtensionService.getCommentCountByNoteIds(
                    url = capture(urlSlot),
                    authorization = any()
                )
            } returns Response.success(response)

            noteExtensionWeb.getCommentCountByNoteIds(
                noteIdList = listOf(0)
            )
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }

    @Test
    fun getCommentCountByNoteIds_success() =
        testScope.runTest {
            val response =
                listOf(
                    GetCommentCountByNoteIdsResponseBody.CommentCount(
                        noteId = 1,
                        count = 1,
                        deletedCount = 1
                    )
                )

            coEvery {
                noteExtensionService.getCommentCountByNoteIds(
                    url = any(),
                    authorization = any()
                )
            } returns Response.success(response)

            val result = noteExtensionWeb
                .getCommentCountByNoteIds(
                    noteIdList = listOf(0, 1, 2)
                )
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrThrow()
            Truth.assertThat(data.isEmpty()).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun getCommentCountByNoteIds_failure() =
        testScope.runTest {
            coEvery {
                noteExtensionService.getCommentCountByNoteIds(
                    url = any(),
                    authorization = any()
                )
            } returns Response.success(null)

            val result = noteExtensionWeb
                .getCommentCountByNoteIds(
                    noteIdList = listOf(0, 1, 2)
                )
            Truth.assertThat(result.isSuccess).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNotNull()
        }

    @Test
    fun `deleteComment_check url`() =
        testScope.runTest {
            val expect = "${EXCEPT_DOMAIN}NoteExtension/api/Comment/Delete/0/0"
            val urlSlot = slot<String>()
            val response: Void? = null
            coEvery {
                noteExtensionService.deleteComment(
                    url = capture(urlSlot),
                    authorization = any()
                )
            } returns Response.success(204, response)

            noteExtensionWeb.deleteComment(
                noteId = 0,
                commentId = 0
            )
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }

    @Test
    fun deleteComment_success() =
        testScope.runTest {
            val response: Void? = null
            coEvery {
                noteExtensionService.deleteComment(
                    url = any(),
                    authorization = any()
                )
            } returns Response.success(204, response)

            val result = noteExtensionWeb
                .deleteComment(
                    noteId = 0,
                    commentId = 0
                )
            Truth.assertThat(result.isSuccess).isTrue()
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun deleteComment_failure() =
        testScope.runTest {
            coEvery {
                noteExtensionService.deleteComment(
                    url = any(),
                    authorization = any()
                )
            } returns Response.success(null)

            val result = noteExtensionWeb
                .deleteComment(
                    noteId = 0,
                    commentId = 0
                )
            Truth.assertThat(result.isSuccess).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNotNull()
        }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}
