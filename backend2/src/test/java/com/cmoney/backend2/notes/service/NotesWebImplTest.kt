package com.cmoney.backend2.notes.service

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsResponseBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.GetNotesByTagsResponseWithError
import com.cmoney.backend2.notes.service.api.getnotesbytags.Note
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotes.GetNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags.GetNotesByTagsResponseBodyWithError
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
class NotesWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var noteService: NotesService
    private lateinit var noteWeb: NotesWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        noteWeb = NotesWebImpl(
            manager = manager,
            service = noteService,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getNotesSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `fetchWritingPost_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}NotesService/Notes/GetNotesByTags"
        val urlSlot = slot<String>()
        coEvery {
            noteService.getNotesByTags(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(
            GetNotesByTagsResponseWithError(
                notes = listOf(
                    Note(
                        noteId = 0,
                        title = "",
                        image = "",
                        createTime = 0,
                        viewCount = 0,
                        price = 0,
                        coAuthorName = "",
                        tags = emptyList()
                    )
                )
            )
        )
        noteWeb.fetchWritingPost(
            noteId = 0,
            fetchCount = 100,
            tags = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun fetchWritingPost_success() = testScope.runTest {
        coEvery {
            noteService.getNotesByTags(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(
            GetNotesByTagsResponseWithError(
                notes = listOf(
                    Note(
                        noteId = 0,
                        title = "",
                        image = "",
                        createTime = 0,
                        viewCount = 0,
                        price = 0,
                        coAuthorName = "",
                        tags = emptyList()
                    )
                )
            )
        )
        val result = noteWeb.fetchWritingPost(
            noteId = 0,
            fetchCount = 100,
            tags = emptyList()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isGreaterThan(0)
    }

    @Test
    fun fetchWritingPost_failure() = testScope.runTest {
        val response = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }"""
        coEvery {
            noteService.getNotesByTags(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(
            gson.fromJson(
                response,
                GetNotesByTagsResponseWithError::class.java
            )
        )
        val result = noteWeb.fetchWritingPost(
            0,
            100,
            emptyList()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val serverException = result.exceptionOrNull() as ServerException
        Truth.assertThat(serverException).isNotNull()
    }

    @Test
    fun `getNotes_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notes/ashx/chipkapi.ashx"
        val urlSlot = slot<String>()
        //準備api成功時的回傳
        val responseBody = GetNotesResponseBodyWithError(
            notes = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            noteService.getNotes(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                noteId = any(),
                fetchSize = any(),
                fetchDay = any(),
                blogId = any(),
                hasPayNotes = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        noteWeb.getNotes(
            noteId = 9223372036854775807,
            fetchSize = 2,
            fetchDay = 90,
            blogId = 234152,
            hasPayNotes = false
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNotes_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetNotesResponseBodyWithError(
            notes = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            noteService.getNotes(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                url = any(),
                noteId = any(),
                fetchSize = any(),
                fetchDay = any(),
                blogId = any(),
                hasPayNotes = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = noteWeb.getNotes(
            9223372036854775807,
            2,
            90,
            234152,
            false
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.notes?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getNotes_failure() = testScope.runTest {
        //設定api成功時的回傳
        coEvery {
            noteService.getNotes(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                url = any(),
                noteId = any(),
                fetchSize = any(),
                fetchDay = any(),
                blogId = any(),
                hasPayNotes = any()
            )
        } returns Response.success(null)

        //確認api是否成功
        val result = noteWeb.getNotes(
            9223372036854775807,
            2,
            90,
            234152,
            false
        )
        //錯誤時還是有回傳200 ok,只是沒有任何值
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as EmptyBodyException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getNotesByTagsUsingNotesApi_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notes/ashx/chipkapi.ashx"
        val urlSlot = slot<String>()
        //準備api成功時的回傳
        val responseBody = GetNotesByTagsResponseBodyWithError(
            notes = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            noteService.getNotesByTagsUsingNotesApi(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                noteId = any(),
                fetchSize = any(),
                fetchDay = any(),
                tags = any(),
                hasPayNotes = any(),
                isShowAllFree = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        noteWeb.getNotesByTagsUsingNotesApi(
            noteId = 9223372036854775807,
            fetchSize = 2,
            fetchDay = 90,
            tags = listOf(70219, 720220),
            hasPayNotes = false,
            isShowAllFree = false
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNotesByTagsUsingNotesApi_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetNotesByTagsResponseBodyWithError(
            notes = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            noteService.getNotesByTagsUsingNotesApi(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                url = any(),
                noteId = any(),
                fetchSize = any(),
                fetchDay = any(),
                tags = any(),
                hasPayNotes = any(),
                isShowAllFree = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = noteWeb.getNotesByTagsUsingNotesApi(
            9223372036854775807,
            2,
            90,
            listOf(70219, 720220),
            false,
            false
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.notes?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getNotesByTagsUsingNotesApi_failure() = testScope.runTest {
        //設定api成功時的回傳
        coEvery {
            noteService.getNotesByTagsUsingNotesApi(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                url = any(),
                noteId = any(),
                fetchSize = any(),
                fetchDay = any(),
                tags = any(),
                hasPayNotes = any(),
                isShowAllFree = any()
            )
        } returns Response.success(null)

        //確認api是否成功
        val result = noteWeb.getNotesByTagsUsingNotesApi(
            noteId = 9223372036854775807,
            fetchSize = 2,
            fetchDay = 90,
            tags = listOf(70219, 720220),
            hasPayNotes = false,
            isShowAllFree = false
        )
        //錯誤時還是有回傳200 ok,只是沒有任何值
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as EmptyBodyException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getPopularAndPayNotes_check url`() =
        testScope.runTest {
            val expect = "${EXCEPT_DOMAIN}NotesService/Notes/GetPopularAndPayNotes"
            val urlSlot = slot<String>()
            val responseBody = GetPopularAndPayNotesResponseBodyWithError(
                notes = listOf()
            )
            coEvery {
                noteService.getPopularAndPayNotes(
                    url = capture(urlSlot),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(responseBody)

            //確認api是否成功
            noteWeb.getPopularAndPayNotes(
                blogId = 22814,
                fetchSize = 4
            )
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }

    @Test
    fun getPopularAndPayNotes_success() =
        testScope.runTest {
            val responseBody = GetPopularAndPayNotesResponseBodyWithError(
                notes = listOf()
            )
            coEvery {
                noteService.getPopularAndPayNotes(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(responseBody)

            //確認api是否成功
            val result = noteWeb.getPopularAndPayNotes(
                blogId = 22814,
                fetchSize = 4
            )
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrThrow()
            Truth.assertThat(data.size).isEqualTo(0)
            Truth.assertThat(result.exceptionOrNull()).isNull()
        }

    @Test
    fun getPopularAndPayNotes_failure() =
        testScope.runTest {
            coEvery {
                noteService.getPopularAndPayNotes(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(null)

            //確認api是否成功
            val result = noteWeb.getPopularAndPayNotes(
                blogId = 22814,
                fetchSize = 4
            )
            //錯誤時還是有回傳200 ok,只是沒有任何值
            Truth.assertThat(result.isSuccess).isFalse()
            val exception = result.exceptionOrNull() as EmptyBodyException
            Truth.assertThat(exception).isNotNull()
        }

    @Test
    fun `getNotesByCoAuthorIds_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}NotesService/Notes/GetNotesByCoAuthorIds"
        val urlSlot = slot<String>()
        val responseBody = GetNotesByCoAuthorIdsResponseBody(
            notes = listOf()
        )
        coEvery {
            noteService.getNotesByCoAuthorIds(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(
            responseBody
        )

        //確認api是否成功
        noteWeb.getNotesByCoAuthorIds(
            fetchSize = 2,
            coAuthorIds = listOf(),
            baseNoteId = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNotesByCoAuthorIds_success() = testScope.runTest {
        val responseBody = GetNotesByCoAuthorIdsResponseBody(
            notes = listOf()
        )
        coEvery {
            noteService.getNotesByCoAuthorIds(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(
            responseBody
        )

        //確認api是否成功
        val result = noteWeb.getNotesByCoAuthorIds(
            fetchSize = 2,
            coAuthorIds = listOf(),
            baseNoteId = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getNotesByCoAuthorIds_failure() = testScope.runTest {
        coEvery {
            noteService.getNotesByCoAuthorIds(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(null)

        //確認api是否成功
        val result = noteWeb.getNotesByCoAuthorIds(
            fetchSize = 2,
            coAuthorIds = listOf(),
            baseNoteId = 1
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as EmptyBodyException
        Truth.assertThat(exception).isNotNull()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}