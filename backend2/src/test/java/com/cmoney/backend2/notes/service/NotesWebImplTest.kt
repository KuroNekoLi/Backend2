package com.cmoney.backend2.notes.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds.GetNotesByCoAuthorIdsResponseBody
import com.cmoney.backend2.notes.service.api.getnotesbytags.GetNotesByTagsResponseWithError
import com.cmoney.backend2.notes.service.api.getnotesbytags.Note
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotes.GetNotesResponseBodyWithError
import com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags.GetNotesByTagsResponseBodyWithError
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class NotesWebImplTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var noteService: NotesService
    private lateinit var noteWeb: NotesWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        noteWeb = NotesWebImpl(
            service = noteService,
            setting = setting,
            dispatcher = TestDispatcher(),
            gson = gson
        )
    }

    @Test
    fun fetchWritingPost_success() = mainCoroutineRule.runBlockingTest {
        coEvery {
            noteService.getNotesByTags(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(
            GetNotesByTagsResponseWithError(
                notes = listOf(
                    Note(
                        0,
                        "",
                        "",
                        0,
                        0,
                        0,
                        ""
                    )
                )
            )
        )
        val result = noteWeb.fetchWritingPost(
            0,
            100,
            emptyList()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isGreaterThan(0)
    }

    @Test
    fun fetchWritingPost_failure() = mainCoroutineRule.runBlockingTest {
        val response = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }"""
        coEvery {
            noteService.getNotesByTags(
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
    fun `getNotes取得網誌文章 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetNotesResponseBodyWithError(
            notes = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            noteService.getNotes(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
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
    fun `getNotes取得網誌文章 失敗`() = mainCoroutineRule.runBlockingTest {
        //設定api成功時的回傳
        coEvery {
            noteService.getNotes(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
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
    fun `getNotesByTagsUsingNotesApi取得網誌文章ByTag分類 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetNotesByTagsResponseBodyWithError(
            notes = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            noteService.getNotesByTagsUsingNotesApi(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
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
    fun `getNotesByTagsUsingNotesApi取得網誌文章ByTag分類 失敗`() = mainCoroutineRule.runBlockingTest {
        //設定api成功時的回傳
        coEvery {
            noteService.getNotesByTagsUsingNotesApi(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
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
            9223372036854775807,
            2,
            90,
            listOf(70219, 720220),
            false,
            false
        )
        //錯誤時還是有回傳200 ok,只是沒有任何值
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as EmptyBodyException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getPopularAndPayNotes取得網誌近三天觀看次數達5000以上以及近一日付費文章 成功`() =
        mainCoroutineRule.runBlockingTest {
            val responseBody = GetPopularAndPayNotesResponseBodyWithError(
                notes = listOf()
            )
            coEvery {
                noteService.getPopularAndPayNotes(
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
    fun `getPopularAndPayNotes取得網誌近三天觀看次數達5000以上以及近一日付費文章 失敗`() =
        mainCoroutineRule.runBlockingTest {

            coEvery {
                noteService.getPopularAndPayNotes(
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
    fun `getNotesByCoAuthorIds取得指定網誌作者(撰文者)的網誌文章清單 成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetNotesByCoAuthorIdsResponseBody(
            notes = listOf()
        )
        coEvery {
            noteService.getNotesByCoAuthorIds(
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
    fun `getNotesByCoAuthorIds取得指定網誌作者(撰文者)的網誌文章清單 失敗`() = mainCoroutineRule.runBlockingTest {

        coEvery {
            noteService.getNotesByCoAuthorIds(
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
}