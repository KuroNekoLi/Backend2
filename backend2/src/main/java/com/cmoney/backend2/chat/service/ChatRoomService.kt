package com.cmoney.backend2.chat.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.chat.service.api.chatroomsetting.request.ChatRoomSettingUpdateProperties
import com.cmoney.backend2.chat.service.api.chatroomsetting.response.ChatRoomSettingResponseBody
import com.cmoney.backend2.chat.service.api.getallreport.request.ReportInfo
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.RawMessage
import com.cmoney.backend2.chat.service.api.getsubjectallrequests.response.AuthorizationRequestResponseBody
import com.cmoney.backend2.chat.service.api.getuserprofile.response.UserProfileResponseBody
import com.cmoney.backend2.chat.service.api.reportsomeone.request.ReportSomeone
import com.cmoney.backend2.chat.service.api.updateuserprofile.request.UpdateUserProfile
import com.cmoney.backend2.chat.service.api.updateuserprofile.response.UpdateUserProfileResponseBody
import com.cmoney.backend2.chat.service.api.variable.RuleSet
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ChatRoomService {
    /**
     * 取得自己的使用者資訊
     *
     * @param authorization JWT token
     * @return 使用者資訊Map
     */
    @RecordApi
    @GET
    suspend fun getUserProfileSelf(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<UserProfileResponseBody>

    /**
     * 取得目前使用者對指定對象所有角色
     */
    @RecordApi
    @GET
    suspend fun getUserCurrentSubjectRoles(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<String?>>

    /**
     * 取得指定對象所有使用者的角色
     */
    @RecordApi
    @GET
    suspend fun lookUpSubjectAllRoles(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Map<String, List<String?>>>

    /**
     * 更新自己的使用者資訊
     *
     * @param authorization JWT token
     * @param userProfile 欲更新的物件
     * @return 更新後的使用者資訊Map
     */
    @RecordApi
    @PATCH
    suspend fun updateUserProfile(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body userProfile: UpdateUserProfile
    ): Response<UpdateUserProfileResponseBody>

    /**
     * 綁定規則
     *
     * @param authorization JWT Token
     *
     * @return 成功則 status code 200, 失敗則status code 非 200
     *
     */
    @RecordApi
    @POST
    suspend fun bindRuleSet(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    /**
     * 增加一個角色設定
     *
     * @param authorization JWT Token
     *
     * @return 成功則 status code 200, 失敗則status code 非 200
     */
    @RecordApi
    @POST
    suspend fun addRole(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    /**
     * 移除一個角色設定
     *
     * @param authorization JWT Token
     * @return 成功則 status code 200, 失敗則status code 非 200
     */
    @RecordApi
    @DELETE
    suspend fun deleteRole(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    /**
     * 取得指定對象所有權限申請
     * 範例:
     * [
     *      {
     *          "userId": 0,
     *          "role": "string"
     *      }
     *  ]
     *
     * @param authorization JWT Token
     *
     * @return 申請物件的陣列
     */
    @RecordApi
    @GET
    suspend fun getSubjectAllAuthorizationRequests(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<AuthorizationRequestResponseBody?>>

    /**
     * 向管理員申請指定角色之權限
     *
     * @return 成功則 status code 200, 失敗則status code 非 200
     */

    /**
     * 取得對象綁定規則清單
     *
     * @param authorization JWT Token
     * @return 綁定的規則清單
     */
    @RecordApi
    @GET
    suspend fun getBindingSubjectRuleSets(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<RuleSet?>>

    /**
     * 取得所有可用聊天室相關設定檔
     *
     * @param authorization JWT Token
     * @return 可用聊天室設定陣列
     */
    @RecordApi
    @GET
    suspend fun getAvailableRoom(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<ChatRoomSettingResponseBody?>>

    /**
     * 取得指定聊天室相關設定檔
     *
     * @param authorization JWT Token
     * @return 目標聊天室ID
     */
    @RecordApi
    @GET
    suspend fun getTargetRoomSetting(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ChatRoomSettingResponseBody>

    /**
     * 更新聊天室設定檔
     *
     * @param authorization JWT Token
     * @param updateProperties 更新設定
     * @return 更新後的設定檔
     */
    @RecordApi
    @PATCH
    suspend fun updateChatRoomSetting(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body updateProperties: ChatRoomSettingUpdateProperties
    ): Response<ChatRoomSettingResponseBody>

    @RecordApi
    @POST
    suspend fun requestJoinChatRoom(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    /**
     * 取得歷史訊息, 新訊息優先
     *
     * @param authorization JWT token
     * @param requestParam 參數KeyValueMap
     * @return 歷史訊息陣列
     */
    @RecordApi
    @GET
    suspend fun getHistoryMessageLatest(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @QueryMap requestParam: Map<String, String>
    ): Response<List<RawMessage?>>

    /**
     * 取得歷史訊息, 舊訊息優先
     *
     * @param authorization JWT token
     * @param requestParam 參數KeyValueMap
     * @return 歷史訊息陣列
     */
    @RecordApi
    @GET
    suspend fun getHistoryMessageOldest(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @QueryMap requestParam: Map<String, String>
    ): Response<List<RawMessage?>>

    /**
     * 取得目標訊息 by Id
     *
     * @param authorization String JWT token
     *
     * @return 目標訊息
     */
    @RecordApi
    @GET
    suspend fun getMessageById(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<RawMessage>

    /**
     * 刪除訊息
     *
     * @param authorization String JWT token
     *
     * @return Response<ResponseBody> statusCode 200 success 非200則失敗
     */
    @RecordApi
    @DELETE
    suspend fun deleteMessage(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    /**
     * 上傳圖片
     *
     * @param formFile 圖片檔案
     * @return 圖片下載網址
     */
    @RecordApi
    @Multipart
    @POST("api/Image/Upload")
    suspend fun uploadImage(
        @Url url: String,
        @Part formFile: MultipartBody.Part
    ): Response<ResponseBody>

    /**
     * 取得所有使用者ID清單
     *
     * @param authorization JWT token
     *
     */
    @RecordApi
    @GET
    suspend fun getAllUser(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<Long?>>

    /**
     * 取得線上使用者數
     *
     * @param authorization JWT token
     *
     * @return 人數
     */
    @RecordApi
    @GET
    suspend fun getOnlineUserCount(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Long>

    /**
     * 取得多個使用者資訊
     *
     * @param authorization JWT token
     * @return 使用者資訊Map清單
     */
    @RecordApi
    @GET
    suspend fun getUserProfile(
        @Url url: String,
        @Header("Authorization") authorization: String,
    ): Response<List<UserProfileResponseBody?>>

    /**
     * 新增一筆檢舉
     *
     * @param authorization JWT token
     * @param body 被檢舉人的資訊
     */
    @RecordApi
    @POST
    suspend fun reportSomeone(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: ReportSomeone
    ): Response<ResponseBody>

    /**
     * 取得指定聊天室全部被檢舉人清單
     *
     * @param authorization JWT token
     *
     */
    @RecordApi
    @GET
    suspend fun getAllReport(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<List<ReportInfo>>

    /**
     * 刪除檢舉
     *
     * @param authorization String JWT token
     * @param id Long 檢舉ID
     * @return Response<ResponseBody> statusCode 200 success, not 200 fail
     */
    @RecordApi
    @DELETE
    suspend fun deleteReport(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("reportId") id: Long
    ): Response<ResponseBody>

}
