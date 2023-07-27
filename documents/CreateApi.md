## 建立API步驟

建立一個新的API，會有以下步驟：

1. 建立Retrofit介面
2. 建立xxxSettingAdapter
3. 建立xxxWeb介面
4. 實作xxxWebImpl
5. 撰寫xxxWeb的單元測試
6. 加入RecordApi的單元測試
7. 在App的應用層，驗證API

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

| 欄位                       | 說明                                                                 |
|--------------------------|--------------------------------------------------------------------|
| /** */                   | 註解，請加入相關說明                                                         |
| @RecordApi               | 紀錄API的參數，可看README的紀錄API                                            |
| @POST                    | Retrofit的RESTFul的方法定義                                              |
| @Url                     | 設定的API位址                                                           |
| @Header("Authorization") | JWT Token所需要的參數                                                    |
| @Body                    | 需要打Post的Body                                                       |
| Response<T>              | 外面一層使用Response，讓呼叫的方法，自己判斷Http Stutus Code。 T是對應的服務回傳資料，不同服務會有不同行為 |

#### 在需要驗證的Retrofit的API中，加入Header的Authorization欄位

```kotlin
@RecordApi
@GET
suspend fun isTokenLatest(
    ...
    @Header("Authorization")
    ...
): Response<IsLatestResponseBodyWithError>
```

#### 若是API PATH相同，服務是以參數進行區分(例如: action)，請全小寫

```kotlin
@FormUrlEncoded
@POST
suspend fun getAuthStatus(
	@Url url: String,
	@Header("Authorization") authorization: String,
	@Field("Action") action: String = "getauth",
	@Field("Guid") guid: String,
	@Field("AppId") appId: Int
): Response<GetAuthResponseBody>
```

#### 紀錄API

目前所有API都要記錄  
需要在服務介面的方法上加上註譯`@RecordApi`，代表要記錄這個API的行為。    
`@RecordApi`有一個可選的參數`cmoneyAction`，預設空字串，代表不會紀錄此API的發送請求中的action。

```kotlin
@RecordApi
@GET(value = "identity/session/isLatest")
suspend fun isTokenLatest(
    @Header("Authorization")
    accessToken: String
): Response<IsLatestResponseBodyWithError>
```

目前MobileService部分API以`Action`作為服務的分類故需要加上`cmoneyAction`參數以利後續資料分析，請全小寫。

```kotlin
/**
 * 服務7. 取得帳號資訊
 *
 * @param guid 該會員的Guid
 * @param appId App編號
 *
 */
@RecordApi(cmoneyAction = "getaccountinfo")
@FormUrlEncoded
@POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
suspend fun getAccountInfo(
    @Header("Authorization") authorization: String,
    @Field("Action") action: String = "getaccountinfo",
    @Field("Guid") guid: String,
    @Field("AppId") appId: Int
): Response<AccountInfoWithError>
```

#### Response選擇

CMoney的錯誤格式有很多種，大部分會以下的格式出現，對應的物件為CMoneyError，不管 Stutus Code 是400還200。

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

##### 情境一、Status Code 200內有CMoneyError

200的code同時表示成功和失敗，為了區分成功和失敗，全部欄位都要接收，然後到 `WebImpl` 時，再進行區分。

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

**[可選的步驟]** 因為不加以過濾錯誤訊息就回傳給使用者，會造成成功時還會有一些不相關的Error欄位，實作 `IWithError<T>` 介面做一層中間層，將資料轉成沒有Error欄位的物件。


```
class XXXResponseBodyWithError(
    @SerializedName("your_field")
    val yourField: String?
) : CMoneyError(), IWithError<XXX> {
    override fun toRealResponse(): XXX {
        return XXX(publicKeyCryptography)
    }
}

```

##### 情境二、Status Code 200內有ResponseCode和IsSuccess

200的code同時表示成功和失敗， `IsSuccess ` 可能是成功和 `ResponseCode ` 也可以代表成功，所有欄位全部接收，然後到 `WebImpl` 時，再進行區分。

```
status code = 200
{
    //不管成功還是失敗都會有
    "IsSuccess": false,//不一定有意義
    "ResponseCode": 2,
    "ResponseMsg": "密碼錯誤"
}
```

**[可選的步驟]** 實作 `ISuccess<T>` 介面做一層中間層，提供真正 `isResponseSuccess `。

```
data class EmailRegister(
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
): ISuccess {

    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 1
    }
}
```

##### 情境三、Status Code 2xx代表成功，4xx代表失敗。

status 200時，欄位都是需要的，所以要全部接收。400不用寫錯誤發生時的欄位，因為它被 Retrofit 的 Response 特別處理，需要在 `WebImpl` 處理，可看下一步驟教學。


### 2.建立xxxSettingAdapter

這邊主要建立單一Web可抽換的欄位，`getDomain ` 是Web中，所有API所需要一次性特換的欄位，如果是個別API的設定，可考慮加在方法中或在此介面加入。

``` 
/**
 * 虛擬下單相關的設定轉接。
 */
interface VirtualTrading2SettingAdapter {

    /**
     * 取得網域名稱
     */
    fun getDomain(): String
}
```

實作 `SettingAdapter`。

- domain：網域名稱，結尾必須是 `/`。
- **path(可選)：路徑名稱，需要更換的服務並不多，結尾必需是 `/`。可查看附加資訊的寫法**

```
class VirtualTrading2SettingAdapterImpl : VirtualTrading2SettingAdapter {

    override fun getDomain(): String {
        return "https://virtualtrade.cmoney.tw/"
    }
}
```

將建立完的 SettingAdapter，加入到 `GlobalBackend2Manager ` 的Builder，方便外面的替換實作。

```
class GlobalBackend2Manager(
    private val context: Context,
    private val virtualTrading2SettingAdapter: VirtualTrading2SettingAdapter
) : KoinComponent {
    constructor(builder: Builder) : this(
        context = builder.context,
        virtualTrading2SettingAdapter = builder.virtualTrading2SettingAdapter
    )
    
    /**
     * 取得虛擬下單V2設定轉接器
     */
    fun getVirtualTrading2SettingAdapter(): VirtualTrading2SettingAdapter {
        return virtualTrading2SettingAdapter
    }
    
    class Builder(
        val context: Context
    ) {
        constructor(builder: Builder) : this(
            context = builder.context
        )

        var virtualTrading2SettingAdapter: VirtualTrading2SettingAdapter =
            VirtualTrading2SettingAdapterImpl()

        fun build(): GlobalBackend2Manager = GlobalBackend2Manager(this)

        companion object {
            inline fun build(
                context: Context,
                block: Builder.() -> Unit
            ) = Builder(
                context = context
            ).apply(block)
                .build()
        }
    }
    
}
```

### 3.建立xxxWeb介面

- 建立xxxWeb

```
interface VirtualTrading2Web {
 	...
}
```
- 加入 `GlobalBackend2Manager`

```
interface VirtualTrading2Web {
 	 val globalBackend2Manager: GlobalBackend2Manager
}
```

- 加入API方法

```
interface VirtualTrading2Web {
    /**
     * 建立帳號
     *
     * @param domain 網域名稱
     * @param url 完整的Url，預設使用[domain]當作網域名稱
     * @param accountInvestType 投資帳戶類型 (現股 : 1 / 期權 : 2)
     * @param cardSn 道具卡序號，沒有道具卡填0(免費創建)
     *
     */
    suspend fun createAccount(
        domain: String = globalBackend2Manager.getVirtualTrading2SettingAdapter().getDomain(),
        url: String = "${domain}account-api/Account",
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody>
}
```

參數 `domain`：預設會從 `globalBackend2Manager.getVirtualTrading2SettingAdapter()` 取得。

參數url：預設會根據 `domain` 和 Path 進行字串的組合，`"${domain}account-api/Account"`。

其他參數：API所需內容，不同API會有不同數量。

- 完整的物件

```
interface VirtualTrading2Web {

    /**
     * 虛擬交易請求轉接器
     */
    val globalBackend2Manager: GlobalBackend2Manager

    /**
     * 建立帳號
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @param accountInvestType 投資帳戶類型 (現股 : 1 / 期權 : 2)
     * @param cardSn 道具卡序號，沒有道具卡填0(免費創建)
     *
     */
    suspend fun createAccount(
        domain: String = globalBackend2Manager.getVirtualTrading2SettingAdapter().getDomain(),
        url: String = "${domain}account-api/Account",
        accountInvestType: Int,
        cardSn: Long
    ): Result<CreateAccountResponseBody>
}
```


### 4.實作xxxWebImpl

- 實作Web

```
class VirtualTrading2WebImpl(
    override val globalBackend2Manager: GlobalBackend2Manager,
    private val service: VirtualTrading2Service,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : VirtualTrading2Web {

    override suspend fun createAccount(
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
                authorization = globalBackend2Manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }
}

```

#### 判斷Http的狀態和解析方法選擇

第一步驟的物件會在這邊進行分類，根據不同的status code會有不同的解析策略。而解析Response的方法都在ResponseExtension.kt底下。

#### 情境一、Status Code 200內有CMoneyError

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

#### 2. Status Code 200內有ResponseCode和IsSuccess

這時候代表200的code同時表示成功和失敗，但為了區分成功和失敗，需實作以下流程。但有時IsSuccess不一定有意義，所有要確認是以ResponseCode還是IsSuccess為準。

- 第一步：檢查Http Status是否在200，如果是回傳[ResponseBody]，否則拋出[HttpException]。

對應方法：checkIsSuccessful()

-  第二步：根據後台規則200一定有ResponseBody，所以再繼續檢查[Response]一定要有[ResponseBody]，否則拋出[EmptyBodyException]。

對應方法：requireBody()

-  第三步：ResponseBody需要實作[ISuccess]介面，來決定本次成功還是失敗，如果成功會回傳ResponseBody本身，否則拋出[ServerException]。

對應方法：checkISuccess  
實作介面：ISuccess

```
{
	//不管成功還是失敗都會有
	"IsSuccess": false,//不一定有意義
	"ResponseCode": 2,
	"ResponseMsg": "密碼錯誤"
}
```

#### 3. Status Code 2xx代表成功，4xx代表失敗。

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

### 5.撰寫xxxWeb的單元測試

這邊請看範例的test資料夾

### 6.加入RecordApi的單元測試

新創建的Retrofit Service要在[RecordApiTest](./backend2/src/test/java/com/cmoney/backend2/base/model/calladapter/RecordApiTest.kt)中加入集合中成為測試案例

### 7.在App的應用層，驗證API

位置：app/src/main/java/com/cmoney/backend2/sample/servicecase/

建立一個物件，規則為：xxxServiceCase

最後就可以開始測試方法