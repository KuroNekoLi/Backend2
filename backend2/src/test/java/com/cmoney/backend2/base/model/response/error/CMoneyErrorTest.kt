package com.cmoney.backend2.base.model.response.error

import com.cmoney.backend2.base.model.request.Constant
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import org.junit.Test

class CMoneyErrorTest {

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Test
    fun `解析第一層的ErrorMessage`() {
        val errorJson = """
            {
                "message": "錯誤訊息"
            }
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        Truth.assertThat(cmoneyError.message).isEqualTo("錯誤訊息")
    }

    @Test
    fun `getErrorDetail_解析大寫錯誤_有error code和message`() {
        val errorJson = """
            {
                "Error":{
                    "Code":101,
                    "Message":"Auth Failed"
                }
            }
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        Truth.assertThat(cmoneyError.detail?.code).isEqualTo(101)
        Truth.assertThat(cmoneyError.detail?.message).isEqualTo("Auth Failed")
    }

    @Test
    fun `getErrorDetail_解析小寫錯誤_有error code和message`() {
        val errorJson = """
            {
                "error":{
                    "code":101,
                    "message":"Auth Failed"
                }
            }
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        Truth.assertThat(cmoneyError.detail?.code).isEqualTo(101)
        Truth.assertThat(cmoneyError.detail?.message).isEqualTo("Auth Failed")
    }

    @Test
    fun `getErrorDetail_解析大寫錯誤_code和message是null`() {
        val errorJson = """
             {"Error":{}}
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        Truth.assertThat(cmoneyError.detail?.code).isNull()
        Truth.assertThat(cmoneyError.detail?.message).isNull()
    }

    @Test
    fun `getErrorDetail_解析小寫錯誤_code和message是null`() {
        val errorJson = """
            {"error":{}}
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        Truth.assertThat(cmoneyError.detail?.code).isNull()
        Truth.assertThat(cmoneyError.detail?.message).isNull()
    }

    @Test
    fun `getErrorDetail_解析沒有錯誤_code和message是null`() {
        val errorJson = """
            {}
        """.trimIndent()
        val cmoneyError = gson.fromJson<CMoneyError>(errorJson, CMoneyError::class.java)
        Truth.assertThat(cmoneyError.detail?.code).isNull()
        Truth.assertThat(cmoneyError.detail?.message).isNull()
    }

    @Test
    fun `getErrorDetail_產生預設CMoneyError_code和message是null`() {
        val cmoneyError = CMoneyError()
        Truth.assertThat(cmoneyError.detail?.code).isNull()
        Truth.assertThat(cmoneyError.detail?.message).isNull()
    }

    @Test
    fun `getErrorDetail_產生預設CMoneyError，並設定Detail_code預設值1和message不是空的`() {
        val expectCode = 1
        val cmoneyError = CMoneyError(
            detail = CMoneyError.Detail(
                code = expectCode,
                message = "YO"
            )
        )
        Truth.assertThat(cmoneyError.detail?.code).isEqualTo(expectCode)
        Truth.assertThat(cmoneyError.detail?.message).isNotEmpty()
    }
}