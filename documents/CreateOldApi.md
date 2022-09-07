### 註釋

- Error 結尾為Error的物件（i.e. GetAccountError)

### 我們使用下列framework

0. Retrofit
0. Koin
0. OkHttp

### 實做步驟

0. 增加retrofit界面 (-Service)
    1. 實做-WithError物件(錯誤的行為有很多種，請參考README.md）
    2. 實做-Web回傳物件(錯誤的行為有很多種，請參考README.md
1. 增加-Web界面
2. 增加-Web實做
2. 撰寫-Web單元測試
3. 在測試用app增加該api的呼叫（在-ServiceCase)

### 範例（以ProfileService增加一個getAccount為例)

#### 增加retrofit界面 (-Service)

```kotlin
interface ProfileService { // 既有Service物件
    @GET("profile/account") // 方法與路徑
    suspend fun getAccount(
        @Header("Authorization") authorization: String // 如果有驗證此欄位必備
    ): Response<GetAccountResponseWithError> // 定義回傳物件
}
```

### 回傳物件

#### 實做-WithError物件

- 繼承CMoneyError （如果api有回傳錯誤物件的話）
- IWithError<GetAccountResponse> 以供在-web實做轉換成無錯誤的物件，注意該物件型別
- Q&A:對，我們會有兩個一模一樣的物件除了有繼承CMoneyError


```kotlin
data class GetAccountResponseWithError(
    val cellphone: String?,
    val contactEmail: String?
) : CMoneyError(), IWithError<GetAccountResponse> {
    override fun toRealResponse(): GetAccountResponse {
        return GetAccountResponse(
            cellphone, contactEmail, email, facebook, signupDate
        )
    }
}
```

#### 實做-Web實際回傳物件

```kotlin
data class GetAccountResponse(
    val cellphone: String?,
    val contactEmail: String?
)
```

#### 增加-Web界面

```kotlin
interface ProfileWeb { // 既有Web物件
    suspend fun getAccount(): Result<GetAccountResponse> // 實際回傳物件
}
```

#### 增加-Web實做

```kotlin
class ProfileWebImpl(
    // 略
) : ProfileWeb {
    override suspend fun getAccount(): Result<GetAccountResponse> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                service.getAccount(
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }
}
```

#### 增加單元測試 (src/test/java/com/cmoney/backend2/profile/service/ProfileWebImplTest.kt)

最少達成兩個項目，以測試web是否有呼叫對應的錯誤/回傳

- 一般成功
- 失敗

```kotlin
    private val testScope = TestScope()

    // 成功
    @Test
    fun getAccountTestSuccess() = testScope.runTest {
        // Mock service,我們在單元測試只測試我們的程式碼。
        coEvery {
            service.getAccount(
                authorization = any() 
            )
        } returns Response.success(
            // language=JSON
            gson.fromJson(
                """{
                  "email": "example@cmoney.com.tw",
                  "contactEmail": "example@cmoney.com.tw",
                  "cellphone": "+886912345678",
                  "facebook": "example@cmoney.com.tw",
                  "signupDate": "2019/10/14 上午 09:18:23"
                }
                """, 
                GetAccountResponseWithError::class.java
            )
        )


        val result = webImpl.getAccount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    // 錯誤
    @Test
    fun getAccountTestError() = testScope.runTest {
        // Mock server,回傳200帶error物件
        coEvery {
            service.getAccount(
                authorization = any()
            )
        } returns Response.success(
            // language=JSON
            gson.fromJson(
                """
                  {
                  "Error": {
                    "Code": 100,
                    "Message": "參數錯誤"
                  }
                }
                """, 
                GetAccountResponseWithError::class.java
            )
        )
        val result = webImpl.getAccount()
        Truth.assertThat(result.isSuccess).isFalse()
    }
```

### 在測試app增加呼叫測試（app/src/main/java/com/cmoney/backend2/sample/servicecase/ProfileServiceCase.kt)

在-ServiceCase的物件內的testAll方法增加呼叫該api。

```kotlin
override suspend fun testAll() {
        profileWeb.getAccount().logResponse(TAG)
}
```