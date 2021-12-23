package com.cmoney.backend2.ocean.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.ocean.service.api.RequestIds
import com.cmoney.backend2.ocean.service.api.answers.AnswersBody
import com.cmoney.backend2.ocean.service.api.answers.AnswersResultWithError
import com.cmoney.backend2.ocean.service.api.changeallbadge.ChangeAllBadgeRequestBody
import com.cmoney.backend2.ocean.service.api.changeallbadge.ChangeWearBadgeRequestBody
import com.cmoney.backend2.ocean.service.api.changecollectarticlestate.ChangeCollectArticleStateRequestBody
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusRequestBody
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.channelquestions.ChannelQuestionsBody
import com.cmoney.backend2.ocean.service.api.channelquestionsactivation.ChannelQuestionsActivationBody
import com.cmoney.backend2.ocean.service.api.channelquestionsactivation.ChannelQuestionsActivationResponseWithError
import com.cmoney.backend2.ocean.service.api.channelwearbadge.ChannelWearBadgeRequestBody
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubRequestBody
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubResponseWithError
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedRequestBody
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.club.*
import com.cmoney.backend2.ocean.service.api.createannouncement.CreateAnnouncementRequest
import com.cmoney.backend2.ocean.service.api.createannouncement.CreateAnnouncementResponseWithError
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubRequestBody
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.deletearticle.DeleteArticleRequestBody
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubRequestBody
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getAnnouncements.GetAnnouncementsWithError
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleLIstRequestBody
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getanswers.GetAnswersBody
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleRequestBody
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getbadgescollection.GetBadgesCollectionRequestBody
import com.cmoney.backend2.ocean.service.api.getblacklist.GetBlackListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getblocklist.GetBlockListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getchannelinfo.GetChannelInfoRequestBody
import com.cmoney.backend2.ocean.service.api.getchannellatestarticle.GetChannelLatestArticleBody
import com.cmoney.backend2.ocean.service.api.getchannellatestarticle.GetChannelLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getchannelquestions.GetChannelQuestionsBody
import com.cmoney.backend2.ocean.service.api.getchannelquestions.GetChannelQuestionsResponseWithError
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsRequestBody
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListRequestBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubRequestBody
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubResponseBody
import com.cmoney.backend2.ocean.service.api.getmanagerlist.GetManagerListRequestBody
import com.cmoney.backend2.ocean.service.api.getmanagerlist.GetManagerListResponseWithError
import com.cmoney.backend2.ocean.service.api.getmasters.GetMastersRequestBody
import com.cmoney.backend2.ocean.service.api.getmasters.GetMastersResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getmemberclubs.GetMemberClubsRequestBody
import com.cmoney.backend2.ocean.service.api.getmemberclubs.GetMemberClubsResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getmemberstatuslist.GetMemberStatusListRequestBody
import com.cmoney.backend2.ocean.service.api.getmemberstatuslist.GetMemberStatusListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getmetricsstats.GetMetricsStatsRequestBody
import com.cmoney.backend2.ocean.service.api.getnotify.GetNotifyRequestBody
import com.cmoney.backend2.ocean.service.api.getnotify.GetNotifyResponseBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.GetRecommendClubsRequestBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.GetRecommendClubsResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getrelevantcomments.GetRelevantCommentsRequest
import com.cmoney.backend2.ocean.service.api.getrelevantcomments.GetRelevantCommentsResponseWithError
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoRequestBody
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoResponseBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleRequestBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesRequestBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstocklatestarticle.GetStockLatestArticleBody
import com.cmoney.backend2.ocean.service.api.getstocklatestarticle.GetStockLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationRequestBody
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListRequestBody
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstockpopulararticle.GetStockPopularArticleBody
import com.cmoney.backend2.ocean.service.api.getstockpopulararticle.GetStockPopularArticleResponse
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesRequestBody
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getunreadbadges.GetUnreadBadgesRequestBody
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountRequestBody
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountResponseBody
import com.cmoney.backend2.ocean.service.api.hadphoneauthentication.HadPhoneAuthResponse
import com.cmoney.backend2.ocean.service.api.hadphoneauthentication.HadPhoneAuthenticationBody
import com.cmoney.backend2.ocean.service.api.impeacharticle.ImpeachArticleBody
import com.cmoney.backend2.ocean.service.api.invite.InviteRequestBody
import com.cmoney.backend2.ocean.service.api.invite.InviteResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.isinwhitelist.IsInCreateArticleWhiteListRequestBody
import com.cmoney.backend2.ocean.service.api.isinwhitelist.IsInCreateArticleWhiteListResponseBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubRequestBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubRequestBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.putonblacklist.PutOnBlackListRequestBody
import com.cmoney.backend2.ocean.service.api.removeannouncement.RemoveAnnouncementRequest
import com.cmoney.backend2.ocean.service.api.removeannouncement.RemoveAnnouncementResponseWithError
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelRequestBody
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelResponseBody
import com.cmoney.backend2.ocean.service.api.setbadgeread.SetBadgeReadRequestBody
import com.cmoney.backend2.ocean.service.api.setevaluation.SetEvaluationRequestBody
import com.cmoney.backend2.ocean.service.api.setreaded.SetReadedRequestBody
import com.cmoney.backend2.ocean.service.api.spinoffblacklist.SpinOffBlackListRequestBody
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionRequestBody
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.uploadchannelimage.UploadChannelImageResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.variable.SuccessResultWithError
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface OceanService {

    /**
     * 全穿或脫掉所有徽章
     */
    @RecordApi
    @POST("OceanService/api/Badge/ChangeAllBadge")
    suspend fun changeAllBadge(
        @Header("Authorization") authorization: String,
        @Body body: ChangeAllBadgeRequestBody
    ): Response<SuccessResultWithError>


    /**
     * 更換裝備的徽章(目前單選由前端控制)
     */
    @RecordApi
    @POST("OceanService/api/Badge/ChangeWearBadge")
    suspend fun changeWearBadge(
        @Header("Authorization") authorization: String,
        @Body body: ChangeWearBadgeRequestBody
    ): Response<SuccessResultWithError>


    /**
     * 取得所有達成徽章及達成條件
     */
    @RecordApi
    @GET("OceanService/api/Badge/GetBadgeAndRequirement")
    suspend fun getBadgeAndRequirement(): Response<JsonElement>


    /**
     * 取得所有徽章持有狀態
     */
    @RecordApi
    @POST("OceanService/api/Badge/GetBadgesCollection")
    suspend fun getBadgesCollection(
        @Header("Authorization") authorization: String,
        @Body body: GetBadgesCollectionRequestBody
    ): Response<JsonElement>


    /**
     * 取得目前所有指標清單
     */
    @RecordApi
    @POST("OceanService/api/Badge/GetMetricsStats")
    suspend fun getMetricsStats(
        @Header("Authorization") authorization: String,
        @Body body: GetMetricsStatsRequestBody
    ): Response<JsonElement>

    /**
     * 取得未讀徽章
     */
    @RecordApi
    @POST("OceanService/api/Badge/GetUnreadBadges")
    suspend fun getUnreadBadges(
        @Header("Authorization") authorization: String,
        @Body body: GetUnreadBadgesRequestBody
    ): Response<JsonElement>

    /**
     * 取得頻道裝備的徽章(多個頻道)
     */
    @RecordApi
    @POST("OceanService/api/Badge/ChannelWearBadge")
    suspend fun channelWearBadge(
        @Header("Authorization") authorization: String,
        @Body body: ChannelWearBadgeRequestBody
    ): Response<JsonElement>

    /**
     * 標記徽章已讀
     */
    @RecordApi
    @POST("OceanService/api/Badge/SetBadgeRead")
    suspend fun setBadgeRead(
        @Header("Authorization") authorization: String,
        @Body body: SetBadgeReadRequestBody
    ): Response<SuccessResultWithError>

    /**
     * 取得加入社團問卷
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Questionnaire/GetChannelQuestions")
    suspend fun getChannelQuestions(
        @Header("Authorization") authorization: String,
        @Body body: GetChannelQuestionsBody
    ): Response<GetChannelQuestionsResponseWithError>

    /**
     * 新增或更新加入社團問卷
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Questionnaire/ChannelQuestions")
    suspend fun channelQuestions(
        @Header("Authorization") authorization: String,
        @Body body: ChannelQuestionsBody
    ): Response<SuccessResultWithError>

    /**
     * 開啟或關閉加入社團問卷
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Questionnaire/ChannelQuestionsActivation")
    suspend fun channelQuestionsActivation(
        @Header("Authorization") authorization: String,
        @Body body: ChannelQuestionsActivationBody
    ): Response<ChannelQuestionsActivationResponseWithError>

    /**
     * 取得問卷回答
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Questionnaire/GetAnswers")
    suspend fun getAnswers(
        @Header("Authorization") authorization: String,
        @Body body: GetAnswersBody
    ): Response<AnswersResultWithError>

    /**
     * 新增問卷回答
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Questionnaire/Answers")
    suspend fun answers(
        @Header("Authorization") authorization: String,
        @Body body: AnswersBody
    ): Response<SuccessResultWithError>

    /**
     * 查詢多用戶是否有手機權限
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/MemberBadge/HadPhoneAuthentication")
    suspend fun hadPhoneAuthentication(
        @Header("Authorization") authorization: String,
        @Body body: HadPhoneAuthenticationBody
    ): Response<HadPhoneAuthResponse>

    /**
     * 取得多檔股票的最新文章清單(可訪客)
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Article/GetStockLatestArticle")
    suspend fun getStockLatestArticle(
        @Header("Authorization") authorization: String,
        @Body body: GetStockLatestArticleBody
    ): Response<GetStockLatestArticleResponse>

    /**
     * 取得多個頻道的最新文章清單(可訪客)
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Article/GetChannelLatestArticle")
    suspend fun getChannelLatestArticle(
        @Header("Authorization") authorization: String,
        @Body body: GetChannelLatestArticleBody
    ): Response<GetChannelLatestArticleResponse>

    /**
     * 設定收藏文章/取消收藏文章
     */
    @RecordApi
    @POST("OceanService/api/SetArticle/ChangeCollectArticleState")
    suspend fun changeCollectArticleState(
        @Header("Authorization") authorization: String,
        @Body body: ChangeCollectArticleStateRequestBody
    ): Response<SuccessResultWithError>

    /**
     * 檢舉文章
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/SetArticle/ImpeachArticle")
    suspend fun impeachArticle(
        @Header("Authorization") authorization: String,
        @Body body: ImpeachArticleBody
    ): Response<SuccessResultWithError>

    /**
     * 刪除文章
     *
     * Note: 限定對此文章有管理權限用的會員使用
     */
    @RecordApi
    @POST("OceanService/api/SetArticle/DeleteArticle")
    suspend fun deleteArticle(
        @Header("Authorization") authorization: String,
        @Body requestBody: DeleteArticleRequestBody
    ): Response<SuccessResultWithError>

    /**
     * 取得多檔股票最熱文章清單(可訪客)
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Article/GetStockPopularArticle")
    suspend fun getStockPopularArticle(
        @Header("Authorization") authorization: String,
        @Body body: GetStockPopularArticleBody
    ): Response<GetStockPopularArticleResponse>

    /**
     * 加入我的黑名單成功or失敗(原本就存在黑名單內視為成功)
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/ChannelBlackList/PutOnBlackList")
    suspend fun putOnBlackList(
        @Header("Authorization") authorization: String,
        @Body body: PutOnBlackListRequestBody
    ): Response<SuccessResultWithError>

    /**
     * 從我的黑名單移除成功or失敗(原本就不存在黑名單內也視為成功)
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/ChannelBlackList/SpinOffBlackList")
    suspend fun spinOffBlackList(
        @Header("Authorization") authorization: String,
        @Body body: SpinOffBlackListRequestBody
    ): Response<SuccessResultWithError>

    /**
     * 取得我的黑名單
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/ChannelBlackList/GetBlackList")
    suspend fun getBlackList(
        @Header("Authorization") authorization: String,
        @Body body: RequestIds
    ): Response<GetBlackListResponseBodyWithError>

    /**
     * 取得黑名單我的人
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/ChannelBlackList/GetBlockList")
    suspend fun getBlockList(
        @Header("Authorization") authorization: String,
        @Body body: RequestIds
    ): Response<GetBlockListResponseBodyWithError>

    /**
     * 取得通知
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Notify/GetNotify")
    suspend fun getNotify(
        @Header("Authorization") authorization: String,
        @Body body: GetNotifyRequestBody
    ): Response<GetNotifyResponseBody>

    /**
     * 取得未讀通知數
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Notify/GetUnreadCount")
    suspend fun getUnreadCount(
        @Header("Authorization") authorization: String,
        @Body body: GetUnreadCountRequestBody
    ): Response<GetUnreadCountResponseBody>

    /**
     * 設定通知已讀
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Notify/SetReaded")
    suspend fun setReaded(
        @Header("Authorization") authorization: String,
        @Body body: SetReadedRequestBody
    ): Response<Void>

    /**
     * 取得指定文章
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Article/GetSingleArticleV2")
    suspend fun getSingleArticle(
        @Header("Authorization") authorization: String,
        @Body body: GetSingleArticleRequestBody
    ): Response<GetSingleArticleResponseBody>

    /**
     * 取得指定文章的回覆
     *
     * @param body
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Comment/GetComments")
    suspend fun getComments(
        @Header("Authorization") authorization: String,
        @Body body: GetCommentsRequestBody
    ): Response<GetCommentsResponseBody>

    /**
     * 取得管理者清單 (幹部、社長)
     */
    @RecordApi
    @POST("OceanService/api/GetClub/GetMangerList")
    suspend fun getManagerList(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetManagerListRequestBody
    ): Response<GetManagerListResponseWithError>

    /**
     * 是否有參加的社團
     */
    @RecordApi
    @POST("OceanService/api/GetClub/CheckHasJoinedClub")
    suspend fun isJoinClub(
        @Header("Authorization") authorization: String,
        @Body requestBody: HasJoinedClubRequestBody
    ): Response<HasJoinedClubResponseWithError>

    /**
     * 取得指定頻道資訊
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/GetChannelInfo")
    suspend fun getOtherChannelInfo(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetChannelInfoRequestBody
    ): Response<ChannelInfo.ChannelBaseInfo>

    /**
     * 取得指定頻道資訊
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/GetChannelInfo")
    suspend fun getRssSignalChannelInfo(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetChannelInfoRequestBody
    ): Response<ChannelInfo.RssSignalChannelInfo>

    /**
     * 取得指定頻道資訊
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/GetChannelInfo")
    suspend fun getMemberChannelInfo(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetChannelInfoRequestBody
    ): Response<ChannelInfo.MemberChannelInfo>

    /**
     * 取得指定頻道資訊
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/GetChannelInfo")
    suspend fun getClubChannelInfo(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetChannelInfoRequestBody
    ): Response<ChannelInfo.ClubChannelInfo>

    /**
     * 搜尋社團 ( 搜尋頻道 )
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/SearchChannel")
    suspend fun searchChannel(
        @Header("Authorization") authorization: String,
        @Body requestBody: SearchChannelRequestBody
    ): Response<SearchChannelResponseBody>

    /**
     * 取得排除加入社團的粉絲清單(已邀請或審核中或黑名單的粉絲也會排除)
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/GetFansListExcludeJoinedClub")
    suspend fun getFanListExcludeJoinedClub(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetFansListExcludeJoinedClubRequestBody
    ): Response<GetFansListExcludeJoinedClubResponseBody>

    /**
     * 取得頻道基本資訊(多筆)
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetChannel/GetSimpleChannelInfo")
    suspend fun getSimpleChannelInfo(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetSimpleChannelInfoRequestBody
    ): Response<GetSimpleChannelInfoResponseBody>

    /**
     * 設定評價
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Evaluation/SetEvaluation")
    suspend fun setEvaluation(
        @Header("Authorization") authorization: String,
        @Body requestBody: SetEvaluationRequestBody
    ): Response<SuccessResultWithError>

    /**
     * 是否給過評價
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Evaluation/CheckHasEvaluated")
    suspend fun checkHasEvaluated(
        @Header("Authorization") authorization: String,
        @Body requestBody: CheckHasEvaluatedRequestBody
    ): Response<CheckHasEvaluatedResponseBodyWithError>

    /**
     * 取得指定用戶的評價清單
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Evaluation/GetEvaluationList")
    suspend fun getEvaluationList(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetEvaluationListRequestBody
    ): Response<GetEvaluationListResponseBodyWithError>

    /**
     * 取得收藏文章清單
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/GetArticleList/GetCollectArticleList")
    suspend fun getCollectArticleList(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetCollectArticleLIstRequestBody
    ): Response<GetCollectArticleListResponseBodyWithError>

    /**
     * 取得使用者是否在白名單中
     *
     * @param requestBody
     */
    @RecordApi
    @POST("OceanService/api/CreateArticleWhiteList/InCreateArticleWhiteList")
    suspend fun isInCreateArticleWhiteList(
        @Header("Authorization") authorization: String,
        @Body requestBody: IsInCreateArticleWhiteListRequestBody
    ): Response<IsInCreateArticleWhiteListResponseBody>

    /**
     * 取得大師排行榜
     *
     * @param requestBody
     */
    @RecordApi
    @POST("OceanService/api/Customization/GetMasters")
    suspend fun getMasters(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetMastersRequestBody
    ): Response<GetMastersResponseBodyWithError>

    /**
     * 取得最新問答文章清單
     *
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Article/GetAskLatestArticle")
    suspend fun getAskLatestArticle(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetAskLatestArticleRequestBody
    ): Response<GetAskLatestArticleResponseBody>

    /**
     * 取得多股大師評價分數
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Customization/GetStockMasterEvaluationList")
    suspend fun getStockMasterEvaluationList(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetStockMasterEvaluationListRequestBody
    ): Response<GetStockMasterEvaluationListResponseBodyWithError>

    /**
     * 取得個股大師評價
     * @param requestBody
     * @return
     */
    @RecordApi
    @POST("OceanService/api/Customization/GetStockMasterEvaluation")
    suspend fun getStockMasterEvaluation(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetStockMasterEvaluationRequestBody
    ): Response<GetStockMasterEvaluationResponseBodyWithError>

    /**
     * 上傳社團頭像
     */
    @RecordApi
    @POST("OceanService/api/File/UploadChannelImage")
    suspend fun uploadChannelImage(
        @Header("Authorization") authorization: String,
        @Body body: MultipartBody
    ): Response<UploadChannelImageResponseBodyWithError>

    /**
     * 創建社團
     */
    @RecordApi
    @POST("OceanService/api/SetClub/CreateClub")
    suspend fun createClub(
        @Header("Authorization") authorization: String,
        @Body requestBody: CreateClubRequestBody
    ): Response<CreateClubResponseBodyWithError>

    /**
     * 關閉社團(解散社團)
     */
    @RecordApi
    @POST("OceanService/api/SetClub/DeleteClub")
    suspend fun deleteClub(
        @Header("Authorization") authorization: String,
        @Body requestBody: DeleteClubRequestBody
    ): Response<DeleteClubResponseBodyWithError>

    /**
     * 離開社團
     */
    @RecordApi
    @POST("OceanService/api/SetClub/LeaveClub")
    suspend fun leaveClub(
        @Header("Authorization") authorization: String,
        @Body requestBody: LeaveClubRequestBody
    ): Response<LeaveClubResponseBodyWithError>

    /**
     * 邀請加入社團
     */
    @RecordApi
    @POST("OceanService/api/SetClub/Invitation")
    suspend fun invite(
        @Header("Authorization") authorization: String,
        @Body requestBody: InviteRequestBody
    ): Response<InviteResponseBodyWithError>

    /**
     * 加入社團
     */
    @RecordApi
    @POST("OceanService/api/SetClub/JoinClub")
    suspend fun joinClub(
        @Header("Authorization") authorization: String,
        @Body requestBody: JoinClubRequestBody
    ): Response<JoinClubResponseBodyWithError>

    /**
     * 取得指定會員的社團清單
     */
    @RecordApi
    @POST("OceanService/api/GetClub/GetMemberClubs")
    suspend fun getMemberClubs(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetMemberClubsRequestBody
    ): Response<GetMemberClubsResponseBodyWithError>

    /**
     * 取得推薦社團
     */
    @RecordApi
    @POST("OceanService/api/GetClub/GetRecommendClubs")
    suspend fun getRecommendClubs(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetRecommendClubsRequestBody
    ): Response<GetRecommendClubsResponseBodyWithError>

    /**
     * 改變會員身分
     */
    @RecordApi
    @POST("OceanService/api/SetClub/ChangeMemberStatus")
    suspend fun changeMemberStatus(
        @Header("Authorization") authorization: String,
        @Body requestBody: ChangeMemberStatusRequestBody
    ): Response<ChangeMemberStatusResponseBodyWithError>

    /**
     * 變更社團描述
     */
    @RecordApi
    @POST("OceanService/api/SetClub/UpdateClubDescription")
    suspend fun updateClubDescription(
        @Header("Authorization") authorization: String,
        @Body requestBody: UpdateClubDescriptionRequestBody
    ): Response<UpdateClubDescriptionResponseBodyWithError>

    /**
     * 取得指定身份的社團成員清單(審核清單,黑名單)
     */
    @RecordApi
    @POST("OceanService/api/GetClub/GetMemberStatusList")
    suspend fun getMemberStatusList(
        @Header("Authorization") authorization: String,
        @Body requestBody: GetMemberStatusListRequestBody
    ): Response<GetMemberStatusListResponseBodyWithError>

    /**
     * 取得公告清單
     */
    @RecordApi
    @POST("OceanService/api/club/{channelId}/readannouncements")
    suspend fun getAnnouncements(
        @Header("Authorization") authorization: String,
        @Path("channelId") channelId: String,
        @Body body: RequestIds
    ): Response<GetAnnouncementsWithError>

    /**
     * 移除公告
     */
    @RecordApi
    @POST("OceanService/api/club/{channelId}/removeannouncement")
    suspend fun removeAnnouncement(
        @Header("Authorization") authorization: String,
        @Path("channelId") channelId: String,
        @Body body: RemoveAnnouncementRequest
    ): Response<RemoveAnnouncementResponseWithError>

    @RecordApi
    @POST("OceanService/api/club/{channelId}/createorupdateannouncement")
    suspend fun createAnnouncement(
        @Header("Authorization") authorization: String,
        @Path("channelId") channelId: String,
        @Body body: CreateAnnouncementRequest
    ): Response<CreateAnnouncementResponseWithError>

    /**
     * 取得與某會員相關的重要留言(現為作者或管理者回覆)
     */
    @RecordApi
    @POST("OceanService/api/Article/GetRelevantComments")
    suspend fun getRelevantComments(
        @Header("Authorization") authorization: String,
        @Body body: GetRelevantCommentsRequest
    ): Response<GetRelevantCommentsResponseWithError>

    /**
     * 取得主題標籤文章(可訪客)
     */
    @RecordApi
    @POST("OceanService/api/Article/GetTopicArticles")
    suspend fun getTopicArticles(
        @Header("Authorization") authorization: String,
        @Body body: GetTopicArticlesRequestBody
    ): Response<GetTopicArticlesResponseBodyWithError>

    /**
     * 取得個股下有主題標籤文章(可訪客)
     */
    @RecordApi
    @POST("OceanService/api/Article/GetStockAndTopicArticles")
    suspend fun getStockAndTopicArticles(
        @Header("Authorization") authorization: String,
        @Body body: GetStockAndTopicArticlesRequestBody
    ): Response<GetStockAndTopicArticlesResponseBodyWithError>

    /**
     * 新增或更新公告
     * @param channelId String
     * @param requestBody CreateOrUpdateAnnouncementRequestBody
     */
    @RecordApi
    @POST("OceanService/api/club/{channelId}/createorupdateannouncement")
    suspend fun createOrUpdateAnnouncement(
        @Header("Authorization") authorization: String,
        @Path("channelId") channelId: Long,
        @Body requestBody: CreateOrUpdateAnnouncementRequestBody
    ): Response<IsCreateOrUpdateSuccessResponseWithError>

    /**
     * 拿到該Channel所有公告
     * @param channelId String
     * @param requestBody ReadAnnouncementsRequestBody
     */
    @RecordApi
    @POST("OceanService/api/club/{channelId}/readannouncements")
    suspend fun readAnnouncement(
        @Header("Authorization") authorization: String,
        @Path("channelId") channelId: Long,
        @Body requestBody: ReadAnnouncementsRequestBody
    ): Response<AnnouncementListResponseWithError>

    /**
     * 刪除公告
     * @param channelId String
     * @param requestBody RemoveAnnouncementRequestBody
     */
    @RecordApi
    @POST("OceanService/api/club/{channelId}/removeannouncement")
    suspend fun removeAnnouncement(
        @Header("Authorization") authorization: String,
        @Path("channelId") channelId: Long,
        @Body requestBody: RemoveAnnouncementRequestBody
    ): Response<IsRemoveAnnouncementSuccessWithError>
}