## [5.53.4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.53.3...5.53.4)


### Features

- ForumOcean
  - getAvailableBoardIds 新增 excludeChatroom 參數


## [5.53.3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.53.2...5.53.3)


### Bug Fixes

- ForumOcean
  - 調整取得用戶不分社團所有非聊天室看板文章回傳型別


## [5.53.2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.53.1...5.53.2)


### Bug Fixes

- ForumOcean
  - 調整取得用戶不分社團所有非聊天室看板文章參數及回傳欄位型別(Int? -> Long?)
  

## [5.53.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.53.0...5.53.1)


### Bug Fixes

- ForumOcean
  - Role 新增 CLUB_HOUSE (語音直播)

# [5.53.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.52.0...5.53.0)


### Features

- ForumOcean
  - 新增取得用戶不分社團所有非聊天室看板文章


# [5.52.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.51.0...5.52.0)


### Features

- ForumOcean
  - 新增聊天室相關API
  - 調整社團推播設定層級至看板


# [5.51.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.50.2...5.51.0)


### Features

- 升級 Kotlin 版本到 1.7.22
- 升級 CMoney-Util 到 5.5.0
- 升級 CMoney-LogDataRecorder 到 5.5.0
- 升級 SDK 版本 Min SDK 23，Target SDK 33


## [5.50.3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.50.2...5.50.3)


### Features

- ForumOcean
  - 新增聊天室相關API
  - 調整社團推播設定層級至看板

## [5.50.2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.50.1...5.50.2)


### Features

- DtnoExtension.kt 
  - toListOfType 支援 @SerializedName 的 alternate 欄位數值用於轉換資料物件
  - 備註：上述轉換方法優先使用 value 作為轉換基礎資料中找不到對應欄位名稱才使用 alternate  


## [5.50.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.50.0...5.50.1)


### Features

- 調整取得推薦文章API回傳物件

### Bug Fixes 

- 修正 toListOfType 無法處理 private class 物件建立問題

# [5.50.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.49.0...5.50.0)


### Features

- 新增釘選社團看板文章
- 新增取消釘選社團看板文章
- 新增取得社團看板置頂文章列表
- 新增取得社團看板置頂文章列表
- 將toListOfSomething()棄用，改用toListOfType()


# [5.49.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.48.1...5.49.0)


### Features

- 新增全域的Backend2管理者(目前能控制虛擬下單Domain)
- 新增可以變更Domain和特定Path的物件，詳看VirtualTradingV2資料夾


#### 虛擬下單

- 新增虛擬下單V2-建立帳號
- 新增虛擬下單V2-建立上市上櫃委託單
- 新增虛擬下單V2-刪除上市上櫃委託單
- 新增虛擬下單V2-取得全部帳號資訊
- 新增虛擬下單V2-取得特定帳號資訊
- 新增虛擬下單V2-取得帳號報酬率
- 新增虛擬下單V2-取得上市上櫃委託單
- 新增虛擬下單V2-取得上市上櫃委託單細節
- 新增虛擬下單V2-取得上市上櫃所有的成交單
- 新增虛擬下單V2-取得上市上櫃的成交單細節
- 新增虛擬下單V2-取得上市上櫃的庫存


## [5.48.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.48.0...5.48.1)


### Changes

- 調整 DispatcherProvider 實作 - DefaultDispatcherProvider 棄用層級為 Warning


# [5.48.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.47.0...5.48.0) (2023-02-07)


### Features

- 新增 DtnoData(FundIdData).toListOfType 減少資源的消耗與增加轉換效率
- DispatcherProvider 改為使用 utils 中定義，並調整對應之預設實作及測試用實作


#  (2023-01-07)



# [5.47.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.46.0...5.47.0) (2023-01-07)


### Features

* 新增編輯文章openGraph接口 ([ad66fcc](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ad66fccf7206f4d7910d16d6187885967e5c7c31))


### Bug Fixes

* BrokerDataTransmission 圖片辨識回傳欄位解析名稱調整


#  (2022-12-22)



# [5.46.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.45.0...5.46.0) (2022-12-22)


### Features

* BrokerDataTransmissionWeb 新增庫存圖片辨識 ([6bbb2d2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6bbb2d22de61d44742e819d12aab5742f1c792e9))



#  (2022-12-02)



# [5.45.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.44.2...5.45.0) (2022-12-02)


### Bug Fixes

* api version of createReportV2 ([51ac80f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/51ac80f4b448088b96b00065235943ae4de128d4))
* ForumOcean ServiceCase ([627f563](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/627f5630b5352b80cbcfbf8b1f071946f5ef52ab))
* ForumOceanWebImplTest ([4cabc75](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4cabc75e9b29e53b36526dccd5c35fa527f71ce8))
* mistaken response type of getCommentsByIndex. ([ebf4a6b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ebf4a6b94a4e0c19658562ddabaa71a79e6f9b98))
* PUT method for emoji ([193a648](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/193a648b38ccfdf32832bdb23284dfb71accd80d))
* Responses spec for recommendation ([37db5e6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/37db5e6628cd24a160284a23b65447c76d93124a))


### Features

* article v2 ([366da05](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/366da0575a6711ba6cc4b6688789b90916ec50aa))
  * article_v2 api調整 ([c2c20ca](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c2c20ca27071cff9a1d3e49cd4bd0206f7e79b65))
  * authType 欄位位置更動 ([9f357e3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9f357e3e375ca65662a11dbbde8d379972d1ed41))
  * createReaction ([5ab59b2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5ab59b2e8deec506ca795121314ce444b61b7626))
  * createReportV2 request body ([6af6153](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6af615363a34b8b0263a557ff90582801df254c5))
  * deleteCommentV2 ([b845e02](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b845e02603fe32348768f4cd58e3f923a6c43671))
  * deleteReaction ([29b59be](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/29b59be7f0be5dcd04b01a8fe2b594cd7ce65db7))
  * ForumOceanServiceCase 使用v2 api ([36eacd6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/36eacd630ae5a7497c968b7e2ef2032c2e88b8d6))
  * getCommentsByIndex ([f2ac12c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/f2ac12ce4989a37058780a165981ef45f512dc0a))
  * getReactionDetailV2 ([9f84607](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9f84607e94a682926b472753402ba283a8e34c45))
  * isAnonymous 欄位位置更動 ([647bd8a](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/647bd8a991a1a6ba95d5a2306e88eaea7cd7ef3a))
  * update getSingleComment path ([4844c5c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4844c5c2aefd7f64666f446e30a33336d5feb266))
  * 刪除文章/留言 v2 ([2b23c75](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/2b23c758fa2b6a58d6ca5161197d9a9da20d2d1a))
  * 刪除留言api path 更新 ([d2d96e2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d2d96e22a63e6d3da20024fa32fba7747bbf79c7))
  * 取得留言列表 startCommentId -> startCommentIndex ([83c051a](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/83c051ac0471ca9836b257d6725021ede7a99d2d))
  * 將v2 article拆分 並修改部分欄位名稱 ([84dd4fa](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/84dd4fa557b94908a2e2ce62f3a2842a208624f8))
  * 將v2部分物件欄位從Any->Boolean ([7f396e6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7f396e6870fb117028cfa2dbbfa73d2359a6441e))
  * 將精選response與文章v2分開 ([5444b50](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5444b50d5480b99f9e3ec2b23d3bc7b7cc478736))
  * 新增判斷精選文章欄位 ([82c8587](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/82c858793e3543095391e9d051b51c2f93f9194f))
  * 新增取單篇留言測試 ([27459a6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/27459a6e8c57b652ba94a645061c96dc2b68baa0))
  * 新增官方下標欄位 ([cb51865](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/cb51865cf2838e79fea932979236106b9261be96))
  * 更新ForumOceanServiceCase ([9f4f6cf](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9f4f6cf6d1dbd31318720f347a6077e6ab9329cd))
  * 檢舉留言 ([c2419ed](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c2419edf60c1d42a17d339a6b6fae25c509b1287))
  * 移除留言commentIndex欄位 ([9994bf4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9994bf46f9c5d0b3bbbf05f7cc7c1e0f5d04512e))
  * 精選文章清單 ([8a58537](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/8a58537f611ea0c9a71b01724e01bad06b953aee))
  * 隱藏/取消隱藏留言 ([8e5c349](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/8e5c349e2b24420c27bfacdb8f8a914810d4ac1c))



#  (2022-11-18)

## [5.44.2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.44.1...5.44.2)

### Bug Fixes

- 修正使用者分析上傳的事件無法被正確記錄在ELK
	- processID os device需要可以傳Null，並且是Null情況時，不應該傳遞Key在Json


#  (2022-11-07)



## [5.44.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.44.0...5.44.1) (2022-11-07)


### Bug Fixes

* 修正使用者分析上傳的事件持續時間應可空值 ([bbfaea4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/bbfaea483dd423f9c11a6f838212e5c09b57e48a))



#  (2022-10-31)



# [5.44.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.43.0...5.44.0) (2022-10-31)


### Features

* 發文新增title欄位 ([5f44694](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5f4469464cff807b3db89818fba1e8f6427930a1))



#  (2022-10-21)



# [5.43.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.42.0...5.43.0) (2022-10-21)


### Features

* **NotificationWeb:** 新增上傳推播到達數和點擊數的時間、標籤參數 ([545a2ac](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/545a2ac73947979ccaa0f15806fba0819c911878))



# 5.42.0

### Features

- 升級Koin到3.2.1
- 升級CMoneyUtils到5.4.0
- 升級LogDataRecorder->5.4.0
- 將所有Service的DI引用合併

#  (2022-10-03)



# [5.41.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.40.0...5.41.0) (2022-10-03)



#  (2022-09-27)



# [5.40.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.39.0...5.40.0) (2022-09-26)


### Features

* 新增籌碼K服務-期貨指數分析 ([d574052](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d5740529085432b05652c19f2ed9dadec00c96ca))



#  (2022-09-16)



# [5.39.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.38.0...5.39.0) (2022-09-16)


### Bug Fixes

* Build ([35ef088](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/35ef088e671a223ad9135bbb763e4151f0bd00bf))


### Features

* ForumOceanWeb 新增 GetColumnistAll ([79d8332](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/79d83327ac3642b2c2bf3ef1aa157cf63cb6c8de))



#  (2022-09-07)



# [5.38.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.38.0-beta02...5.38.0) (2022-09-07)

> support Kotlin 1.6.21

### Features

* 更新BuildSrc 中 Dependencies.kt, Versions.kt，
* Kotlin升級至1.6.21，targetSdk升至32
* 專案更新 Gradle Plugin to 7.1.3, 升級 Gradle to 7.2
* 更新LogDataRecorder to 5.3.0
* UnitTest 中 Coroutines Dispatchers.setMain 改為使用 utils-test 的 CoroutineTestRule 進行設定


#  (2022-09-05)



# [5.37.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.36.0...5.37.0) (2022-09-05)


### Features

* ForumOcean 新增取得會員的被評價資訊統計
* ForumOcean 新增取得指定評價
* ForumOcean 新增取得指定會員的被評價清單
* ForumOcean 新增評價會員


#  (2022-08-25)



# [5.36.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.35.0...5.36.0) (2022-08-25)


### Features

* ForumOceanWeb 新增 getMembersByRole ([6945515](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6945515499c4429835347fd66d53cc56fa9d8d55))



#  (2022-08-19)



# [5.35.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.34.0...5.35.0) (2022-08-19)


### Features

* ForumOcean 新增 OpenGraph field in articles. ([4ee15c7](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4ee15c7e4fe9df4632a095195863dacfcfdef738))



#  (2022-08-15)



# [5.34.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.33.1...5.34.0) (2022-08-15)


### Features

* 更新檢舉文章接口 ([7bd2fe5](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7bd2fe5fb8f08de1bc3bd46bf75e82bf5630eb9d))



#  (2022-08-12)



## [5.33.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.33.0...5.33.1) (2022-08-12)


### Bug Fixes

* 修正endCursor沒有轉成json格式問題 ([fd9fe55](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/fd9fe55b5995e7c0487488f6d86f3966f582c29a))



#  (2022-08-12)



# [5.33.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.32.0...5.33.0) (2022-08-12)


### Features

* Api of Group CRUD. ([e1d59c1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/e1d59c11a363430cf0a886f39e3c4ed5eff25d61))
* Api spec updated. ([9f2f7d4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9f2f7d475d52c8ba9e2d6ed2926a5025681d9e70))
* Available board ids. ([08ecee6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/08ecee644edd9ba66ed68fc602075da35cf7d610))
* Board's endpoints ([d34941d](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d34941d1cc894192d8cc07539bffbb7e10bc0420))
* CommonUse新增取得商品歷史推播事件 ([bde315c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/bde315ce842eb3a0406fa55b106ae2037d20249e))
* ForumOceanWeb implementation ([207b583](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/207b5839cba29c091401a9bb116621b168231d28))
* GroupMember endpoints ([10d5a05](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/10d5a0524c3d5a23f00ee9a128c7dd374c0767dc))
* PendingRequestDTO id/createTime改為Long ([8a6904f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/8a6904f8bd6782761a4cff01207cc10e9c59cb7e))
* PushSettings ([0ddb57c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/0ddb57c0944499f8dfcff732bd373156fb5f08a4))



#  (2022-08-03)



# [5.32.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.31.0...5.32.0) (2022-08-03)


### Features

* Support getting multiple products ([2b67971](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/2b6797113426aeb207da8cf01a7b43335a5f6349))



#  (2022-08-03)



# [5.31.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.30.0...5.31.0) (2022-08-03)


### Features

* 新增現金股利與股票股利欄位至 BrokerDataTransmissionWeb 的 getBrokerStockData 和 putBrokerStockData 方法 ([4deaec9](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4deaec9a78ddefef1340ea861797c572f6fe3889))



#  (2022-08-01)



# [5.30.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.29.0...5.30.0) (2022-08-01)


### Features

* CommonUse新增取得與更新用戶投資屬性 ([419897e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/419897e142ddd6108a5c90b4dae23f6feeb642d4))
* CommonUse更改更新用戶投資屬性回傳值 ([6e29ed6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6e29ed6edd5fa676dce0e08c5c0c8d3d6c81db71))



#  (2022-07-15)



# [5.29.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.28.0...5.29.0) (2022-07-15)


### Features

* 新增自選股搜尋，口袋場外的未定義類型。 ([eaf9b3f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/eaf9b3f3bf45abccdc1a16b3afcfca77727ce51c))



#  (2022-07-06)



# [5.28.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.27.0...5.28.0) (2022-07-06)


### Features

* 新增RealtimeAfterMarketWeb - getStockSinIndex(服務23.取得指數的成分股票清單)


#  (2022-07-04)



# [5.27.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.26.0...5.27.0) (2022-07-04)


### Features

* 調整ForumOcean - MemberStaticsResponse API路徑


#  (2022-07-01)



# [5.26.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.25.0...5.26.0) (2022-07-01)


### Features

* 新增 CommonUse 服務 ([4c6b313](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4c6b313d2c7c87f6c570dcdef788582ac58159f9))



#  (2022-06-14)



# [5.25.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.24.0...5.25.0) (2022-06-14)


### Features

* 新增ForumOcean取得專欄作家的VIP社團資訊 ([3209677](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/32096779f45d29d87219e5c1bca5d8295b8a8b34))
* 新增ForumOcean取得指定研究報告文章ID


#  (2022-06-09)



# [5.24.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.23.0...5.24.0) (2022-06-09)


### Features

* AuthorizationWeb.getPurchasedSubjectIds ([a0095e6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/a0095e6f6c13f72f8692658dd9ee08a357cbe203))



#  (2022-06-08)



# [5.23.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.22.0...5.23.0) (2022-06-08)


### Bug Fixes

* Coding standards ([bd3f1cd](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/bd3f1cdf4f870fec2f595a0608ef9e2c9d4b9052))
* GraphQL param ([223c99a](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/223c99ae44894cbe4b45708fb236dce0f47a3d92))
* 修正檢查訂閱狀態判斷 ([d6baa75](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d6baa751f9c5b5e2fd7e762967d111d1dd2e40ee))


### Features

* add ItemPrice for getting prodcut ([4ef4f8a](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4ef4f8ac62f438c3a87c8b3fa40ce65008e48980))
* ArticleType in content ([76d9e76](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/76d9e761b84311f861afe1c91f19dec5605e0de8))
* Exchange column article by p coin ([6303c3e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6303c3ef155cc131d370eac0a73852be68cf66c9))
* GetExchangeCount ([b081dad](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b081dadce2089f6ae055a3c49c35ca7b4e58a9bc))
* GetRole in ForumOcean ([ecbbf97](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ecbbf979d31ecb7834dd857d200f04c2e492b845))
* GetSaleItemBySubjectId ([030a8fa](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/030a8fa31276a661d5d683b79444e0028f1d3b2e))
* IsMemberSubscribe ([25fcc21](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/25fcc21aa621a2c45e98b7f3243994b9655e8c8e))
* 取得指定CMoney銷售類型的歷史訂閱數量 ([1266325](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/126632555734fb7314ed8b9865b029ea61246db3))
* 歷史訂閱數量改為Long ([a1514b4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/a1514b4ddf56fc890a1c4ed5d12c89d41144dfd2))



#  (2022-06-02)



# [5.22.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.21.0...5.22.0) (2022-06-02)


### Features

* **自選股搜尋:** 新增指數彙編、上市創新版、興櫃戰略新版 ([992cd8e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/992cd8e6c99a1cee53a1d9c05f1bfef7f05620ac))



#  (2022-05-18)



# [5.21.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.20.0...5.21.0) (2022-05-18)


### Features

* additionalInformation about change to use Previous replace Yesterday, and fix web service path default value ([6666071](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6666071005809781c5e5986b5853d9d675b38f79))



#  (2022-04-26)



# [5.20.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.19.0...5.20.0) (2022-04-26)


### Features

* ForumOcean新增以關鍵字搜尋會員 ([46278ed](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/46278ed0e86ab3ae62904ced295a0e29bddd2cde))



#  (2022-04-26)



# [5.19.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.18.0...5.19.0) (2022-04-26)


### Features

* ForumOcean新增個人文章之發文及取得 ([2e296f2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/2e296f2ac5fdb9857369d83982a711bacc921543))
* ForumOcean新增取得個人文章列表 ([3ddb8c0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/3ddb8c0fefee975b90ad2f9141c2da534d8e5565))



#  (2022-04-25)



# [5.18.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.17.0...5.18.0) (2022-04-25)


### Features

* add AdditionalInformationRevisit YesterdayData api ([c19c0f8](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c19c0f8a4410f70be69fdcdd5b06c9cf21d3b1f3))



#  (2022-04-21)



# [5.17.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.16.0...5.17.0) (2022-04-21)


### Features

* 新增台新取得 CAStatus 服務至 CrawlSettingService ([6c4e6cb](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6c4e6cbb57cf808abce12b28f5f85e70e6b23c8e))



#  (2022-04-13)



# [5.16.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.15.0...5.16.0) (2022-04-13)


### Features

* 新增 CrawlSettingService ([077540a](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/077540a99f5ae46bf4a12e24e90b2af10b93479e))



#  (2022-03-28)



# [5.15.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.14.0...5.15.0) (2022-03-28)


### Bug Fixes

* 將相對應市場類型的valueOf回傳，調整成屬於的市場類型。 ([0a9e8a0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/0a9e8a06a1b981f92b9fbf662c8abd69f798c648))


### Features

* 新增口袋證券場外、口袋證券自編 ([191c91d](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/191c91da48e80337b849be5d31b2600a39849fa7))
* 大富翁服務創建帳號之回傳改為回傳取得帳號之內容


#  (2022-03-16)



# [5.14.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.13.0...5.14.0) (2022-03-16)


### Features

* 更改getCardInstanceSns的path與參數 ([2f8abed](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/2f8abed3a1494a8da6e39264c8c2393850710a5e))



#  (2022-03-15)



# [5.13.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.12.0...5.13.0) (2022-03-15)


### Features

* 新增 VirtualTrade service ([5c98889](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5c98889d7ad12ca72ae955742d87aa18dcbb4c69))



#  (2022-03-14)



# [5.12.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.11.3...5.12.0) (2022-03-14)


### Features

* UserBehavior - Event新增duration欄位用於上傳時間事件


#  (2022-02-25)



## [5.11.3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.11.2...5.11.3) (2022-02-25)


### Bug Fixes

* 修正ForumOceanWeb Rank相關參數名稱


#  (2022-02-14)



## [5.11.2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.11.1...5.11.2) (2022-02-14)


### Bug Fixes

* 修正 BrokerStockData 服務的 TradeType 型別在傳輸時與文件不符的問題 ([589f5b0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/589f5b05949a8099395d28972ef8878ba0301350))



#  (2022-02-10)



## [5.11.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.11.0...5.11.1) (2022-02-10)



#  (2022-01-28)



# [5.11.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.10.4...5.11.0) (2022-01-28)


### Features

* add FrontEndLogger service ([072b245](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/072b245b4019016794c46734f9bc1157ada83762))



#  (2022-01-25)



## [5.10.4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.10.3...5.10.4) (2022-01-25)


* 調整ForumOcean GroupResponse.position enum value.

#  (2022-01-13)



## [5.10.3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.10.2...5.10.3) (2022-01-13)


### Bug Fixes

* 修正MemberProfile name查詢欄位 ([001bcaa](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/001bcaa6b49413f3b4a1955c2e2d685c0fb4017d))



#  (2022-01-10)



## [5.10.2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.10.1...5.10.2) (2022-01-10)


### Bug Fixes

* 修正response欄位型別錯誤 ([1b16a05](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/1b16a05c20afd6f792a64d42ead4cc254caec4bf))



#  (2022-01-08)



## [5.10.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.10.0...5.10.1) (2022-01-08)


### Features

* 新增根據關鍵字、回傳語系[Language]搜尋股市標的、市場類別[MarketType]搜尋股市標的，V2版本 ([3c69648](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/3c69648f3284be66622d69f54384aa05bda013cd))



# [5.10.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.9.0...5.10.0) (2022-01-06)


### Features

* 新增上市、上櫃ENT類型 ([1ba8dd0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/1ba8dd031c8ac9e3758fb2d855da264e36cd7fd1))
* 新增期貨、選擇權、期貨盤後、選擇權盤後、上市權證、上櫃權證 ([c7be593](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c7be5930f62e3a4dec1255dfdb44c97ad26d16cf))



# [5.9.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.8.0...5.9.0) (2022-01-05)


### Bug Fixes

* Field change type->notifyType in GetNotifyResponseBody.kt ([9cafa55](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9cafa550b95121344619c1a39d0b87b6bfc9936d))
* 修正其他會員資訊isNeedNickName實作 ([b948395](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b948395617f1bf10026ef7ec2cfa58ae21d04c06))
* 修正檢舉的列舉類型 ([4747919](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/474791909825ed08e2b264f90cf1f83ebb84bcf9))


### Features

* MemberProfileQueryBuilder 新增isBindingCellphone參數 ([a418aea](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/a418aeaf3996731bf782b4f54d8c112d8484a57e))
* profileWeb add getOtherMemberProfile ([c67fce2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c67fce260bbca004e26a71500489798d578d2290))
* 調整DataWeb使用baseHost及host作為API設定網域的機制 ([6ade97b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6ade97b6476139da3bd8fecc35070dd64f449474))



# [5.8.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.7.0...5.8.0) (2022-01-03)



# [5.7.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.6.0...5.7.0) (2021-12-23)



# [5.6.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.5.0...5.6.0) (2021-12-23)


### Features

* 新增 baseHost 至 BrokerDataTransmissionWeb ([6e10b60](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6e10b608101333852d58746999a11fe28ac58dd7))
* 新增 BrokerDataTransmission 服務 ([0da2555](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/0da2555d4be64c664d96d26380bb6d972251d66d))
* 新增 BrokerDataTransmission 服務 - wip ([413e0ed](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/413e0ed330e54c8626da9e75db903187e98a9208))
* 新增 deleteBrokerStockData 方法、調整 getBrokers 回應欄位 ([e4a0815](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/e4a0815e22601193a8de05ed189214b494f45ebb))



# [5.5.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.4.0...5.5.0) (2021-12-22)


### Features

* add authorization hasAuth ([be834c3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/be834c32509e639470cfd2af05aca15e27c53d2d))



# [5.4.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.3.1...5.4.0) (2021-12-17)


### Bug Fixes

* 修正 OceanWeb 的 SetReaded 成功時因為 No Content 而被判斷為失敗的問題 ([502403b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/502403bb4f6dc5ce791f9800b7645259e13dffc5))
* 修正缺少 被刪除的回文數 的欄位問題 ([5cd9ff6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5cd9ff63fe78a138f1711dc487ade6746adfa707))


### Features

* add image recognition service ([ae332d2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ae332d2279af7932add1982bcd04ff57aa4d7465))
* 新增三方的合作廠商登入方式 ([860d33b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/860d33bd8d6c28abeaa9585ebba7fc9f8bf161aa))
* 新增取得Youtube頻道影音api ([b8dfa47](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b8dfa47e5ce57391bcdffe838163fc3f31462565))



## [5.3.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.3.0...5.3.1) (2021-11-30)


### Bug Fixes

* change ChatRoomSettingProperties announcements return type ([7f17c67](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7f17c673c7afc4fcf2bf9f251db2bcaece23146b))



# [5.3.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.2.1...5.3.0) (2021-11-30)


### Features

* add chat getTargetRoom, updateTargetRoom function, let baseModule okhttp method internal ([10f33a0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/10f33a081ab164d1be1c29c5bf575bee514de2f1))



## [5.2.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.2.0...5.2.1) (2021-11-26)


### Bug Fixes

* RawMessage, Content json parse config ([578156e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/578156eb8a829afbd75b183d9dbc79fb0c57f94e))



# [5.2.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.1.1...5.2.0) (2021-11-25)


### Features

* 新增聊天室回覆訊息物件及解析 ([33b6bac](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/33b6bac1562bfc272d35ab815336677b69ccc27b))



## [5.1.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.1.0...5.1.1) (2021-11-24)



# [5.1.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/5.0.0...5.1.0) (2021-11-24)


### Bug Fixes

* 修正聊天室顯示訊息Parse機制 ([363c40b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/363c40b890f0ec7afbb6b11956c519bb2084f4da))
* 統一調整社團身份使用的類別 ([7f68384](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7f683847ea7a4c4b6f642b148146ace5cc48b751))
* 補上缺漏的文章狀態 ([a9af3cc](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/a9af3cc50b367eed94f4318a57fa0e5c35eaf38b))


### Features

* add identityProviderWeb new login method - loginByPkce and its unitTest ([516f669](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/516f66938362499d7d75dbd6bdeedb4a2b26a910))
* ForumOceanWebImpl 提供參數來修改url path ([0733435](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/0733435a3bed82679cf73f05f3443a8f7a65b889))
* 取得通知清單api 介面更動 ([bea20ae](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/bea20ae61ff4228d1480db770796ed76ecc114da))
* 新增 DataService 及取得 FundId 相關服務及測試 ([d3abb83](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d3abb838bb803c08a7ae8639222b9a21bb504e29))



# [5.0.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.10.0...5.0.0) (2021-11-04)


### Features

* 取得多位使用者資訊Api 新增欄位 該用戶是否有綁定手機 ([dc712b1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/dc712b1e141e7be456cdda9f7dae0d029bc3a5c5))
* 拿掉在OceanService上 檢查是否手機綁定的api(IsPhoneAuthentication) 該功能交由其他站台負責 ([5ffe5e0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5ffe5e001a601f013d226a2edad5e713078475b8))



# [4.10.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.9.0...4.10.0) (2021-11-02)


### Features

* 新增api  IsPhoneAuthentication ([d8e68c6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d8e68c67f08f4c7aac4329d19dee928220aa5e20))
* 新增測試domain ([35f7c7e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/35f7c7e1955bb08ffb49348d8ebd4f0c97338bdd))
* 調整CustomGroupService2的GetCustomGroup參數 ([77e10e2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/77e10e28fdc42a6c2f6b6afd0d87928c969dabed))



# [4.9.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.8.0...4.9.0) (2021-10-27)


### Bug Fixes

* Proguard ([fcae639](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/fcae639ab52f844452d31b176c7548a5af12bf44))
* 修正redirectInfo物件的SerializedName ([369bff8](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/369bff8bbc4dc11dc039e3feb0c5852bbabe9bb4))


### Features

* add serviceCase ([c361f66](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c361f66c735d3453b8bdc58ef0eb8f6596d1c696))
* 取得回文物件新增欄位 ([839f840](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/839f840bc3f57abe9daf346772abfa1c6ec58392))
* 因應服務格式變更修正通知轉導資訊格式 ([a1de7c4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/a1de7c44e022ad0e333f6d31e60d5479bf3a7b94))
* 新增ClientConfiguration API ([83a9c1c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/83a9c1c5e3d4c62e0df0e68126e6523015e5ea74))
* 新增取得設定檔失敗unitTest ([c66c2c4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c66c2c496c6ec35c92954c55b3d88bc4652d6d83))
* 新增美股文章頻道 ([6b91f13](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/6b91f13a97372cc0e159d9cc8c93b6728c26968f))
* 新增重設通知數量api ([8c680b2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/8c680b208f79170431c2107ba211941214a4d3f3))
* 新聞文章欄位調整 ([d8d2c58](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d8d2c584c956692dbab9a6c3f661d4a065691505))
* 添加LogDataRecorder的Annotation及測試 ([bc56232](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/bc562327e3159e2324533076f59832edd271e254))
* 社團資訊新增欄位 ([bbd3355](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/bbd3355b88e785559c7713ab323dc9377fee5bec))



# [4.8.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.7.0...4.8.0) (2021-10-08)


### Bug Fixes

* 修正CentralizedImage 的service 設定錯誤 ([3f445d8](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/3f445d8169729c13e7c28d90554ec42b1886ffee))
* 修正CentralizedImageService界面不符問題 ([ae28d67](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ae28d673e2928a9b657939e8fcf5863f882bb8bb))


### Features

* backend gson 加入 ULongTypeAdapter ([9eca072](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9eca07273b5475d1052f61cef7103790a2a7e4fe))
* 因應服務格式變更修正通知參數格式 ([da0e9e1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/da0e9e147fc3efb4372f42e61f185bccdcd909f2))
* 新增自選股服務 ([e6b961c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/e6b961c3b778b5488a8fa48a9e96c5004546c586))
* 更新 README 說明 unsigned data type 相關支援 ([ddd8525](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ddd8525fa418ecb6c7eb6ed4265e93d85f1ad01b))
* 根據服務變動調整 getNewTickInfo 的回傳成交量欄位型別並加入單元測試 ([ee0c443](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ee0c443340f7550ed1dadd32b0f7e94c517b9616))
* 根據服務變動調整 getNewTickInfo 的回傳成交量欄位型別並加入單元測試 並新增對應的 Gson ULongTypeAdapter ([b51087d](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b51087d9c89391b19bdd90ea12f31cac7d9f0492))
* 根據服務變動調整介面 ([d8dcb45](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d8dcb4500102b8ea3448cf1c85245b0cab12fb86))



# [4.7.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.6.0...4.7.0) (2021-09-23)


### Bug Fixes

* 修正ApiLog欄位 Encode的方式 ([22377dc](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/22377dcbe8fa5fb70c18dccbe7db6c4b0ffc1afa))
* 修正Header ApiLog字串經過URL Encode ([b413e18](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b413e18da19c1097ccf946299c69603304054fe1))
* 修正因官方Api修改介面而發生問題的測試 ([8cbfafd](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/8cbfafd59181999cdad6df09a73d2405e3e4dfd5))


### Features

* 修改取文的api ([5fb16e2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5fb16e2808b8adab482a23f0776e92abf5a772c6))
* 修改解題達人參數名稱 ([4a8b707](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4a8b70779d638132f06ede0eca2228bae7b82115))
* 取得社團清單的三道Api新增是否包含App社團的參數 ([f9033af](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/f9033af95385e98cb7ecd334d2895fc658edfc04))
* 官方頻道資訊新增 subscribeCount 欄位 。並將所有欄位設定為可nullable ([52e42c5](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/52e42c5ffbae5af460ba895b4cfeca5ce88672dc))
* 指定達人排行參數名稱修正 ([ef6ec31](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ef6ec31639f486bc48847f66011114131baac865))
* 新增GenreAndSubGenre參數物件，新增401測試，修正其餘測試 ([ad4bee3](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ad4bee323f6774e8f592bbcacc3be8196bb74537))
* 新增取得自己是否被禁言的Api ([f21c1e9](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/f21c1e90a0b44509792f33cec4197a7439249b84))
* 新增官方頻道Api   GetOfficials ,GetOfficialsByIds,GetOfficialsByKeyword 修改掉之前官方頻道的Api ([847b168](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/847b168ab63f5a8030bfa13ac428d95a9f82d0bd))
* 新增排行榜api ([150e069](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/150e0698bbf1f87d08cdb6e36c39831b400370f6))
* 新增通知三道api  通知設定三道api ([e5696c4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/e5696c414c37ca7dc6f2729267e0a343b3907177))
* 達人排行熱門值改為Double ([7c92bc4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7c92bc401dd0fe649dd0c4fd13c821fb4a5dfea6))



# [4.6.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.5.1...4.6.0) (2021-09-08)



## [4.5.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.5.0...4.5.1) (2021-09-04)


### Bug Fixes

* 修正UserBehavior沒加入Proguard ([0239681](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/0239681ef32fd4c22863e3251067f60a538523fa))



# [4.5.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.4.0...4.5.0) (2021-09-04)


### Bug Fixes

* 修正發文Content 社團文章不會有Topics欄位 ([0132ac1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/0132ac15052eb8130f05b6f9ce3898a0523a1bcf))
* 修正錯誤的request字串 ([8c74f12](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/8c74f12f4ce2d91637a79cd68e7ed6ec0a6f5b9c))


### Features

* GetMemberStatistics 回傳物件新增資料欄位 ([9b9004b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9b9004b2fbeffae5d82147712fe0d9a7d3160406))
* 修改取得使用者訂閱的官方頻道清單的api 改為分頁取得的方式 ([d9f7fad](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d9f7fad1433bdb8e5a620c2f961f6d0d2c2df318))
* 修改取得打賞清單的api 改為分頁取得的方式 ([32cdd99](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/32cdd99db776a6c8eaae26420f01df6314d3707b))
* 修改取得申請加入社團待審核清單的api 改為分頁取得的方式 ([9f92a83](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9f92a83a5ab64155e317fb42883ba4ccd45b8429))
* 修改取得社團清單相關的api 改為分頁取得的方式 ([cb3f83e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/cb3f83e656401eba65091932bb9c12922665ed5a))
* 修改更新文章 與 回文的api ([cd1aca4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/cd1aca4e4d605d0948e4337595be2be38f167e79))
* 取得正在追蹤 被追蹤 封鎖 被封鎖 清單的api改成分頁取資料的方式 ([650cab6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/650cab6e0d46a884bfe420e3e69bc69fc0a4f73a))
* 將所有可以使用標籤的文章都實做TopicInfo ([4466a7c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4466a7c27d3612ec27617f8db9e2b100775cc192))
* 打賞資訊新增用戶打賞的資料 ([2cf4f03](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/2cf4f039359e1199b3fe54ba9b0536672f74291e))
* 新增 取得指定的會員清單與我目前的關係 的api ([53ea9de](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/53ea9def6463ee1a7a44f50d602344231d800c70))
* 新增使用者分析API ([45661cc](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/45661ccaab4eee60f2fc6682ddf848885ed41551))
* 新增目前全部有實做的文章頻道類型定義 ([95ed4a1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/95ed4a135fb0a7d070da776ddd450990538a7d84))
* 社團資訊回傳新增在社團的職位資訊 ([7afc657](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7afc6570698227078a4093d71744647ccd3e9213))
* 移除 GroupArticle/Delete 的方法 ([3d31963](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/3d31963a293154ff326f01e2f652d48e6de92a4a))
* 調整成新版文章分類 ([324e7d6](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/324e7d699cb65345325b74e9e1479920a4b364b4))
* 調整成新版文章分類的方式呼叫api ([49b988f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/49b988f1f1c4457903a55b4902c795a73de3c6a4))



# [4.4.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.4.0-beta01...4.4.0) (2021-08-23)


### Bug Fixes

* 修正MobileService-追訊，服務3-4. 我也想知道回答 action沒有全小寫 ([c224f06](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c224f06391edc99911b76a52416ab3c8d5555396))


### Features

* 新增判斷用戶是否有CMoney續約中手機商品 ([03d4d4a](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/03d4d4a24dffd7fe58a443aba90ec416b290186f))
* 補RecordApi ([4f12205](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/4f12205b3b9da7cc3f34cb989669fabdef269b04))



# [4.4.0-beta01](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0...4.4.0-beta01) (2021-08-20)


### Features

* 新增權限Type列舉 ([edfe47b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/edfe47b85448ac68af50f6057904667ae13e8810))
* 新增美股股票文章的類別 ([256f8a8](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/256f8a86e3f62e80c49eaf6edb31067b6cce2401))
* 新增語系Header物件，新增自選股服務語系參數 ([9ce9868](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9ce986842d1b1f307d1ecd3054ddf0065450213c))



# [4.3.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha07...4.3.0) (2021-08-12)


### Features

* 新增ChipKWeb 服務6-9. 要求國際盤後資料 ([88581b4](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/88581b44e8163e8be54f26b9860a7ddb926d2864))



# [4.3.0-alpha07](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha06...4.3.0-alpha07) (2021-08-11)


### Features

* 新增可取得他人的自我介紹欄位 ([d0f32e7](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/d0f32e7ff65e72d88ce749abdc0ebf727a715354))



# [4.3.0-alpha06](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha05...4.3.0-alpha06) (2021-08-10)


### Features

* 調整附加資訊Service可以呼叫不同的domain ([b0f09fa](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/b0f09fac21a1d05f2d0d6d16fa588805bfa1934f))



# [4.3.0-alpha05](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha04...4.3.0-alpha05) (2021-08-06)


### Bug Fixes

* 修正文章狀態欄位放錯位置問題 ([04affbc](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/04affbcac3c2d4d6b88d53f2b8813a5c2a0e5762))



# [4.3.0-alpha04](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha03...4.3.0-alpha04) (2021-08-05)


### Bug Fixes

* 修正發文時股票Tag的猜多空狀態使用String傳出 應該要以Int形式傳出 ([155ee80](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/155ee803cf3de2ea3abc0da2af3b64a9fd9c3e25))



# [4.3.0-alpha03](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha02...4.3.0-alpha03) (2021-08-05)


### Bug Fixes

* createComment createTime ms -> s ([635b097](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/635b09795c52700a05e35317d252cbf3ad5ce522))


### Features

* 新增Google登入 ([ca1127f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ca1127fd52370f5b5c9c3cd0bc824094feef3568))



# [4.3.0-alpha02](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.3.0-alpha01...4.3.0-alpha02) (2021-08-03)



# [4.3.0-alpha01](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.2.0...4.3.0-alpha01) (2021-07-30)



# [4.2.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.2.0-alpha01...4.2.0) (2021-07-30)



# [4.2.0-alpha01](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.1.2-alpha01...4.2.0-alpha01) (2021-07-24)


### Features

* 新增CustomGroup2 service及UnitTest ([9bbc1ac](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/9bbc1aca0a58e9fdbdb9d24ae773dd3d6edc0637))



## [4.1.2-alpha01](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.1.1...4.1.2-alpha01) (2021-07-23)


### Bug Fixes

* 修正發回文時 。回文的附加多媒體的Key ([fe2b95e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/fe2b95e72ce87b841ee927339de64610549a3ff4))



## [4.1.1](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.1.0...4.1.1) (2021-07-20)


### Features

* ImageService改版 。名稱改為CentralizedImageSerice 並調整api介面 ([7e53ece](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/7e53ece54ff4dbc6d4abbeca88d77d7fb68a5324))
* 新增API教學步驟 ([c39f781](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c39f781c778fd73faaa0ddf2601299e097129a9f))
* 新增Proguard規則 ([5bde426](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/5bde42613592308f905cef4dbf9374659b2d7b21))



# [4.1.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/4.0.0...4.1.0) (2021-07-14)


### Features

* 新增AccessToken.isGuest, IdentityToken.isNewUser, JwtToken.isDefault及相關測試 ([17f6a0e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/17f6a0e0d0bbdb0c538356cea52aa9795c2339f1))



# [4.0.0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/compare/3f5ad5b31e1db7e2f3ca0efac44553bfb9fb172c...4.0.0) (2021-07-14)


### Features

* chipkserver新增服務 ([190228f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/190228ff8602e0e3b7a151ae6fdc8d820058ddf3))
* 將ForumOcean3.0.0-alpha4的修改同步到backend2整合包裡 ([1e1eb16](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/1e1eb1657db99ebace2a33fe5d670479aa876268))
* 新增ActivityService,AdditionalInformationRevisitService.kt,AuthorizationService.kt,BillingService.kt,CellphoneService.kt,ChatRoomService.kt,ChipKService.kt,CMTalkService.kt,CommonService.kt,CustomGroupService.kt,DtnoService.kt,EmilyService.kt,ForumOceanService.kt,ImageService.kt的Api Log ([2c9788d](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/2c9788d580ff1395d23570256e29b3cc25841ed9))
* 新增Authorization module ([3f5ad5b](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/3f5ad5b31e1db7e2f3ca0efac44553bfb9fb172c))
* 新增Common module ([ea16b4c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ea16b4c2468d26ba0f8f8cfc873ac8029e05b815))
* 新增CRM服務、新版錯誤解析邏輯 ([c07910e](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/c07910e6b41797d8dd2236465b8941ca6ce0edf0))
* 新增CRM服務的RecordApi ([825b1cb](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/825b1cbe55019e93e95d594b586b5588b3fa1eee))
* 新增ForumOcean module ([16d59f5](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/16d59f58f9b2e6dfbc1293504bcd23f51ddd9812))
* 新增Identity的Log ([ba4f320](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/ba4f320a1d06a50c3a1fef74f88588155f0f4da1))
* 新增Image module ([14621ad](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/14621ad6500a9f630e3cf087efaa26a43c42654c))
* 新增MobileOcean module ([127ccbb](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/127ccbb0ccd3a38a4fe28ffeba36dfb9e225134e))
* 新增NoteExtension module ([71b7fc2](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/71b7fc22b159f0dbe9490da23b0df09cc02162d8))
* 新增Ocean module ([544a838](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/544a838203941514bdb1af73f37b9047059b2b60))
* 新增Profile module ([10ea9b9](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/10ea9b9cf0f37d023ad50d1cb2bc2eb1b31aa7e4))
* 新增RealTimeAfterMarket - GetCommList ([af4e2cb](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/af4e2cb21121b3e4c2a0c62d2957d48bf98d692e))
* 新增RealTimeAfterMarket - GetForeignExchangeTicks ([27c8c9c](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/27c8c9c89cbe3a1bb995bdc8fdf05136963a2246))
* 新增Request的action和過濾Authorization ([e115b6f](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/e115b6fe49c7d8530e381ed09727ff34e95e1b60))
* 更新MobileMedia module ([41275e0](http://192.168.10.147/CG_Mobile/CG_Module_Android/Backend2/backend2/commits/41275e098a4a554486df1721245ad87ed2678763))



