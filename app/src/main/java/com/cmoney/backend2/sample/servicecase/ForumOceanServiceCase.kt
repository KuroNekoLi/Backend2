package com.cmoney.backend2.sample.servicecase

import android.util.Log
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.forumocean.service.ForumOceanWeb
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.BullOrBear
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.CommodityTag
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.StockTypeInfo
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.definechannelnamebuilder.CommodityType
import com.cmoney.backend2.forumocean.service.api.channel.channelname.definechannelnamebuilder.DefineChannelName
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.report.create.ReasonType
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupJoinType
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.Type
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.sample.servicecase.data.AccountSettingInfo.Companion.changeUser
import com.cmoney.backend2.sample.servicecase.data.MultiAccountLoginManager
import org.koin.core.inject
import retrofit2.HttpException

class ForumOceanServiceCase : ServiceCase {

    companion object {
        private const val TAG = "ForumOceanServiceCase"
    }

    private val forumOceanWeb by inject<ForumOceanWeb>()

    private val setting by inject<Setting>(BACKEND2_SETTING)

    override suspend fun testAll() {
        forumOceanWeb.apply {

            getMemberStatistics(listOf(setting.identityToken.getMemberId().toLong(),35)).logResponse(TAG)

            val articleId = createArticle(
                Content.Article.General(
                    text = "測試發文設計",
                    multiMedia = listOf(
                        MediaType(
                            Type.IMAGE,
                            "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                        )
                    ),
                    commodityTags = listOf(
                        CommodityTag("1234", BullOrBear.Bear,
                            StockTypeInfo.Stock)
                    ),
                    voteOptions = null,
                    voteMinutes = null,
                    topics = listOf(
                        "測測測測測測"
                    )
                )
            ).getOrNull()?.articleId

            articleId?.apply {
                testArticle(this)
            }

            testQuestion()

            testChannel()

            articleId?.apply {
                testCollection(this)
                testComment(this)
                testInteractive(this)
            }

            articleId?.apply {
                createArticle(
                        Content.Article.General(
                                text = "補一篇文章為了看刪除文章被夾在中間",
                                multiMedia = listOf(
                                        MediaType(
                                                Type.IMAGE,
                                                "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                                        )
                                ),
                                commodityTags = listOf(
                                    CommodityTag("1234", BullOrBear.Bear,
                                        StockTypeInfo.Stock)
                                ),
                                voteOptions = null,
                                voteMinutes = null,
                                topics = null
                        )
                ).logResponse(TAG)
                deleteArticle(this)
                getArticle(this).fold(
                        {
                            Log.d(TAG, "response: $it")
                        },
                        {
                            Log.d(TAG,"預期接收到Http Code 404 結果:${(it as HttpException).code()}")
                        }
                )

                getChannelsArticleByWeight(
                        listOf(DefineChannelName.Commodity(CommodityType.Stock.text,"1234")),
                        Long.MAX_VALUE,
                        20
                ).logResponse(TAG)
            }

            testGroup()
            testOfficials()
            testReport()
            testVote()
            testSupport(setting.identityToken.getMemberId().toLong())
        }
    }

    /**
     * 測試社團的相互操作api
     *
     * @param manager
     */
    suspend fun testMultiUser(manager: MultiAccountLoginManager) {
        forumOceanWeb.apply {
            testGroupMember(manager)
            testRelation(manager)
            testGroupArticle(manager)
            testBlockComment(manager)

            val memberIdArray =
                manager.accountList.map { it.identityToken.getMemberId().toLong() }.toLongArray()
            testSupport(*memberIdArray)
        }
    }

    private suspend fun ForumOceanWeb.testShareArticle(articleId: Long) {
        //測試轉推文章
        val sharedArticleId = createArticle(
            Content.Article.Shared(
                text = "轉推文章測試",
                multiMedia = null,
                sharedPostsArticleId = articleId,
                commodityTags = null,
                voteOptions = null,
                voteMinutes = null,
                topics = null
            )
        ).getOrNull()?.articleId

        sharedArticleId?.let {
            getUnknownArticle(it).logResponse(TAG)
            getSharedArticle(it).logResponse(TAG)
            deleteArticle(it).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testInteractive(articleId: Long) {
        createArticleReaction(articleId, ReactionType.LIKE).logResponse(TAG)
        getArticleReactionDetail(articleId, listOf(ReactionType.LIKE), 0,20).logResponse(TAG)
        createArticleReaction(articleId, ReactionType.DISLIKE).logResponse(TAG)
        getArticleReactionDetail(
            articleId,
            listOf(ReactionType.LIKE, ReactionType.DISLIKE),
            0,
            20
        ).logResponse(TAG)
        deleteArticleReaction(articleId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testComment(articleId: Long) {
        var commentId: Long? = null
        createComment(
            articleId,
            null,
            listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            ),
            null
        ).fold(
            {
                commentId = it.commentIndex
                Log.d(TAG, "response: $it")
            },
            {
                it.printStackTrace()
            }
        )
        commentId?.apply {
            getComment(articleId, this, null).logResponse(TAG)
            val updateCommentHelper = UpdateCommentHelper()
            updateCommentHelper.setText("我修改回復了")
            updateCommentHelper.deleteMultiMedia()
            updateComment(articleId, this, updateCommentHelper).logResponse(TAG)
            getComment(articleId, this, 20).logResponse(TAG)
            reactionComment(articleId, 1, ReactionType.LIKE).logResponse(TAG)
            reactionComment(articleId, 1, ReactionType.DISLIKE).logResponse(TAG)
            getReactionDetail(articleId, 1,ReactionType.values().toList(),0,20).logResponse(TAG)
            removeReactionComment(articleId, 1).logResponse(TAG)
            deleteComment(articleId, 1).logResponse(TAG)
            getComment(articleId, this, 20).logResponse(TAG)
        }

        val commentIdList = mutableListOf<Long>()
        createComment(
            articleId,
            null,
            listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            ),
            null
        ).fold(
            {
                val firstCommentId = it.commentIndex
                if (firstCommentId != null) {
                    commentIdList.add(firstCommentId)
                }
                Log.d(TAG, "response: $it")
            },
            {
                it.printStackTrace()
            }
        )
        createComment(
            articleId,
            "第二篇回文",
            listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            ),
            null
        ).logResponse(TAG)
        createComment(
            articleId,
            "第204sadjskdj篇回文",
            listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            ),
            null
        ).fold(
            {
                val thirdCommentId = it.commentIndex
                if (thirdCommentId != null) {
                    commentIdList.add(thirdCommentId)
                }
                Log.d(TAG, "response: $it")
            },
            {
                it.printStackTrace()
            }
        )

        getCommentWithId(articleId, commentIdList).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testCollection(articleId: Long) {
        createCollection(articleId).logResponse(TAG)

        getChannelsArticleByWeight(
            listOf(DefineChannelName.Collection(setting.identityToken.getMemberId().toLong())),
            Long.MAX_VALUE,
            50
        ).logResponse(TAG)

        deleteCollection(articleId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testChannel() {
        getChannelsArticleByWeight(listOf(DefineChannelName.Commodity(CommodityType.Stock.text,"2330")), Long.MAX_VALUE, 50).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testArticle(articleId: Long) {
        testShareArticle(articleId)

        getUnknownArticle(articleId).logResponse(TAG)
        getArticle(articleId).logResponse(TAG)

        val helper = UpdateArticleHelper()
        helper.setText("我修改了文章")
        helper.deleteMultiMedia()
        updateArticle(articleId, helper).logResponse(TAG)

        getArticle(articleId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testQuestion() {
        val questionId = createQuestion(
            Content.Question(
                text = "問答", multiMedia = listOf(), anonymous = Any(), commodityTags = null, topics = null
            )
        ).getOrNull()?.articleId

        questionId?.apply {

            getUnknownArticle(this).logResponse(TAG)
            getQuestionArticle(this).logResponse(TAG)

            deleteArticle(this).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testGroup() {
        val groupId = createGroup("測試用社團名稱").getOrNull()?.groupId
        groupId?.apply {
            getGroup(this).logResponse(TAG)
            getUserOwnGroup(setting.identityToken.getMemberId().toLong(),0,20).logResponse(TAG)
            getMemberManagedGroups(setting.identityToken.getMemberId().toLong(),0,20).logResponse(TAG)
            getMemberBelongGroups(setting.identityToken.getMemberId().toLong(),0,20).logResponse(TAG)
            getMemberJoinAnyGroups(setting.identityToken.getMemberId().toLong()).logResponse(TAG)
            updateGroup(
                this,
                UpdateGroupRequestBody(joinType = GroupJoinType.Invitation)
            ).logResponse(TAG)
            getGroup(this).logResponse(TAG)
            deleteGroup(this)
        }
    }

    private suspend fun ForumOceanWeb.testOfficials() {
        val builder = DefineChannelName.BotCommodity(CommodityType.Stock.text,"2330")
        val botIdList = getChannelsArticleByWeight(listOf(builder), Long.MAX_VALUE, 50).getOrNull()
            ?.mapNotNull { it.articleContent?.botId }?.distinct()
        if (botIdList != null && botIdList.size >= 2) {
            val firstBotId = botIdList[0]
            val secondBotId = botIdList[1]
            val memberId = setting.identityToken.getMemberId().toLong()
            getOfficials(botIdList).logResponse(TAG)

            subscribe(firstBotId).logResponse(TAG)
            subscribe(secondBotId).logResponse(TAG)

            getOfficialSubscribedCount(firstBotId).logResponse(TAG)
            getSubscribedCount(memberId).logResponse(TAG)
            getSubscribed(memberId,0,20).logResponse(TAG)

            unsubscribe(firstBotId).logResponse(TAG)
            unsubscribeAll().logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testGroupMember(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(setting)
        var groupId: Long? = null
        createGroup("測試用社團名稱").logResponse(TAG) {
            groupId = it.groupId
        }

        groupId?.apply {
            user2.changeUser(setting)
            join(this, "測試api用").logResponse(TAG)
            val groupArticleId = createArticle(
                body = Content.Article.Group(
                    text = "我在社團發文",
                    groupId = this,
                    multiMedia = null,
                    commodityTags = null,
                    position = null,
                    voteOptions = null,
                    voteMinutes = null
                )
            ).getOrNull()?.articleId
            leave(this).logResponse(TAG)

            user1.changeUser(setting)

            if (groupArticleId != null) {
                deleteArticle(groupArticleId).logResponse(TAG)
            }

            updateGroup(
                this,
                UpdateGroupRequestBody(joinType = GroupJoinType.Private)
            ).logResponse(TAG)

            user2.changeUser(setting)
            join(this, "測試api用").logResponse(TAG)

            user1.changeUser(setting)
            getMembers(this,0,200,true).logResponse(TAG)
            val needApprovalId = getApprovals(this,0,20).getOrNull()?.firstOrNull()?.memberId
            needApprovalId?.let {
                approval(this, needApprovalId, true).logResponse(TAG)
                changeGroupMemberPosition(
                    this,
                    needApprovalId,
                    GroupPosition.Cadre
                ).logResponse(TAG)
                kick(this, needApprovalId).logResponse(TAG)

                user1.changeUser(setting)
                transferGroup(this, needApprovalId).logResponse(TAG)
                user2.changeUser(setting)
            }

            deleteGroup(this)
        }
    }

    private suspend fun ForumOceanWeb.testRelation(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(setting)
        val user1MemberId = setting.identityToken.getMemberId().toLong()
        user2.changeUser(setting)
        val user2MemberId = setting.identityToken.getMemberId().toLong()

        follow(user1MemberId).logResponse(TAG)
        getFollowingList(user2MemberId,0,10).logResponse(TAG)
        getFollowers(user2MemberId,0,10).logResponse(TAG)
        unfollow(user1MemberId).logResponse(TAG)
        getFollowingList(user2MemberId,0,10).logResponse(TAG)

        block(user1MemberId).logResponse(TAG)
        user1.changeUser(setting)
        block(user2MemberId).logResponse(TAG)
        getBlockers(0,10).logResponse(TAG)
        getBlockingList(0,10).logResponse(TAG)
        unblock(user2MemberId).logResponse(TAG)
        user2.changeUser(setting)
        unblock(user1MemberId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testReport() {
        val articleId = createArticle(
            Content.Article.General(
                text = "需被檢舉文章",
                multiMedia = null,
                commodityTags = null,
                voteOptions = null,
                voteMinutes = null,
                topics = null
            )
        ).getOrNull()?.articleId

        articleId?.also {
            val commentId = createComment(articleId = articleId, text = "需被檢舉的回文", multiMedia = null, position = null).getOrNull()
            createReport(articleId,ReasonType.Fraud,commentId?.commentIndex).logResponse(TAG)
            getComment(articleId,null,null).logResponse(TAG)
        }

        articleId?.apply {
            createReport(articleId, ReasonType.AD,null).logResponse(TAG)
            getArticle(articleId).logResponse(TAG)
            deleteReport(articleId,null).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testVote() {
        val articleId = createArticle(
            Content.Article.General(
                text = "投票文章測試",
                multiMedia = null,
                commodityTags = null,
                voteOptions = listOf("A選項", "B選項"),
                voteMinutes = 5,
                topics = null
            )
        ).getOrNull()?.articleId

        articleId?.apply {
            getArticle(articleId).logResponse(TAG)
            createVote(articleId, 0).logResponse(TAG)
            getCurrentVote(articleId).logResponse(TAG)
            getArticle(articleId).logResponse(TAG)
            deleteArticle(articleId).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testGroupArticle(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(setting)
        val groupId = createGroup("社團文章名稱").getOrNull()?.groupId

        groupId?.apply {
            updateGroup(
                this,
                UpdateGroupRequestBody(joinType = GroupJoinType.Private)
            ).logResponse(TAG)

            var presidentGroupArticleId: Long? = null
            createArticle(
                Content.Article.Group(
                    text = "社長建文章",
                    groupId = groupId,
                    multiMedia = null,
                    commodityTags = null,
                    position = Any(),
                    voteOptions = null,
                    voteMinutes = null
                )
            ).logResponse(TAG) {
                presidentGroupArticleId = it.articleId
            }

            presidentGroupArticleId?.apply {
                pinArticle(this).logResponse(TAG)
                unpinArticle(this).logResponse(TAG)
            }

            user2.changeUser(setting)
            val user2MemberId = setting.identityToken.getMemberId().toLong()
            join(groupId, "測試").logResponse(TAG)

            presidentGroupArticleId?.apply {
                createComment(
                    articleId = this,
                    text = "社團回文應該失敗",
                    multiMedia = null,
                    position = Any()
                ).logResponse(TAG)
            }

            createArticle(
                Content.Article.Group(
                    text = "社團文章應該失敗",
                    groupId = groupId,
                    multiMedia = null,
                    commodityTags = null,
                    position = Any(),
                    voteOptions = null,
                    voteMinutes = null
                )
            ).logResponse(TAG)

            user1.changeUser(setting)

            getApprovals(groupId,0,20).logResponse(TAG)
            approval(groupId, user2MemberId, true).logResponse(TAG)

            changeGroupMemberPosition(
                groupId,
                user2MemberId,
                GroupPosition.Cadre
            ).logResponse(TAG)

            user2.changeUser(setting)
            var groupArticleId: Long? = null
            createArticle(
                Content.Article.Group(
                    text = "社團文章應該成功",
                    groupId = groupId,
                    multiMedia = null,
                    commodityTags = null,
                    position = Any(),
                    voteOptions = null,
                    voteMinutes = null
                )
            ).logResponse(TAG) {
                groupArticleId = it.articleId
            }

            groupArticleId?.apply {
                createComment(
                    articleId = this,
                    text = "社團回文",
                    multiMedia = listOf(),
                    position = Any()
                ).logResponse(TAG)
                getGroupManagerComments(this).logResponse(TAG)
            }

            user1.changeUser(setting)
            deleteGroup(groupId)
        }
    }

    private suspend fun ForumOceanWeb.testSupport(vararg memberId: Long) {

        var channelIdAndMemberMapList: List<ChannelIdAndMemberId>? = null
        getChannelIds(memberId.toList()).logResponse(TAG) {
            channelIdAndMemberMapList = it
        }

        channelIdAndMemberMapList?.apply {
            getMemberIds(mapNotNull { it.channelId }).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testBlockComment(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(setting)
        var articleId : Long? = null
        createArticle(Content.Article.General(
            text = "測試回文被阻擋用主文",
            multiMedia = listOf(),
            commodityTags = listOf(),
            voteOptions = listOf(),
            voteMinutes = null,
            topics = null
        )).logResponse(TAG){
            articleId = it.articleId
        }
        articleId?.apply {
            user2.changeUser(setting)
            val user2MemberId = setting.identityToken.getMemberId().toLong()
            createComment(this,"使用者二的回文", multiMedia = listOf(), position = null).logResponse(TAG)

            user1.changeUser(setting)
            block(user2MemberId).logResponse(TAG)
            getComment(this,null,null).logResponse(TAG)
            unblock(user2MemberId).logResponse(TAG)
            deleteArticle(this)
        }
    }
}
