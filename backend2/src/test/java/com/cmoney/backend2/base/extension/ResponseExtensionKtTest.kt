package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ResponseExtensionKtTest {

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Test(expected = ServerException::class)
    fun `checkIWithError_有錯誤_throw ServerException`() {
        val errorJson = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        cmoneyError.checkIWithError()
    }

    @Test
    fun `checkIWithError_沒有錯誤_回傳自己`() {
        val errorJson = """
            {
                "field1":"value"
            }
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        val actual = cmoneyError.checkIWithError()
        assertThat(actual).isEqualTo(cmoneyError)
    }

    @Test
    fun `checkISuccess_成功_回傳自己`() {
        val originalSuccess = object : ISuccess {
            override fun getErrorMessage(): String {
                return ISuccess.DEFAULT_ERROR_MESSAGE
            }

            override fun getErrorCode(): Int {
                return ISuccess.DEFAULT_ERROR_CODE
            }

            override fun isResponseSuccess(): Boolean {
                return true
            }
        }

        val afterCheckSuccess = originalSuccess.checkISuccess()
        assertThat(afterCheckSuccess).isEqualTo(originalSuccess)
    }


    @Test(expected = ServerException::class)
    fun `checkISuccess_失敗_throw ServerException`() {
        val originalSuccess = object : ISuccess {
            override fun getErrorMessage(): String {
                return ISuccess.DEFAULT_ERROR_MESSAGE
            }

            override fun getErrorCode(): Int {
                return ISuccess.DEFAULT_ERROR_CODE
            }

            override fun isResponseSuccess(): Boolean {
                return false
            }
        }

        try {
            val afterCheckSuccess = originalSuccess.checkISuccess()
            assertThat(afterCheckSuccess.getErrorMessage()).isEmpty()
            assertThat(afterCheckSuccess.getErrorCode()).isEqualTo(101)
        } catch (e: Exception) {
            throw e
        }
    }

    @Test
    fun `checkIsSuccessful_回應成功_回傳自己`() {
        val except = Response.success("")
        val actual = except.checkIsSuccessful()
        assertThat(actual).isEqualTo(except)
    }

    @Test(expected = HttpException::class)
    fun `checkIsSuccessful_回應失敗_throw HttpException`() {
        val except = Response.error<Any>(400, "".toResponseBody())
        except.checkIsSuccessful()
    }

    @Test
    fun `requireBody_回應成功_有Body`() {
        val BODY = "Body"
        val response = Response.success(BODY)
        val actual = response.requireBody()
        assertThat(actual).isEqualTo(BODY)
    }

    @Test(expected = EmptyBodyException::class)
    fun `requireBody_回應成功_沒有body throw EmptyBodyException`() {
        val response = Response.success<Void>(null)
        response.requireBody()
    }

    @Test
    fun `checkResponseBody_回應成功_有Body`() {
        val body = "Body"
        val response = Response.success(body)
        val actual = response.checkResponseBody(gson)
        assertThat(actual).isEqualTo(body)
    }

    @Test(expected = EmptyBodyException::class)
    fun `checkResponseBody_回應成功_沒有body throw EmptyBodyException`() {
        val response = Response.success<Void>(null)
        response.checkResponseBody(gson)
    }

    @Test(expected = HttpException::class)
    fun `checkResponseBody_回應失敗500_throw HttpException`() {
        val response = Response.error<Any>(500, "".toResponseBody())
        response.checkResponseBody(gson)
    }

    @Test(expected = HttpException::class)
    fun `checkResponseBody_回應失敗401_throw HttpException`() {
        val response = Response.error<Any>(401, "".toResponseBody())
        response.checkResponseBody(gson)
    }

    @Test(expected = ServerException::class)
    fun `checkResponseBody_回應失敗_throw ServerException`() {
        val errorBody = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val response = Response.error<Any>(400, errorBody.toResponseBody())
        response.checkResponseBody(gson)
    }

    @Test(expected = JsonSyntaxException::class)
    fun `checkResponseBody_回應失敗_throw JsonSyntaxException`() {
        val errorBody = """
            "123"
        """.trimIndent()
        val response = Response.error<Any>(400, errorBody.toResponseBody())
        response.checkResponseBody(gson)
    }

    @Test
    fun `handleNoContent_回應成功_Unit`() {
        val response = Response.success<Void>(204, null)
        val actual = response.handleNoContent(gson)
        assertThat(actual).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `handleNoContent_回應失敗400_throw ServerException`() {
        val errorBody = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val response = Response.error<Void>(400, errorBody.toResponseBody())
        response.handleNoContent(gson)
    }

    @Test(expected = HttpException::class)
    fun `handleNoContent_回應失敗401_throw HttpException`() {
        val errorBody = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val response = Response.error<Void>(401, errorBody.toResponseBody())
        response.handleNoContent(gson)
    }

    @Test
    fun `handleHttpStatusCode_當Http Status是200_有字串格式ResponseBody`() {
        val body = "Body"
        val response = Response.success(body.toResponseBody())
        val result =
            response.handleHttpStatusCode<Response<ResponseBody>, String>(gson) { _: Int, responseBody: ResponseBody? ->
                return@handleHttpStatusCode responseBody?.string()!!
            }
        assertThat(result).isEqualTo(body)
    }

    @Test
    fun `handleHttpStatusCode_當Http Status是200_沒有字串格式ResponseBody`() {
        val body = ""
        val response = Response.success(body.toResponseBody())
        val result =
            response.handleHttpStatusCode<Response<ResponseBody>, String>(gson) { _: Int, responseBody: ResponseBody? ->
                return@handleHttpStatusCode responseBody?.string()!!
            }
        assertThat(result).isEmpty()
    }

    @Test
    fun `handleHttpStatusCode_當Http Status是200_有正確的Json格式的ResponseBody`() {
        val body = """
            {
                "content": "yo"
            }
        """.trimIndent()
        val response = Response.success(body.toResponseBody())
        val result =
            response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody>(gson) { _: Int, responseBody: ResponseBody? ->
                return@handleHttpStatusCode gson.fromJson(
                    responseBody?.string(),
                    MockResponseBody::class.java
                )
            }
        assertThat(result.content).isEqualTo("yo")
    }

    @Test(expected = JsonSyntaxException::class)
    fun `handleHttpStatusCode_當Http Status是200_有錯誤的Json格式的ResponseBody`() {
        val body = """
            123
        """.trimIndent()
        val response = Response.success(body.toResponseBody())
        response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody>(gson) { _: Int, responseBody: ResponseBody? ->
                return@handleHttpStatusCode gson.fromJson(
                    responseBody?.string(),
                    MockResponseBody::class.java
                )
            }
    }

    @Test
    fun `handleHttpStatusCode_當Http Status是204_沒有ResponseBody`() {
        val response = Response.success<ResponseBody>(204, null)
        val result =
            response.handleHttpStatusCode<Response<ResponseBody>, Unit>(gson) { code: Int, responseBody: ResponseBody? ->
                Unit
            }
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `handleHttpStatusCode_當Http Status是204_有ResponseBody`() {
        val body = """
            {
                "content": "yo"
            }
        """.trimIndent()
        val response = Response.success<ResponseBody>(204, body.toResponseBody())
        val result =
            response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody>(gson) { _: Int, responseBody: ResponseBody? ->
                return@handleHttpStatusCode gson.fromJson(
                    responseBody?.string(),
                    MockResponseBody::class.java
                )
            }
        assertThat(result.content).isEqualTo("yo")
    }

    @Test(expected = ServerException::class)
    fun `handleHttpStatusCode_當Http Status是400_ServerException`() {
        val errorBody = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val response = Response.error<ResponseBody>(400, errorBody.toResponseBody())
        response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody>(gson) { code: Int, responseBody: ResponseBody? ->
            return@handleHttpStatusCode gson.fromJson(
                responseBody?.string(),
                MockResponseBody::class.java
            )
        }
    }

    @Test(expected = HttpException::class)
    fun `handleHttpStatusCode_當Http Status是401_HttpException`() {
        val errorBody = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val response = Response.error<ResponseBody>(401, errorBody.toResponseBody())
        response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody>(gson) { code: Int, responseBody: ResponseBody? ->
            return@handleHttpStatusCode gson.fromJson(
                responseBody?.string(),
                MockResponseBody::class.java
            )
        }
    }

    @Test(expected = HttpException::class)
    fun `handleHttpStatusCode_當Http Status是500_HttpException`() {
        val errorBody = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val response = Response.error<ResponseBody>(500, errorBody.toResponseBody())

        response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody>(gson) { code: Int, responseBody: ResponseBody? ->
            return@handleHttpStatusCode gson.fromJson(
                responseBody?.string(),
                MockResponseBody::class.java
            )
        }
    }
}