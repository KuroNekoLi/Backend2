package com.cmoney.backend2.chat.service

import com.cmoney.backend2.chat.service.api.chatroomsetting.response.ChatRoomSettingResponseBody
import com.cmoney.backend2.chat.service.api.getallreport.request.ReportInfo
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.Message
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
     * 取得目前使用者對指定對象所有角色
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @return 使用者對Subject所有角色的陣列
     */
    @GET("api/Authorization/{subject}/Role/Current/{subjectId}")
    suspend fun getUserCurrentSubjectRoles(
        @Header("Authorization") authorization: String,
        @Path("subject") subject: String,
        @Path("subjectId") subjectId: Long
    ): Response<List<String?>>

    /**
     * 取得指定對象所有使用者的角色
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @return 所有使用者對角色的字典
     */
    @GET("api/Authorization/{subject}/Role/Lookup/{subjectId}")
    suspend fun lookUpSubjectAllRoles(
        @Header("Authorization") authorization: String, @Path("subject") subject: String, @Path(
            "subjectId"
        ) subjectId: Long
    ): Response<Map<String, List<String?>>>

    /**
     * 綁定規則
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @param ruleSetId 規則ID
     * @return 成功則 status code 200, 失敗則status code 非 200
     */
    @POST("api/Authorization/{subject}/Binding/{subjectId}/{ruleSetId}")
    suspend fun bindRuleSet(
        @Header("Authorization") authorization: String, @Path("subject") subject: String, @Path(
            "subjectId"
        ) subjectId: Long, @Path("ruleSetId") ruleSetId: Long
    ): Response<ResponseBody>

    /**
     * 向管理員申請指定角色之權限
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @param role 角色名稱
     * @return 成功則 status code 200, 失敗則status code 非 200
     */
    @POST("api/Authorization/{subject}/Request/{subjectId}/{role}")
    suspend fun requestJoinChatRoom(
        @Header("Authorization") authorization: String, @Path("subject") subject: String, @Path(
            "subjectId"
        ) subjectId: Long, @Path("role") role: String
    ): Response<ResponseBody>

    /**
     * 增加一個角色設定
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @param userId 使用者ID
     * @param role 角色名稱
     * @return 成功則 status code 200, 失敗則status code 非 200
     */
    @POST("api/Authorization/{subject}/Role/{subjectId}/{userId}/{role}")
    suspend fun addRole(
        @Header("Authorization") authorization: String, @Path("subject") subject: String, @Path(
            "subjectId"
        ) subjectId: Long, @Path("userId") userId: Long, @Path("role") role: String
    ): Response<ResponseBody>

    /**
     * 移除一個角色設定
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @param userId 使用者ID
     * @param role 角色名稱
     * @return 成功則 status code 200, 失敗則status code 非 200
     */
    @DELETE("api/Authorization/{subject}/Role/{subjectId}/{userId}/{role}")
    suspend fun deleteRole(
        @Header("Authorization") authorization: String, @Path("subject") subject: String, @Path(
            "subjectId"
        ) subjectId: Long, @Path("userId") userId: Long, @Path("role") role: String
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
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @return 申請物件的陣列
     */
    @GET("api/Authorization/{subject}/Request/{subjectId}")
    suspend fun getSubjectAllAuthorizationRequests(
        @Header("Authorization") authorization: String,
        @Path("subject") subject: String,
        @Path("subjectId") subjectId: Long
    ): Response<List<AuthorizationRequestResponseBody?>>

    /**
     * 取得對象綁定規則清單
     *
     * @param authorization JWT Token
     * @param subject [com.cmoney.backend2.chat.service.api.variable.Subject]的名稱
     * @param subjectId 對象ID
     * @return 綁定的規則清單
     */
    @GET("api/Authorization/{subject}/Binding/{subjectId}")
    suspend fun getBindingSubjectRuleSets(
        @Header("Authorization") authorization: String,
        @Path("subject") subject: String,
        @Path("subjectId") subjectId: Long
    ): Response<List<RuleSet?>>

    // ChatRoom

    /**
     * 取得所有可用聊天室相關設定檔
     *
     * @param authorization JWT Token
     * @return 可用聊天室設定陣列
     */
    @GET("api/Chatroom")
    suspend fun getAvailableRoom(@Header("Authorization") authorization: String): Response<List<ChatRoomSettingResponseBody?>>

    /**
     * 取得指定聊天室相關設定檔
     *
     * @param authorization JWT Token
     * @param chatRoomId 聊天室ID
     * @return 目標聊天室ID
     */
    @GET("api/Chatroom/{chatroomId}")
    suspend fun getTargetRoomSetting(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") chatRoomId: Long
    ): Result<ChatRoomSettingResponseBody>

    /**
     * 更新聊天室設定檔
     *
     * @param authorization JWT Token
     * @param chatRoomId 聊天室ID
     * @param settingMap 更新設定
     * @return 更新後的設定檔
     */
    @PATCH("api/Chatroom/{chatroomId}")
    suspend fun updateChatRoomSetting(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") chatRoomId: Long,
        @Body settingMap: Map<String, Any>
    ): Result<ChatRoomSettingResponseBody>

    /**
     * 取得目標訊息 by Id
     *
     * @param authorization String JWT token
     * @param messageId Long 訊息ID
     * @return 目標訊息
     */
    @GET("api/Chatroom/Message/{messageId}")
    suspend fun getMessageById(
        @Header("Authorization") authorization: String,
        @Path("messageId") messageId: Long
    ): Response<Message>

    /**
     * 取得歷史訊息, 新訊息優先
     *
     * @param authorization JWT token
     * @param roomId 聊天室ID
     * @param map 參數KeyValueMap
     * @return 歷史訊息陣列
     */
    @GET("api/Chatroom/{chatroomId}/Message/Latest")
    suspend fun getHistoryMessageLatest(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long,
        @QueryMap map: Map<String, String>
    ): Response<List<Message?>>

    /**
     * 取得歷史訊息, 舊訊息優先
     *
     * @param authorization JWT token
     * @param roomId 聊天室ID
     * @param map 參數KeyValueMap
     * @return 歷史訊息陣列
     */
    @GET("api/Chatroom/{chatroomId}/Message/Oldest")
    suspend fun getHistoryMessageOldest(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long,
        @QueryMap map: Map<String, String>
    ): Response<List<Message?>>

    /**
     * 刪除訊息
     *
     * @param authorization String JWT token
     * @param roomId Long 聊天室ID
     * @param messageId Long 訊息ID
     * @return Response<ResponseBody> statusCode 200 success 非200則失敗
     */
    @DELETE("api/Chatroom/{chatroomId}/Message/{messageId}")
    suspend fun deleteMessage(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long,
        @Path("messageId") messageId: Long
    ): Response<ResponseBody>

    // Image

    /**
     * 上傳圖片
     *
     * @param formFile 圖片檔案
     * @return 圖片下載網址
     */
    @Multipart
    @POST("api/Image/Upload")
    suspend fun uploadImage(@Part formFile: MultipartBody.Part): Response<ResponseBody>

    // Ping

    // Report

    /**
     * 新增一筆檢舉
     *
     * @param authorization JWT token
     * @param roomId 在哪個聊天室
     * @param body 被檢舉人的資訊
     */
    @POST("api/Chatroom/{chatroomId}/Report")
    suspend fun reportSomeone(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long,
        @Body body: ReportSomeone
    ): Response<ResponseBody>

    /**
     * 取得指定聊天室全部被檢舉人清單
     *
     * @param authorization JWT token
     * @param roomId
     */
    @GET("api/Chatroom/{chatroomId}/Report")
    suspend fun getAllReport(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long
    ): Response<List<ReportInfo>>

    /**
     * 刪除檢舉
     *
     * @param authorization String JWT token
     * @param roomId Long 聊天室ID
     * @param id Long 檢舉ID
     * @return Response<ResponseBody> statusCode 200 success, not 200 fail
     */
    @DELETE("api/Chatroom/{chatroomId}/Report")
    suspend fun deleteReport(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long,
        @Query("reportId") id: Long
    ): Response<ResponseBody>

    // Statistic

    /**
     * 取得線上所有使用者ID清單
     *
     * @param authorization JWT token
     * @param roomId 聊天室ID
     * @return ID清單
     */
    @GET("api/Chatroom/{chatroomId}/Statistic/OnlineUser")
    suspend fun getAllOnlineUser(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long
    ): Response<List<Long?>>

    /**
     * 取得線上使用者數
     *
     * @param authorization JWT token
     * @param roomId 聊天室ID
     * @return 人數
     */
    @GET("api/Chatroom/{chatroomId}/Statistic/OnlineUserCount")
    suspend fun getOnlineUserCount(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long
    ): Response<Long>

    /**
     * 取得所有使用者ID清單
     *
     * @param authorization JWT token
     * @param roomId 聊天室ID
     * @return ID清單
     */
    @GET("api/Chatroom/{chatroomId}/Statistic/User")
    suspend fun getAllUser(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long
    ): Response<List<Long?>>

    /**
     * 取得使用者數
     *
     * @param authorization JWT token
     * @param roomId 聊天室ID
     * @return 人數
     */
    @GET("api/Chatroom/{chatroomId}/Statistic/UserCount")
    suspend fun getUserCount(
        @Header("Authorization") authorization: String,
        @Path("chatroomId") roomId: Long
    ): Response<Long>

    // UserProfile

    /**
     * 取得自己的使用者資訊
     *
     * @param authorization JWT token
     * @return 使用者資訊Map
     */
    @GET("api/UserProfile/")
    suspend fun getUserProfileSelf(@Header("Authorization") authorization: String): Response<UserProfileResponseBody>

    /**
     * 更新自己的使用者資訊
     *
     * @param authorization JWT token
     * @param userProfile 欲更新的物件
     * @return 更新後的使用者資訊Map
     */
    @PATCH("api/UserProfile")
    suspend fun updateUserProfile(
        @Header("Authorization") authorization: String,
        @Body userProfile: UpdateUserProfile
    ): Response<UpdateUserProfileResponseBody>

    /**
     * 取得多個使用者資訊
     *
     * @param authorization JWT token
     * @param idList 目標ID清單(以`,`區隔)
     * @return 使用者資訊Map清單
     */
    @GET("api/UserProfile/{idList}")
    suspend fun getUserProfile(
        @Header("Authorization") authorization: String,
        @Path("idList") idList: String
    ): Response<List<UserProfileResponseBody?>>
}