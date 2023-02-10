### 以`virtualTrading2ServiceModule`為例

## 建立API步驟

建立一個新的API，會有以下步驟：

1. 建立Retrofit介面
2. 建立xxxWeb介面
3. 實作xxxWebImpl
4. 撰寫xxxWeb的單元測試
5. 加入RecordApi的單元測試
6. 在App的應用層，驗證API

### 1.建立Retrofit介面

```
interface VirtualTrading2Service {
    /**
     * 創建帳戶
     */
    @RecordApi
    @POST
    suspend fun createAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CreateAccountRequestBody
    ): Response<CreateAccountResponseBody>
}
```

|欄位|說明|
|---|---|
| /** */ |註解，請加入相關說明|
| @RecordApi |紀錄API的參數，可看README的紀錄API|
| @POST |Retrofit的RESTFul的方法定義|
| @Url | 設定的API位址 |
| @Header("Authorization") | JWT Token所需要的參數 |
| @Body | 需要打Post的Body |
| Response<T> | 外面一層使用Response，讓呼叫的方法，自己判斷Http Stutus Code。 T是對應的服務回傳資料，不同服務會有不同行為|

### 2.建立xxxWeb介面

- 建立xxxWeb

```
interface VirtualTrading2Web {
 	...
}
```
- 加入 `RequestConfig`

```
interface VirtualTrading2Web {
 	val requestConfig: VirtualTradingRequestConfig
}
```

```
/**
 * 所有Request的設定
 */
interface VirtualTradingRequestConfig {
    /**
     * 取得網域名稱
     */
    fun getDomain(): String

    /**
     * 取得BearerToken
     */
    fun getBearerToken(): String
}
```

可以看到 `VirtualTradingRequestConfig ` ，不同的服務，內容定義不同。基本上至少會有兩個變數 `Domain` 和 `BearerToken` ，分別是設定服務的Domain和授權Token。

- 加入API方法

```
interface VirtualTrading2Web {
    /**
     * 建立帳號
     *
     * @param authorization 授權
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param accountInvestType 投資帳戶類型 (現股 : 1 / 期權 : 2)
     * @param cardSn 道具卡序號，沒有道具卡填0(免費創建)
     *
     */
    suspend fun createAccount(
        authorization: String = requestConfig.getBearerToken(),
        domain: String = requestConfig.getDomain(),
        url: String = "${domain}account-api/Account",
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody>
}
```

參數 `authorization`：預設會從 `requestConfig.getBearerToken()` 取得。

參數 `domain`：預設會從 `requestConfig.getDomain()` 取得。

參數url：預設會根據`domain`和Path進行字串的組合，`"${domain}account-api/Account"`。

其他參數：API所需內容，不同API會有不同數量。

- 完整的物件

```
interface VirtualTrading2Web {

    /**
     * 虛擬交易請求轉接器
     */
    val requestAdapter: VirtualTradingRequestAdapter

    /**
     * 建立帳號
     *
     * @param authorization 授權
     * @param domain 網域名稱
     * @param url 完整的Url
     * @param accountInvestType 投資帳戶類型 (現股 : 1 / 期權 : 2)
     * @param cardSn 道具卡序號，沒有道具卡填0(免費創建)
     *
     */
    suspend fun createAccount(
        authorization: String = requestAdapter.getBearerToken(),
        domain: String = requestAdapter.getDomain(),
        url: String = "${domain}account-api/Account",
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody>
}
```


### 3.實作xxxWebImpl

- 實作VirtualTrading的RequestAdapter

```
class VirtualTradingRequestAdapterImpl(
    private val setting: Setting
) : VirtualTradingRequestAdapter {

    override fun getDomain(): String {
        return "https://virtualtrade-testing.cmoney.tw/"
    }

    override fun getBearerToken(): String {
        return setting.accessToken.createAuthorizationBearer()
    }
}
```

- 實作Web

```
class VirtualTrading2WebImpl(
    override val requestAdapter: VirtualTradingRequestAdapter,
    private val service: VirtualTrading2Service,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : VirtualTrading2Web {

    override suspend fun createAccount(
        authorization: String,
        domain: String,
        url: String,
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestBody = CreateAccountRequestBody(
                accountInvestType = accountInvestType,
                cardSn = cardSn
            )
            service.createAccount(
                url = url,
                authorization = authorization,
                body = requestBody
            ).checkResponseBody(gson)
        }
    }
}

```


#### 判斷HttpStatus和方法選擇

CMoney的錯誤格式有很多種，大部分會以下面範例code的格式出現，對應的物件為CMoneyError，根據不同的status code會有不同的解析策略。而解析Response的方法都在ResponseExtension.kt底下。

```
{	
	"message":"錯誤訊息",
	"Error":{
		"Code":101,
		"Message":"Auth Failed"
	}
}

{	
	"message":"錯誤訊息",
	"error":{
		"code":101,
		"message":"Auth Failed"
	}
}
```

下面會以不同的情況說明用哪一種方法

#### Status Code 200內有CMoneyError

這時候代表200的code同時表示成功和失敗，但為了區分成功和失敗，需實作以下流程。

- 第一步：檢查Http Status是否在200，如果是回傳[ResponseBody]，否則拋出[HttpException]。

對應方法：checkIsSuccessful()

-  第二步：根據後台規則200一定有ResponseBody，所以再繼續檢查[Response]一定要有[ResponseBody]，否則拋出[EmptyBodyException]。

對應方法：requireBody()

-  第三步：如果ResponseBody有CMoneyError則拋出[ServerException]。前提是ResponseBody有繼承CMoneyError

對應方法：checkIWithError()

-  第四步（可選）：因為不加以過濾錯誤訊息就回傳給使用者，會造成成功時還會有一些不相關的Error欄位，所以ResponseBody可以實作IWithError介面做一層中間層，將資料轉成沒有Error欄位的物件。

實作介面：IWithError

```
status code = 200
{
	//成功才會有	
	"commkey":"2330"
	//錯誤才會有
	"Error":{
		"Code":101,
		"Message":"Auth Failed"
	},
	"error":{
		"Code":101,
		"Message":"Auth Failed"
	}
}
```

#### Status Code 200內有ResponseCode和IsSuccess

這時候代表200的code同時表示成功和失敗，但為了區分成功和失敗，需實作以下流程。但有時IsSuccess不一定有意義，所有要確認是以ResponseCode還是IsSuccess為準。

- 第一步：檢查Http Status是否在200，如果是回傳[ResponseBody]，否則拋出[HttpException]。

對應方法：checkIsSuccessful()

-  第二步：根據後台規則200一定有ResponseBody，所以再繼續檢查[Response]一定要有[ResponseBody]，否則拋出[EmptyBodyException]。

對應方法：requireBody()

-  第三步：ResponseBody需要實作[ISuccess]介面，來決定本次成功還是失敗，如果成功會回傳ResponseBody本身，否則拋出[ServerException]。

實作介面：ISuccess  
對應方法：checkISuccess

```
{
	//不管成功還是失敗都會有
	"IsSuccess": false,//不一定有意義
	"ResponseCode": 2,
	"ResponseMsg": "密碼錯誤"
}
```

#### Status Code 2xx代表成功，4xx代表失敗。

預設status為400時會給CMoneyError

##### 第一種：處理http status code 200-299的狀態，並且`一定`有ResponseBody。

處理http status code，200-299的狀態，並且一定有ResponseBody，如果沒有拋出[EmptyBodyException]。  
400的狀態，解析[CMoneyError]，並拋出[ServerException]。  
其他拋出[HttpException]。

- 第一步：檢查Http Status是否在200，如果是回傳[ResponseBody]，否則拋出[HttpException]。

對應方法：checkResponseBody

```
{	
	//成功才有
	"commkey":"2330"
	//失敗才有
	"Error":{
		"Code":101,
		"Message":"Auth Failed"
	},
	"error":{
		"Code":101,
		"Message":"Auth Failed"
	}
}
```

##### 帶二種：Http status code 204，不會有ResponseBody。 

code是204的狀態的狀態，並且沒有[ResponseBody]。  
400的狀態，解析[CMoneyError]，並拋出[ServerException]。  
其他拋出[HttpException]。  

檢查方法：handleNoContent

```
沒有任何東西
```

並推薦以下寫法

* 在Retrofit回傳介面使用Response<Void>

```kotlin
@POST(URL)
suspend fun action(
	// ...
): Response<Void>
```

* 在WebImpl中使用handleNoContent(Gson)解析ResponseBody，回傳介面使用Result<Unit>

```kotlin
override suspend fun action(): Result<Unit> = withContext(dispatcher.io()) {
    runCatching {
        val response = // ...
        return@runCatching response.handleNoContent(gson)
    }
}
```

##### 第三種：當上面兩種都不符合時

可能有時候有200和204或其他情況，需自行處理成功時的狀態，400時處理[CMoneyError]，其他[HttpException]。

對應方法：handleHttpStatusCode

* 在Retrofit回傳介面使用Response<ResponseBody>

```kotlin
@POST(URL)
suspend fun action(
	// ...
): Response<ResponseBody>
```

* 在WebImpl中使用handleHttpStatusCode<Response<ResponseBody>。

```kotlin
 val response = Response.success(body.toResponseBody())
 response.handleHttpStatusCode<Response<ResponseBody>, MockResponseBody?>(gson) { code: Int, responseBody: ResponseBody? ->
	return@handleHttpStatusCode when(code) {
		200 -> {
			gson.fromJson(responseBody?.string(), MockResponseBody::class.java )
		}
		204 -> {
			null
		}
		else -> {
			null
		}
	}
}
```

NO，以上都不符合，恭喜你，請自己寫判斷邏輯，幫你QQ，但現在新的API都應該是`Status Code 2xx代表成功，4xx代表失敗`，如果不是請跟核心組或是後台反應。




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

### 4.撰寫xxxWeb的單元測試

這邊請看範例的test資料夾

### 5.加入RecordApi的單元測試

新創建的Retrofit Service要在[RecordApiTest](./backend2/src/test/java/com/cmoney/backend2/base/model/calladapter/RecordApiTest.kt)中加入集合中成為測試案例

### 6.在App的應用層，驗證API

位置：app/src/main/java/com/cmoney/backend2/sample/servicecase/

建立一個物件，規則為：xxxServiceCase

最後就可以開始測試方法