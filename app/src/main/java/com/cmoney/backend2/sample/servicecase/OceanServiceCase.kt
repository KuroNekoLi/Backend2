package com.cmoney.backend2.sample.servicecase

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import com.cmoney.backend2.ocean.service.OceanWeb
import com.cmoney.backend2.ocean.service.api.changememberstatus.Operation
import com.cmoney.backend2.ocean.service.api.channelquestions.ChannelQuestionnaire
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.backend2.ocean.service.api.getmasters.MasterType
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.RecommendClubsNeedInfo
import com.cmoney.backend2.ocean.service.api.setreaded.NotifyIdAndIsSpecificPair
import com.cmoney.backend2.ocean.service.api.variable.*
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.get
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class OceanServiceCase : ServiceCase {
    private val oceanWeb by inject<OceanWeb>()

    override suspend fun testAll() {
        val context = get<Context>()
        oceanWeb.apply {
            changeAllBadge(false).logResponse(TAG)
            changeWearBadge(false, listOf(1)).logResponse(TAG)
            getBadgeAndRequirement().logResponse(TAG)
            getBadgesCollection().logResponse(TAG)
            getMetricsStats().logResponse(TAG)
            getUnreadBadges().logResponse(TAG)
            channelWearBadge(listOf(1)).logResponse(TAG)
            setBadgeRead(1).logResponse(TAG)
            //正式機社團
            val publicClubChannelId = 4277314L

            //測試機社團
            val clubChannelId = 105551L
            val channelId = publicClubChannelId
            channelQuestions(
                ChannelQuestionnaire(
                    channelId,
                    listOf(QuestionParam("社團問卷問題建立", 0, QuestionType.Openended))
                )
            ).logResponse(TAG)
            getChannelQuestions(channelId).logResponse(TAG)
            channelQuestionsActivation(true, channelId).logResponse(TAG)
            answers(listOf(AnswerParam("社團問卷回答建立", 30068))).logResponse(TAG)
            getAnswers(channelId, listOf(), listOf()).logResponse(TAG)
            hadPhoneAuthentication(
                listOf(
                    3065258,
                    3065253,
                    3065251,
                    110043,
                    109321,
                    195861,
                    105780
                )
            ).logResponse(TAG)
            val articleNeedInfo = ArticleNeedInfo()
            articleNeedInfo.apply {
                addArticleContentAppendInfo()
                addArticleInteractAppendInfo()
                addAuthorInfo()
                addQuestionArticleInfo()
                addArticleViewerInfo()
            }
            getStockLatestArticle(
                0,
                20,
                true,
                listOf("2330"),
                articleNeedInfo,
                FilterType.DISCUSS
            ).logResponse(TAG)

            //測試機的個人頻道
            val personChannelId = 105399L
            getChannelLatestArticle(
                0,
                20,
                true,
                listOf(personChannelId),
                articleNeedInfo
            ).logResponse(TAG)
            impeachArticle(5719645, "測試檢舉").logResponse(TAG)
            getStockPopularArticle(

                0,
                20,
                true,
                listOf("2330", "1234"),
                articleNeedInfo,
                FilterType.DISCUSS
            ).logResponse(TAG)
            putOnBlackList(personChannelId).logResponse(TAG)
            spinOffBlackList(personChannelId).logResponse(TAG)
            getBlackList().logResponse(TAG)
            getBlockList().logResponse(TAG)

            val importantNotifyList = listOf(
                NotificationType.GroupArticleNotify,
                NotificationType.FollowMemberArticle,
                NotificationType.Follow,
                NotificationType.ArticleTip,
                NotificationType.NotifyQuestionAsker,
                NotificationType.BestAnswerForAsker,
                NotificationType.BestAnswerForReplier,
                NotificationType.FollowMemberAskArticle,
                NotificationType.ReplySuggest
            )
            getNotify(false, 0, importantNotifyList, 20).logResponse(TAG)
            getUnreadCount(false, 0, importantNotifyList).logResponse(TAG)
            setReaded(listOf(NotifyIdAndIsSpecificPair(false, 0L))).logResponse(TAG)

            val articleId = 87030622L
            getSingleArticle(articleId, articleNeedInfo).logResponse(TAG)
            getComments(articleId, 0L, 20).logResponse(TAG)

            val needInfo = ChannelNeedInfo<ChannelInfoOption.Other>()
            needInfo.add(ChannelInfoOption.Other.Description)
            getOtherChannelInfo(105956L, needInfo).logResponse(TAG)

            val rssSignalNeedInfo = ChannelNeedInfo<ChannelInfoOption.RssSignal>()
            rssSignalNeedInfo.add(ChannelInfoOption.RssSignal.Image)
            rssSignalNeedInfo.add(ChannelInfoOption.RssSignal.FansCount)
            getRssSignalChannelInfo(105956L, rssSignalNeedInfo).logResponse(TAG)

            val memberNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
            memberNeedInfo.add(ChannelInfoOption.Member.Image)
            memberNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
            getMemberChannelInfo(1443597, memberNeedInfo).logResponse(TAG)

            val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Club>()
            clubNeedInfo.add(ChannelInfoOption.Club.MemberClubInfo)
            clubNeedInfo.add(ChannelInfoOption.Club.ClubInfo)
            getClubChannelInfo(channelId, clubNeedInfo).logResponse(TAG)
            searchChannel(ChannelTypes.MemberClub, 20, "個人").logResponse(TAG)
            getFanListExcludeJoinedClub(
                memberNeedInfo,
                channelId,
                50,
                10
            ).logResponse(TAG)

            getSimpleChannelInfo(listOf(1443597)).logResponse(TAG)
            setEvaluation(3065497, "不錯呦", 4).logResponse(TAG)
            checkHasEvaluated(3065497).logResponse(TAG)

            getEvaluationList(6448552, 10, 0, SortType.LatestToOldest).logResponse(TAG)
            getSingleArticle(102029966, articleNeedInfo).logResponse(TAG)
            changeCollectArticleState(102029966, true).logResponse(TAG)
            changeCollectArticleState(102029966, false).logResponse(TAG)
            getSingleArticle(102029966, articleNeedInfo).logResponse(TAG)

            getCollectArticleList(0, 20, articleNeedInfo, true).logResponse(TAG)
            isInCreateArticleWhiteList().logResponse(TAG)

            getMasters(MasterType.Popularity, 20).logResponse(TAG)

            getAskLatestArticle(0, 20, listOf("2330"), articleNeedInfo).logResponse(TAG)

            getMasters(MasterType.Popularity, 20).logResponse(TAG)

            getAskLatestArticle(0, 20, listOf("2330"), articleNeedInfo).logResponse(TAG)
            getStockMasterEvaluationList(listOf("2330")).logResponse(TAG)
            getStockMasterEvaluation("2330").logResponse(TAG)

            val file = saveImageToInternalStorage(context, android.R.drawable.stat_sys_warning)
            uploadChannelImage(105498, file).logResponse(TAG)
            file.delete()

            createClub("我的新社團", "新的呦", JoinMethod.Join).logResponse(TAG)
            deleteClub(0).logResponse(TAG)
            leaveClub(0).logResponse(TAG)
            isJoinedClub(2850768, Relation.ClubCreated).logResponse(TAG)
            invite(0L, listOf(7869455)).logResponse(TAG)
            joinClub(0L, "").logResponse(TAG)

            val clubNeedInfoV2 = ChannelNeedInfo<ChannelInfoOption.Club>()
            clubNeedInfoV2.add(ChannelInfoOption.Club.MemberClubInfo)
            clubNeedInfoV2.add(ChannelInfoOption.Club.ClubInfo)
            clubNeedInfoV2.add(ChannelInfoOption.Club.Image)
            clubNeedInfoV2.add(ChannelInfoOption.Club.Description)
            clubNeedInfoV2.add(ChannelInfoOption.Club.ViewerClubInfo)
            getMemberClubs(6448552, clubNeedInfoV2, Relation.AllClub).logResponse(TAG)

            val clubNeedInfoV3 = RecommendClubsNeedInfo()
            clubNeedInfoV3.add(RecommendClubsNeedInfo.NeedInfo.ClubInfo)
            clubNeedInfoV3.add(RecommendClubsNeedInfo.NeedInfo.Image)
            clubNeedInfoV3.add(RecommendClubsNeedInfo.NeedInfo.Description)
            clubNeedInfoV3.add(RecommendClubsNeedInfo.NeedInfo.MemberClubInfo)
            getRecommendClubs(10, 0, clubNeedInfoV3).logResponse(TAG)

            changeMemberStatus(4277314, listOf(7869455), Operation.MoveBlackList).logResponse(TAG)

            updateClubDescription(4277314, "社團的功能有些單調123456").logResponse(TAG)

            val clubNeedInfoV4 = ChannelNeedInfo<ChannelInfoOption.Member>()
            clubNeedInfoV4.add(ChannelInfoOption.Member.IsFollowed)
            clubNeedInfoV4.add(ChannelInfoOption.Member.LevelInfo)
            clubNeedInfoV4.add(ChannelInfoOption.Member.DiamondInfo)
            clubNeedInfoV4.add(ChannelInfoOption.Member.Image)
            getMemberStatusList(
                4277314,
                MemberPosition.BlackList,
                50,
                0,
                clubNeedInfoV4
            ).logResponse(TAG)
            changeMemberStatus(
                4277314,
                listOf(7869455),
                Operation.MoveOutBlackList
            ).logResponse(TAG)

            val clubNeedInfoV5 = ChannelNeedInfo<ChannelInfoOption.Member>()
            clubNeedInfoV5.add(ChannelInfoOption.Member.IsFollowed)
            clubNeedInfoV5.add(ChannelInfoOption.Member.LevelInfo)
            clubNeedInfoV5.add(ChannelInfoOption.Member.DiamondInfo)
            clubNeedInfoV5.add(ChannelInfoOption.Member.Image)
            clubNeedInfoV5.add(ChannelInfoOption.Member.FansCount)
            clubNeedInfoV5.add(ChannelInfoOption.Member.LikeCount)
            clubNeedInfoV5.add(ChannelInfoOption.Member.ArticleCount)
            clubNeedInfoV5.add(ChannelInfoOption.Member.AnswersCount)
            clubNeedInfoV5.add(ChannelInfoOption.Member.AttentionCount)
            clubNeedInfoV5.add(ChannelInfoOption.Member.MemberRegisterTime)
            clubNeedInfoV5.add(ChannelInfoOption.Member.EvaluationInfo)
            clubNeedInfoV5.add(ChannelInfoOption.Member.ViewerClubInfo)
            getManagerList(4277314, clubNeedInfoV5).logResponse(TAG)

            createAnnouncement(publicClubChannelId, true, articleId)
            getAnnouncements(publicClubChannelId)
            removeAnnouncements(publicClubChannelId, true, articleId)
            getRelevantComments(articleIds = listOf(articleId), fetch = 10)

            getTopicArticles(
                topic = "研究報告",
                baseArticleId = 9223372036854775807,
                fetchCount = 2,
                articleNeedInfo = ArticleNeedInfo().apply {
                    addAll(ArticleNeedInfo.NeedInfo.values().toList())
                }).logResponse(TAG)

            getStockAndTopicArticles(
                stockId = "2330",
                topic = "研究報告",
                baseArticleId = 9223372036854775807,
                fetchCount = 2,
                articleNeedInfo = ArticleNeedInfo().apply {
                    addAll(ArticleNeedInfo.NeedInfo.values().toList())
                }).logResponse(TAG)

            getStockLatestArticle(
                0,
                20,
                true,
                listOf("AAPL"),
                articleNeedInfo,
                FilterType.ALL
            ).logResponse(TAG)

            createAnnouncement(
                channelId = publicClubChannelId,
                isPinned = false,
                articleId = 102029966
            ).logResponse("TEST_CREATE_ANNOUNCEMENT")
//            testAnnouncement()
        }
    }
    /**
     * 測試社團公告API
     */
     private suspend fun testAnnouncement() {
        
        val announcementTag = "announcementTEST"
        val announceCheck = "announceCheck"
        val channelArticleList = oceanWeb.getChannelLatestArticle(
            baseArticleId = 0,
            fetchCount = 5,
            isIncludeLimitedAskArticle = true,
            articleNeedInfo = ArticleNeedInfo().apply {
                add(ArticleNeedInfo.NeedInfo.Title)
            },
            channelIdList = listOf(
                5083102
            )
        ).apply {
            logResponse(announcementTag)
        }
        channelArticleList.fold(
            onSuccess = {
                var isPinned = false
                it.articles?.forEach {
                    val articleId = it?.articleId ?: 0
                    isPinned= !isPinned
                    oceanWeb.createOrUpdateAnnouncement(
                        clubChannelId = 5083102,
                        articleId = articleId,
                        isPinned = !isPinned
                    ).apply {
                        logResponse(announceCheck)
                    }
                    
                }
                oceanWeb.getAllAnnouncements(
                    clubChannelId = 5083102
                ).apply {
                    logResponse(announceCheck)
                }.fold(
                    onSuccess = {
                        it.list?.forEach {announcement ->
                            val removeResult =  oceanWeb.removeAnnouncements(
                                clubChannelId = 5083102,
                                articleId = announcement.article?.articleId ?: 0,
                                isPinned = announcement.isPinned ?: false
                            ).apply {
                                logResponse(announceCheck)
                            }
                        }
                    },
                    onFailure = {
                    
                    }
                )
            },
            onFailure ={
            }
        )
    }
    /**
     * Method to save an image to internal storage - 為了測試上傳圖片
     */
    private fun saveImageToInternalStorage(context:Context, drawableId: Int): File {
        // Get the image from drawable resource as drawable object
        val drawable = ContextCompat.getDrawable(context, drawableId)
        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap
        // Get the context wrapper instance
        val wrapper = ContextWrapper(context)
        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        // Create a file to save the image
        file = File(file, "${Calendar.getInstance().timeInMillis}.jpg")
        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)
            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            // Flush the stream
            stream.flush()
            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }
        // Return file
        return file
    }

    companion object {
        private const val TAG = "Ocean"
    }
}