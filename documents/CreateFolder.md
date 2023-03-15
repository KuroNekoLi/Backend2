## 資料夾結構

此為規則，請新建立的人，依循資料結構。

### 結構

```
+--- com.cmoney.backend2.xxx
|    +--- di
|        +--- ServiceModule.kt
|    +--- model
|        +--- settingadapter
|            +--- xxxSettingAdapter.kt
|    +--- service
|        +--- api
|        +--- xxxService.kt
|        +--- xxxWeb.kt
```

- di：放置依賴注入相關定義
- model.settingadapter：放置API設定相關定義
- service：放置Retrofit的Service和轉接成Web的相關定義

### 在模組專案加入混淆設定

將混淆設定加入在**consumer-rules.pro**裡面，主要是有用到Gson的`SerializedName`等註解跟反射，通常目錄為`service.api`。

```
-keep class com.cmoney.backend2.$SOME_PACKAGE.service.api.** { *; }
```