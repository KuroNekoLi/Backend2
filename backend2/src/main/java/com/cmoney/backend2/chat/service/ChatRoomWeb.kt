package com.cmoney.backend2.chat.service

import com.cmoney.backend2.chat.service.api.chatroomsetting.request.ChatRoomSettingUpdateProperties
import com.cmoney.backend2.chat.service.api.chatroomsetting.response.ChatRoomSettingResponseBody
import com.cmoney.backend2.chat.service.api.getallreport.request.ReportInfo
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.Message
import com.cmoney.backend2.chat.service.api.getsubjectallrequests.response.AuthorizationRequestResponseBody
import com.cmoney.backend2.chat.service.api.getuserprofile.response.UserProfileResponseBody
import com.cmoney.backend2.chat.service.api.updateuserprofile.response.UpdateUserProfileResponseBody
import com.cmoney.backend2.chat.service.api.variable.Role
import com.cmoney.backend2.chat.service.api.variable.RuleSet
import com.cmoney.backend2.chat.service.api.variable.Subject
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.File

interface ChatRoomWeb {

    /**
     * 取得會員資訊
     */
    suspend fun getUserProfile(): Result<UserProfileResponseBody>

    /**
     * 取得目前使用者對指定對象所有角色
     *
     * @param subject Subject 對象名稱
     * @param subjectId Long 對象ID
     * @return Result<List<String>> 角色們的字串定義
     */
    suspend fun getUserCurrentSubjectRoles(
        subject: Subject,
        subjectId: Long
    ): Result<List<String>>

    /**
     * 取得指定對象所有使用者的角色
     *
     * @param subject 對象名稱
     * @param subjectId 對象ID
     */
    suspend fun lookUpSubjectAllRoles(
        subject: Subject,
        subjectId: Long
    ): Result<Map<String, List<String>>>

    /**
     * 更新會員資料
     */
    suspend fun updateProfile(

        name: String,
        imageUrl: String
    ): Result<UpdateUserProfileResponseBody>

    /**
     * 綁定個人資訊規則
     */
    suspend fun bindUserProfileRuleSet(

        target: String,
        userId: Long,
        ruleSetId: Long
    ): Result<ResponseBody>

    /**
     * 為目標新增角色
     */
    suspend fun addRole(

        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role
    ): Result<ResponseBody>

    /**
     * 移除目標一個角色設定
     *
     * @param subject 對象類別
     * @param subjectId 對象ID
     * @param userId 使用者ID
     * @param role 角色
     * @return 是否成功
     */
    suspend fun deleteRole(

        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role
    ): Result<ResponseBody>

    /**
     * 取得指定對象所有權限申請
     *
     * @param subject 對象名稱
     * @param subjectId 對象Id
     */
    suspend fun getSubjectAllAuthorizationRequests(
        subject: Subject,
        subjectId: Long
    ): Result<List<AuthorizationRequestResponseBody>>

    /**
     * 取得對象之綁定規則清單
     *
     * @param subject 對象名稱
     * @param subjectId 對象Id
     * @return 對象之綁定規則清單
     */
    suspend fun getBindingSubjectRuleSets(
        subject: Subject,
        subjectId: Long
    ): Result<List<RuleSet>>

    /**
     * 取得可用的聊天室
     */
    suspend fun getAvailableRooms(): Result<List<ChatRoomSettingResponseBody>>

    /**
     * 取得指定的聊天室
     *
     * @param id 聊天室ID
     */
    suspend fun getTargetRoom(id: Long): Result<ChatRoomSettingResponseBody>

    /**
     * 更新聊天室設定
     *
     * ※[Converter]其Gson需要避免serialized null避免傳送null properties給server更新
     *
     * @param id 聊天室ID
     * @param updateProperties 要更新的欄位※
     */
    suspend fun updateTargetRoom(id: Long, updateProperties: ChatRoomSettingUpdateProperties): Result<Unit>

    /**
     * 加入聊天室
     */
    suspend fun joinChatRoom(roomId: Long): Result<ResponseBody>

    /**
     * 取得歷史訊息(最新的[fetchCount]資料筆數)
     */
    suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(限制為時間上限或下限，最新的[fetchCount]資料筆數)
     */
    suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(時間區段內最新的[fetchCount]資料筆數)
     */
    suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(最早的[fetchCount]資料筆數)
     */
    suspend fun getHistoryMessageFromOldest(

        roomId: Long,
        fetchCount: Int
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(限制為時間上限或下限，最早的[fetchCount]資料筆數)
     */
    suspend fun getHistoryMessageFromOldest(

        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(時間區段內最早的[fetchCount]資料筆數)
     */
    suspend fun getHistoryMessageFromOldest(

        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long
    ): Result<List<Message>>

    /**
     * 取得目標訊息 by Id
     *
     * @param id Long 訊息ID
     * @return Result<Message> 目標訊息
     */
    suspend fun getMessageById(id: Long): Result<Message>

    /**
     * 刪除訊息
     *
     * @param roomId Long 聊天室ID
     * @param id Long 訊息ID
     * @return Result<ResponseBody> statusCode 200 success, 非200失敗
     */
    suspend fun deleteMessage(roomId: Long, id: Long): Result<ResponseBody>

    /**
     * 上傳圖片
     */
    suspend fun uploadImage(photoFile: File): Result<ResponseBody>

    /**
     * 取得所有聊天室使用者編號清單
     */
    suspend fun getAllUser(chatRoomId: Long): Result<List<Long>>

    /**
     * 取得聊天室上線的人數
     */
    suspend fun getOnlineUserCount(chatRoomId: Long): Result<Long>

    /**
     * 取得目標使用者設定
     */
    suspend fun getTargetUserProfile(
        vararg userId: Long
    ): Result<List<UserProfileResponseBody>>

    /**
     * 檢舉某人
     *
     * @param chatRoomId 在哪個聊天室
     * @param userId 被檢舉人的Id
     * @param messageId 被檢舉訊息Id
     * @param description 被檢舉的理由
     * @return
     */
    suspend fun reportSomeone(
        chatRoomId: Long,
        userId: Long,
        messageId: Long,
        description: String
    ): Result<ResponseBody>

    /**
     * 取得某聊天室的檢舉清單
     *
     * @param chatRoomId
     * @return
     */
    suspend fun getAllReport(chatRoomId: Long): Result<List<ReportInfo>>

    /**
     * 刪除一筆檢舉
     *
     * @param roomId Long 聊天室ID
     * @param reportId Long 檢舉ID
     * @return Result<ResponseBody> statusCode 200 success, not 200 fail
     */
    suspend fun deleteReport(roomId: Long, reportId: Long): Result<ResponseBody>
}