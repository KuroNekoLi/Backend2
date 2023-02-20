## Backend2

## 建立資料夾說明

[參考](./documents/CreateFolder.md)

## 建立API說明

[參考](./documents/CreateApi.md)


## MIGRATE

- [3.x.x -> 4.0.0](documents/migrate/3.x.x_4.0.0.md)

## 引用

- build.gradle

```groovy
allprojects {
    repositories {
		google()
		mavenCentral()
		maven { 
          url "http://192.168.99.70:8081/repository/maven-public/"
          setIsAllowInsecureProtocol(true)
        }
    }
}
```

- app/build.gradle

```groovy
android {
	compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = '11'
    }
}
dependecies {
	implementation("com.cmoney.backend2:backend2:5.48.1")
	implementation("com.cmoney.logdatarecorder:logdatarecorder-data:5.4.0")
	implementation("com.cmoney.logdatarecorder:logdatarecorder-domain:5.4.0")
}
```

- 指定debug/release

```groovy
dependecies {
	releaseImplementation("com.cmoney.backend2:backend2:5.48.1")
	debugImplementation("com.cmoney.backend2:backend2-debug:5.48.1")
	implementation("com.cmoney.logdatarecorder:logdatarecorder-data:5.4.0")
	implementation("com.cmoney.logdatarecorder:logdatarecorder-domain:5.4.0")
}
```

- app/src/main/AndroidManifest.xml，新增使用網路、取得網路狀態權限

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.cmoney.backend2.sample">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application>
        // ...
    </application>

</manifest>
```

## 初始化LogDataRecorder

為了紀錄Api行為，需要在Application加入初始化設定。

```kotlin
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LogDataRecorder.initialization(this) {
            isEnable = 是否開啟紀錄
            appId = 你的AppId
            platform = com.cmoney.domain_logdatarecorder.data.information.Platform.Android
        }
    }
}
```


## 使用 [Koin](https://insert-koin.io/)

以下這些StringQualifier已被使用

```kotlin
val BACKEND2_GSON = named("backend2_gson")
val BACKEND2_GSON_NON_SERIALIZE_NULLS = named("backend2_gson_non_serialize_nulls")
val BACKEND2_RETROFIT = named("backend2_retrofit")
val BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS = named("backend2_retrofit_with_gson_non_serialize_nulls")
val BACKEND2_SETTING = named("backend2_setting")
```

#### 已被定義好的Class

- Gson
	- `BACKEND2_GSON`：預設的Gson
	- `BACKEND2_GSON_NON_SERIALIZE_NULLS`：不會將Josn的Null傳出去。
- Retrofit
	- `BACKEND2_RETROFIT`：預設的Retrofit
	- `BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS`：Retrofit的Gson設定(不會將Josn的Null傳出去)。
- Setting
	- `BACKEND2_SETTING`：設定檔

#### 取得定義好的Module

定義名稱：backendBaseModule

```kotlin
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if(DEBUG){
            	androidLogger()
            }
            androidContext(this@SampleApplication)
            loadKoinModules(
                listOf(
				    // 在此宣告需要的定義 
                    backendServicesModule
                )
            )
        }
    }
}
```

#### 目前定義好的Module

backendServicesModule都包含以下定義，如果需要單一覆寫，可從下方找到。

| 定義名稱                                      | 對應PackageName              |
|-------------------------------------------|----------------------------|
| activityServiceModule                     | activity                   |
| additionalInformationRevisitServiceModule | additionInformationReviist |
| authorizationServiceModule                | authorization              |
| backendBaseModule                         | base                       |
| billingServiceModule                      | billing                    |
| cellphoneServiceModule                    | cellphone                  |
| chatServiceModule                         | chat                       |
| chipkServiceModule                        | chipk                      |
| cmtalkServiceModule                       | cmtalk                     |
| commonServiceModule                       | common                     |
| commonUseModule                           | commonuse                  |
| customGroupServiceModule                  | customgroup                |
| customGroup2ServiceModule                 | customgroup2               |
| dataServiceModule                         | data                       |
| dtnoServiceModule                         | dtno                       |
| emilyServiceModule                        | emilystock                 |
| forumOceanServiceModule                   | forumocean                 |
| identityProviderServiceModule             | identityprovider           |
| imageServiceModule                        | image                      |
| mediaServiceModule                        | media                      |
| mobileOceanServiceModule                  | mobileocean                |
| noteExtensionServiceModule                | note_extension             |
| notesServiceModule                        | notes                      |
| notificationServiceModule                 | notification               |
| notification2ServiceModule                | notification2              |
| oceanServiceModule                        | ocean                      |
| portalServiceModule                       | portal                     |
| profileServiceModule                      | profile                    |
| realtimeAfterMarketServiceModule          | realtimeaftermarket        |
| tickDataServiceModule                     | tickdata                   |
| trialServiceModule                        | trial                      |
| virtualAssetsServiceModule                | virtualassets              |
| crmServiceModule                          | crm                        |
| userBehaviorServiceModule                 | userbehavior               |
| clientConfigurationModule                 | clientconfiguration        |
| videoChannelServiceModule                 | videochannel               |
| imageRecognitionServiceModule             | imageRecognition           |
| brokerDataTransmissionServiceModule       | brokerdatatransmission     |
| frontEndLoggerServiceModule               | frontendlogger             |
| virtualTradeServiceModule                 | vtwebapi                   |
| crawlSettingServiceModule                 | crawlsetting               |
| productProvider                           | productdataprovider        |
| virtualTrading2ServiceModule              | virtualtrading2            |

#### 選擇使用Module的步驟

- 確認目前需要使用的服務的host之後的path，以下舉例說明

```text
AuthorizationServer/Authorization/ExpiredTime/{type}/{subjectId}
```

- 於模組專案中使用`Find in Path`進行搜尋
- 搜尋到後，目前有兩個方式可以找到其koin定義Module
    
    1. 找到該`XXXService.kt`在哪個backend2/{packageName}，再來上方表格搜尋
    
    ```text
    +--- targetPackage
    |    +--- service
    |         +--- XXXService.kt
    ```
    
    2. 在該`XXXService.kt`的package(常為service)，同層找到`di`package，其中檔案(ServiceModule.kt)中就有定義
    
    ```text
    +--- package
    |    +--- di
    |    |    +--- ServiceModule.kt
    |    +--- service
    |         +--- XXXService.kt
    ```

#### 更改設定的值

通常在Application要把基本預設值設定完成

Setting.domainUrl基本上不會在Application使用，會在Activity，因為需透過RemoteConfig設定。

```kotlin
get<Setting>(BACKEND2_SETTING).apply {
	appId = 2
	clientId = "cmchipkmobile"
	appVersion = "1.0.0"
	appVersionCode = 1
	platform = Platform.Android
}
```

### 實作Setting介面

Base模組預設實作的Setting是沒有加密的SharedPreference，如果需要加密需自己Override(目前登入模組已實作)，Koin的DI設定。

```kotlin
val overrideSettingModule = module {
    single<Setting>(
        qualifier = BACKEND2_SETTING,
        override = true
    ) {
    	YOUR_SETTING
    }
}
```

#### 沒有加密的Setting

使用SharedPreference為存儲體，避免記憶體不夠時回收。如果要使用記憶體，必須考慮到欄位是一個常數才可以用，不然回收後會使用預設值，不會使用更改後的值。

```kotlin
class InsecuritySetting(InsecuritySharedPreference: SharedPreferences): Setting {
	// 使用SharedPreference
	override var domainUrl: String = InsecuritySharedPreference.domainUrl
		set(value) {
		    InsecuritySharedPreference.domainUrl = value
		    field = value
		}
	// 使用記憶體
	override var appId: Int = 2
	// ...
}
```


### JWT登入相關

[JWT相關功能和API](http://192.168.10.147:10080/CG_Mobile/CG_Module_Android/Backend2/IdentityProvider)登入模組會實作，正常來說在專案中不需要實作太多。如果單純要使用使用API，不想使用登入模組，可以看其他的Backend範例。

如果要自己實作網路相關的DI需要注意下列項目：

#### 在Header加入Log和替換Url

* 規定格式對應的物件為[ApiLog]

```
cmoneyapi-trace-context:{
	"appId": AppId,
	"platform":平台,
	"appVersion":"App版本",
	"osVersion":"系統版本",
	"manufacturer":"廠牌",(可選)
	"model":"手機型號"(可選)
}
```

細節可參考[BaseModule]

* 只有特定的API才需要加入（之後會棄用）

登入或註冊方式, 1.Email 2.FB 3.手機 4.AppleId 5.Firebase匿名 6. Refresh Token

```
 x-cmapi-trace-context: {"AppId":2,Mode:xxx,"Platform":2}

```

* 因為其他Backend也統一使用`BACKEND2_SETTING`，所以相關設定引用也務必一樣，不然你需要重新實作所有Backend的DI，才能正確套用在你定義的設定。

```
val setting = getKoin().get<Setting>(BACKEND2_SETTING)
```

### 實作API注意事項

#### 在需要驗證的Retrofit的API中，加入Header的Authorization欄位

```kotlin
@GET(value = "identity/session/isLatest")
suspend fun isTokenLatest(
    @Header("Authorization")
    accessToken: String
): Response<IsLatestResponseBodyWithError>
```

#### 若是API PATH相同，服務是以參數進行區分(例如: action)，請全小寫

```kotlin
@FormUrlEncoded
@POST("MobileService/ashx/LoginCheck/LoginCheck.ashx")
suspend fun getAuthStatus(
    @Header("Authorization") authorization: String,
    @Field("Action") action: String = "getauth",
    @Field("Guid") guid: String,
    @Field("AppId") appId: Int
): Response<GetAuthResponseBody>
```

#### 在實作的WebImpl中，產生Authorization Bear格式，並傳給Retrofit Service。

* 傳入實作的設定物件

```kotlin
class IdentityProviderWebImpl(
    private val service: IdentityProviderService,
    private val gson: Gson,
    private val setting: Setting,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : IdentityProviderWeb {
	// ...
}
```

* 產生Authorization Bearer格式：`AccessToken.createAuthorizationBearer()`

```kotlin
override suspend fun isTokenLatest(): Result<Boolean> = withContext(dispatcherProvider.io()) {
	runCatching {
		val responseBody = service.isTokenLatest(
			accessToken = setting.accessToken.createAuthorizationBearer()
		)
		// ...         
	}
}

```

## 紀錄API

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

## Unsigned Data Type 支援

某些服務回傳可能會遇到 Unsigned 的型別，目前模組預設提供的`Gson`有包含`ULong`轉換的支援。如果需要客製化的 Gson，註冊`ULongTypeAdapter`即可支援`ULong`。

```kotlin
GsonBuilder().registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
```

[BaseModule]:http://192.168.10.147:10080/CG_Mobile/CG_Module_Android/Backend2/Base/blob/master/base/src/main/java/com/cmoney/backend2/base/di/BaseModule.kt

[ApiLog]:http://192.168.10.147:10080/CG_Mobile/CG_Module_Android/Backend2/Base/blob/master/base/src/main/java/com/cmoney/backend2/base/model/log/ApiLog.kt
