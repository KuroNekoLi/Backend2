package com.cmoney.backend2.chat.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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

    val manager: GlobalBackend2Manager

    /**
     * 取得會員資訊
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getUserProfile(
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/UserProfile/"
    ): Result<UserProfileResponseBody>

    /**
     * 取得目前使用者對指定對象所有角色
     *
     * @param subject 對象名稱
     * @param subjectId 對象ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return Result<List<String>> 角色們的字串定義
     */
    suspend fun getUserCurrentSubjectRoles(
        subject: Subject,
        subjectId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${subject.subjectName}/Role/Current/${subjectId}"
    ): Result<List<String>>

    /**
     * 取得指定對象所有使用者的角色
     *
     * @param subject 對象名稱
     * @param subjectId 對象ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun lookUpSubjectAllRoles(
        subject: Subject,
        subjectId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${subject.subjectName}/Role/Lookup/${subjectId}"
    ): Result<Map<String, List<String>>>

    /**
     * 更新會員資料
     *
     * @param name 名稱
     * @param imageUrl 圖片Url
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateProfile(
        name: String,
        imageUrl: String,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/UserProfile"
    ): Result<UpdateUserProfileResponseBody>

    /**
     * 綁定個人資訊規則
     *
     * @param target 對象名稱
     * @param userId 對象ID
     * @param ruleSetId 規則ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun bindUserProfileRuleSet(
        target: String,
        userId: Long,
        ruleSetId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${target}/Binding/${userId}/${ruleSetId}"
    ): Result<ResponseBody>

    /**
     * 為目標新增角色
     *
     * @param subject 對象類別
     * @param subjectId 對象ID
     * @param userId 使用者ID
     * @param role 角色
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun addRole(
        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${subject.subjectName}/Role/${subjectId}/${userId}/${role.definitionName}"
    ): Result<ResponseBody>

    /**
     * 移除目標一個角色設定
     *
     * @param subject 對象類別
     * @param subjectId 對象ID
     * @param userId 使用者ID
     * @param role 角色
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun deleteRole(
        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${subject.subjectName}/Role/${subjectId}/${userId}/${role.definitionName}"
    ): Result<ResponseBody>

    /**
     * 取得指定對象所有權限申請
     *
     * @param subject 對象名稱
     * @param subjectId 對象Id
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getSubjectAllAuthorizationRequests(
        subject: Subject,
        subjectId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${subject.subjectName}/Request/${subjectId}"
    ): Result<List<AuthorizationRequestResponseBody>>

    /**
     * 取得對象之綁定規則清單
     *
     * @param subject 對象名稱
     * @param subjectId 對象Id
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getBindingSubjectRuleSets(
        subject: Subject,
        subjectId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${subject.subjectName}/Binding/${subjectId}"
    ): Result<List<RuleSet>>

    /**
     * 取得可用的聊天室
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAvailableRooms(
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom"
    ): Result<List<ChatRoomSettingResponseBody>>

    /**
     * 取得指定的聊天室
     *
     * @param id 聊天室ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTargetRoom(
        id: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${id}"
    ): Result<ChatRoomSettingResponseBody>

    /**
     * 更新聊天室設定
     *
     * ※[Converter]其Gson需要避免serialized null避免傳送null properties給server更新
     *
     * @param id 聊天室ID
     * @param updateProperties 要更新的欄位
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun updateTargetRoom(
        id: Long,
        updateProperties: ChatRoomSettingUpdateProperties,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${id}"
    ): Result<Unit>

    /**
     * 加入聊天室
     *
     * @param roomId 聊天室ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun joinChatRoom(
        roomId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Authorization/${Subject.CHAT_ROOM.subjectName}/Request/${roomId}/${Role.USER.definitionName}"
    ): Result<ResponseBody>


    /**
     * 取得歷史訊息(最新的[fetchCount]資料筆數)
     *
     * @param roomId 聊天室ID
     * @param fetchCount 要取得的資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/Latest"
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(限制為時間上限或下限，最新的[fetchCount]資料筆數)
     *
     * @param roomId 聊天室ID
     * @param fetchCount 要取得的資料筆數
     * @param time 時間
     * @param isStart 是否為下限
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/Latest"
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(時間區段內最新的[fetchCount]資料筆數)
     *
     * @param roomId 聊天室ID
     * @param fetchCount 要取得的資料筆數
     * @param startTime 時間區段上限
     * @param endTime 時間區段下限
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/Latest"
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(最早的[fetchCount]資料筆數)
     *
     * @param roomId 聊天室ID
     * @param fetchCount 要取得的資料筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 歷史訊息
     */
    suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/Oldest"
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(限制為時間上限或下限，最早的[fetchCount]資料筆數)
     *
     * @param roomId 聊天室ID
     * @param fetchCount 要取得的資料筆數
     * @param time 時間
     * @param isStart 是否為下限
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 歷史訊息
     */
    suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/Oldest"
    ): Result<List<Message>>

    /**
     * 取得歷史訊息(時間區段內最早的[fetchCount]資料筆數)
     *
     * @param roomId 聊天室ID
     * @param fetchCount 要取得的資料筆數
     * @param startTime 時間區段上限
     * @param endTime 時間區段下限
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 歷史訊息
     */
    suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/Oldest"
    ): Result<List<Message>>

    /**
     * 根據ID取得目標訊息
     *
     * @param id 訊息ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 目標訊息
     */
    suspend fun getMessageById(
        id: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/Message/${id}"
    ): Result<Message>

    /**
     * 刪除訊息
     *
     * @param roomId 聊天室ID
     * @param id 訊息ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun deleteMessage(
        roomId: Long,
        id: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Message/${id}"
    ): Result<ResponseBody>

    /**
     * 上傳圖片
     *
     * @param photoFile 圖片檔案
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun uploadImage(
        photoFile: File,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Image/Upload"
    ): Result<ResponseBody>

    /**
     * 取得所有聊天室使用者編號清單
     *
     * @param chatRoomId 聊天室ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAllUser(
        chatRoomId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${chatRoomId}/Statistic/User"
    ): Result<List<Long>>

    /**
     * 取得聊天室上線的人數
     *
     * @param chatRoomId 聊天室ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getOnlineUserCount(
        chatRoomId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${chatRoomId}/Statistic/OnlineUserCount"
    ): Result<Long>

    /**
     * 取得目標使用者設定
     *
     * @param userId 使用者ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getTargetUserProfile(
        vararg userId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/UserProfile/${userId.joinToString(",")}"
    ): Result<List<UserProfileResponseBody>>

    /**
     * 檢舉某人
     *
     * @param chatRoomId 在哪個聊天室
     * @param userId 被檢舉人的Id
     * @param messageId 被檢舉訊息Id
     * @param description 被檢舉的理由
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun reportSomeone(
        chatRoomId: Long,
        userId: Long,
        messageId: Long,
        description: String,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${chatRoomId}/Report"
    ): Result<ResponseBody>

    /**
     * 取得指定聊天室全部被檢舉人清單
     *
     * @param chatRoomId 聊天室ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAllReport(
        chatRoomId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${chatRoomId}/Report"
    ): Result<List<ReportInfo>>

    /**
     * 刪除一筆檢舉
     *
     * @param roomId 聊天室ID
     * @param reportId 檢舉ID
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun deleteReport(
        roomId: Long,
        reportId: Long,
        domain: String = manager.getChatRoomSettingAdapter().getDomain(),
        url: String = "${domain}api/Chatroom/${roomId}/Report"
    ): Result<ResponseBody>
}