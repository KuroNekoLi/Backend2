package com.cmoney.backend2.note_extension.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.note_extension.service.api.createreply.CreateCommentResponseBody
import com.cmoney.backend2.note_extension.service.api.getnotecommentcount.GetCommentCountByNoteIdsResponseBody
import com.cmoney.backend2.note_extension.service.api.getreplylistbyid.GetCommentListByNoteIdResponseBody
import com.cmoney.core.CoroutineTestRule

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        noteExtensionWeb = NoteExtensionWebImpl(
            service = noteExtensionService,
            setting = setting,
            dispatcher = TestDispatcher(),
            gson = gson
        )
    }

    @Test
    fun formatTest() {
        val result = listOf<Long>(1, 2, 3, 4).toStringWithFormat()
        val data = "1,2,3,4"
        Truth.assertThat(data).isEqualTo(result)
    }

    private fun List<Long>.toStringWithFormat(): String {
        return this.toString().filter {
            it != '[' &&
                it != ']' &&
                it != ' '
        }
    }

    @Test
    fun `createReply取得指定主文的回文清單 成功`() =
        testScope.runTest {
            val responseBody = CreateCommentResponseBody(1)
            coEvery {
                noteExtensionService.createComment(
                    authorization = any(),
                    requestBody = any(),
                    noteId = any()
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
    fun `createReply取得指定主文的回文清單 失敗`() =
        testScope.runTest {
            coEvery {
                noteExtensionService.createComment(
                    authorization = any(),
                    requestBody = any(),
                    noteId = any()
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
    fun `getReplyListById取得指定主文的回文清單 成功`() =
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
                    authorization = any(),
                    noteId = any(),
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
            Truth.assertThat(data.isNullOrEmpty()).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun `getReplyListById取得指定主文的回文清單 失敗`() =
        testScope.runTest {
            coEvery {
                noteExtensionService.getCommentListByNoteId(
                    authorization = any(),
                    noteId = any(),
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
    fun `getNoteCommentCount取得網誌回文數量 成功`() =
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
                    authorization = any(),
                    noteIds = any()
                )
            } returns Response.success(response)

            val result = noteExtensionWeb
                .getCommentCountByNoteIds(
                    noteIdList = listOf(0, 1, 2)
                )
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrThrow()
            Truth.assertThat(data.isNullOrEmpty()).isFalse()
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun `getNoteCommentCount取得網誌回文數量 失敗`() =
        testScope.runTest {
            coEvery {
                noteExtensionService.getCommentCountByNoteIds(
                    authorization = any(),
                    noteIds = any()
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
    fun `deleteReply刪除回文 成功`() =
        testScope.runTest {
            val response: Void? = null
            coEvery {
                noteExtensionService.deleteComment(
                    authorization = any(),
                    noteId = any(),
                    commentId = any()
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
    fun `deleteReply刪除回文 失敗`() =
        testScope.runTest {
            coEvery {
                noteExtensionService.deleteComment(
                    authorization = any(),
                    noteId = any(),
                    commentId = any()
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
}
