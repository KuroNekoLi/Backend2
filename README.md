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
	implementation("com.cmoney.backend2:backend2:5.57.0")
	implementation("com.cmoney.logdatarecorder:logdatarecorder-data:5.5.0")
	implementation("com.cmoney.logdatarecorder:logdatarecorder-domain:5.5.0")
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

V1版本的定義將逐步被換成V2。

### V2是根據V1的問題進行改良

- 透過攔截器統一覆寫最後的Domain？

V2已經轉由個別的Web轉接器，各自實作。

- 更改Domain時，需要建立自己的Retrofit和OkHttp定義。

職責不需要Retrofit和OkHttp，由Web轉接器和GlobalBackend2Manager處理。

- Debug版本是否移除？

TODO

癥結點：一旦移除，原始碼需要被包進去aar？

API打出去的Log攔截器還需要由模組控制？因為各個專案會自己覆寫OkHttp的定義，來配合熟悉的Debug工具。


### V2版本

預先定義的`Qualifier`

```
val BACKEND2_GSON = named("backend2_gson")
val BACKEND2_GSON_NON_SERIALIZE_NULLS = named("backend2_gson_non_serialize_nulls")
val BACKEND2_OKHTTP_V2 = named("backend2_okhttp_v2")
val BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2 = named("backend2_retrofit_with_gson_non_serialize_nulls_v2")
```

#### 物件說明

- Gson
	- `BACKEND2_GSON`：預設的Gson。
	- `BACKEND2_GSON_NON_SERIALIZE_NULLS`：不會將Json的Null傳出去。
- OkHttp
	- `BACKEND2_OKHTTP_V2`：預設的OkHttp，加入`addLogInterceptor`和`addCMoneyApiTraceContextInterceptor`。
- Retrofit
	- TODO 加入會將Json null 傳出去的Retrofit。
	- `BACKEND2_RETROFIT_WITH_GSON_NON_SERIALIZE_NULLS_V2`：使用`BACKEND2_OKHTTP_V2 `和`BACKEND2_GSON_NON_SERIALIZE_NULLS `的Retrofit。

#### 特別說明 GlobalBackend2Manager

`GlobalBackend2Manager`主要用來控制參數如下：全域的Doamin(預設的Domain)、AppId、Jwt的ClientId、AppVersionCode、AppVersionName、Platform、Manufacturer、Model、OsVersion、Jwt的AccessToken、Jwt的IdentityToken、Jwt的RefreshToken和**最重要的東西：組合各個Web服務的設定轉接器。設定轉接器就是控制Domain和Path或一些特別的參數設定。**

細節設定可看[建立API說明](./documents/CreateApi.md)

### V1版本

預先定義的`Qualifier`

```kotlin
val BACKEND2_OKHTTP = named("backend2_okhttp")
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
	- `BACKEND2_SETTING`：設定檔，已被棄用，使用 GlobalBackendManager代替。

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


## GlobalBackend2Manager

- **覆寫DI設定**，依照App的設定AppId、platform、Jwt ClientId參數。

```
single {
    GlobalBackend2Manager.Builder.build(
        backendSetting = get(),
        jwtSetting = get()
    ) {
        appId = TODO
        platform = TODO
        clientId = TODO
    }
}
```

- 在特定邏輯後更改設定。

會一直更改AppId或ClientId應該只有公版才會用到。

```
val manager = getKoin().get<GlobalBackend2Manager>()
manager.setGlobalDomainUrl()
manager.setAppId()
manager.setPlatform()
```

### Jwt登入相關

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

細節可參考`BaseModuleV2的addCMoneyApiTraceContextInterceptor`

* 只有特定的API才需要加入

登入或註冊方式, 1.Email 2.FB 3.手機 4.AppleId 5.Firebase匿名 6. Refresh Token

```
 x-cmapi-trace-context: {"AppId":2,Mode:xxx,"Platform":2}

```

## Unsigned Data Type 支援

某些服務回傳可能會遇到 Unsigned 的型別，目前模組預設提供的`Gson`有包含`ULong`轉換的支援。如果需要客製化的 Gson，註冊`ULongTypeAdapter`即可支援`ULong`。

```kotlin
GsonBuilder().registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
```

[BaseModule]:http://192.168.10.147:10080/CG_Mobile/CG_Module_Android/Backend2/Base/blob/master/base/src/main/java/com/cmoney/backend2/base/di/BaseModule.kt

[ApiLog]:http://192.168.10.147:10080/CG_Mobile/CG_Module_Android/Backend2/Base/blob/master/base/src/main/java/com/cmoney/backend2/base/model/log/ApiLog.kt
