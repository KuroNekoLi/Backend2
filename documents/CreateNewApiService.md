### 建立API的資料夾

根據服務給的名稱建立資料夾在`com.cmoney.backend2`底下。

### 加入API檔案

一般來說資料夾會有兩個di和service

di資料夾，可以參考其他backend-service的設定．

service資料夾會有以下幾個規則

* api-用來放置要打的api，裡面會以api名稱作為區分．
* XXXService-Retrofit的Service介面．
* XXXWeb-開放給外部使用者的介面．
* XXXWebImpl-為XXXWeb的實作類別．

### 在模組專案加入混淆設定

將混淆設定加入在**consumer-rules.pro**裡面，主要是有用到Gson的`SerializedName`等註意跟反射，通常目錄為`service.api`。

```
-keep class com.cmoney.backend2.activity.service.api.** { *; }
```

### 建立API的步驟

可以參考[建立一個API](../documents/CreateOldApi.md)