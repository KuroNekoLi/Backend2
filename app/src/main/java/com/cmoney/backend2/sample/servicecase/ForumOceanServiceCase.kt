package com.cmoney.backend2.sample.servicecase

import android.util.Log
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.forumocean.service.ForumOceanWeb
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.channelname.definechannelnamebuilder.CommodityType
import com.cmoney.backend2.forumocean.service.api.channel.channelname.definechannelnamebuilder.DefineChannelName
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupJoinType
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.BullOrBear
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.CommodityTag
import com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag.StockTypeInfo
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.Type
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.sample.servicecase.data.AccountSettingInfo.Companion.changeUser
import com.cmoney.backend2.sample.servicecase.data.MultiAccountLoginManager
import org.koin.core.component.inject
import retrofit2.HttpException

class ForumOceanServiceCase : ServiceCase {

    companion object {
        private const val TAG = "ForumOceanServiceCase"
    }

    private val forumOceanWeb by inject<ForumOceanWeb>()
    private val globalBackend2Manager by inject<GlobalBackend2Manager>()

    override suspend fun testAll() {
        forumOceanWeb.apply {

            getMemberStatistics(
                listOf(
                    globalBackend2Manager.getIdentityToken().getMemberId().toLong(),
                    35
                )
            ).logResponse(TAG)

            val articleId = createArticle(
                Content.Article.General(
                    title = "測試發文標題",
                    text = "測試發文設計",
                    multiMedia = listOf(
                        MediaType(
                            Type.IMAGE,
                            "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                        )
                    ),
                    commodityTags = listOf(
                        CommodityTag(
                            "1234", BullOrBear.Bear,
                            StockTypeInfo.Stock
                        )
                    ),
                    voteOptions = null,
                    voteMinutes = null,
                    topics = listOf(
                        "測測測測測測"
                    ),
                    openGraph = null
                )
            ).getOrNull()?.articleId

            articleId?.apply {
                testArticle(this)
            }

            testPersonalArticle()

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
                        title = "測試發文標題",
                        text = "補一篇文章為了看刪除文章被夾在中間",
                        multiMedia = listOf(
                            MediaType(
                                Type.IMAGE,
                                "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                            )
                        ),
                        commodityTags = listOf(
                            CommodityTag(
                                "1234", BullOrBear.Bear,
                                StockTypeInfo.Stock
                            )
                        ),
                        voteOptions = null,
                        voteMinutes = null,
                        topics = null,
                        openGraph = null
                    )
                ).logResponse(TAG)
                deleteArticleV2(this.toString())
                getArticleV2(this).fold(
                    {
                        Log.d(TAG, "response: $it")
                    },
                    {
                        Log.d(TAG, "預期接收到Http Code 404 結果:${(it as HttpException).code()}")
                    }
                )

                getChannelsArticleByWeight(
                    listOf(DefineChannelName.Commodity(CommodityType.Stock.text, "1234")),
                    Long.MAX_VALUE,
                    20
                ).logResponse(TAG)
            }

            testGroup()
            testOfficials()
            testReport()
            testVote()
            testSupport(globalBackend2Manager.getIdentityToken().getMemberId().toLong())
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
                topics = null,
                openGraph = null
            )
        ).getOrNull()?.articleId

        sharedArticleId?.let {
            getArticleV2(it).logResponse(TAG)
            deleteArticleV2(it.toString()).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testInteractive(articleId: Long) {
        createReaction(articleId.toString(), ReactionType.LIKE).logResponse(TAG)
        getArticleReactionDetail(articleId, listOf(ReactionType.LIKE), 0, 20).logResponse(TAG)
        createReaction(articleId.toString(), ReactionType.DISLIKE).logResponse(TAG)
        getArticleReactionDetail(
            articleId,
            listOf(ReactionType.LIKE, ReactionType.DISLIKE),
            0,
            20
        ).logResponse(TAG)
        deleteReaction(articleId.toString()).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testComment(articleId: Long) {
        var commentIndex: Long? = null
        createCommentV2(
            id = articleId.toString(),
            text = null,
            multiMedia = listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            )
        ).fold(
            {
                commentIndex = it.commentIndex
                Log.d(TAG, "response: $it")
            },
            {
                it.printStackTrace()
            }
        )
        val commentId = "${articleId}-${commentIndex}"
        commentIndex?.apply {
            getCommentV2(articleId.toString(), this, null).logResponse(TAG)
            val updateCommentHelper = UpdateCommentHelper()
            updateCommentHelper.setText("我修改回復了")
            updateCommentHelper.deleteMultiMedia()
            updateComment(articleId, this, updateCommentHelper).logResponse(TAG)
            getCommentV2(articleId.toString(), this, 20).logResponse(TAG)
            createReaction(commentId, ReactionType.LIKE).logResponse(TAG)
            createReaction(commentId, ReactionType.DISLIKE).logResponse(TAG)
            getReactionDetailV2(commentId, ReactionType.values().toList(), 0, 20).logResponse(TAG)
            deleteReaction(commentId).logResponse(TAG)
            deleteCommentV2(commentId).logResponse(TAG)
            getCommentV2(articleId.toString(), this, 20).logResponse(TAG)
        }

        val commentIdList = mutableListOf<Long>()
        createCommentV2(
            id = articleId.toString(),
            text = null,
            multiMedia = listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            )
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
        createCommentV2(
            id = articleId.toString(),
            text = "第二篇回文",
            multiMedia = listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            )
        ).logResponse(TAG)
        createCommentV2(
            id = articleId.toString(),
            text = "第204sadjskdj篇回文",
            multiMedia = listOf(
                MediaType(
                    Type.IMAGE,
                    "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                )
            )
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

        getCommentsByIndex(articleId.toString(), commentIdList).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testCollection(articleId: Long) {
        createCollection(articleId).logResponse(TAG)

        getChannelsArticleByWeight(
            listOf(DefineChannelName.Collection(globalBackend2Manager.getIdentityToken().getMemberId().toLong())),
            Long.MAX_VALUE,
            50
        ).logResponse(TAG)

        deleteCollection(articleId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testChannel() {
        getChannelsArticleByWeight(
            listOf(
                DefineChannelName.Commodity(
                    CommodityType.Stock.text,
                    "2330"
                )
            ), Long.MAX_VALUE, 50
        ).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testArticle(articleId: Long) {
        testShareArticle(articleId)

        getArticleV2(articleId).logResponse(TAG)

        val helper = UpdateArticleHelper()
        helper.setText("我修改了文章")
        helper.deleteMultiMedia()
        updateArticle(articleId, helper).logResponse(TAG)

        getArticleV2(articleId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testPersonalArticle() {
        val articleId = createPersonalArticle(
            Content.PersonalArticle.Note(
                text = "測試發筆記文",
                commodityTags = listOf(
                    CommodityTag(
                        commodityKey = "1234",
                        bullOrBear = BullOrBear.None,
                        type = StockTypeInfo.Stock
                    )
                ),
                multiMedia = listOf(
                    MediaType(
                        type = Type.IMAGE,
                        url = "https://zh.wikipedia.org/wiki/Android#/media/File:Android_logo_2019_(stacked).svg"
                    )
                ),
                topics = listOf("筆記"),
            )
        )
            .getOrNull()
            ?.articleId

        getChannelsArticleByWeight(
            channelNameBuilderList = listOf(
                DefineChannelName.MemberNote(
                    memberId = globalBackend2Manager.getIdentityToken().getMemberId().toLong(),
                    commodityType = CommodityType.Stock.text,
                    stockId = "1234"
                )
            ),
            weight = Long.MAX_VALUE,
            count = 20
        ).logResponse(TAG)

        articleId?.apply {
            getArticleV2(this).logResponse(TAG)

            val updateArticleHelper = UpdateArticleHelper()
            updateArticleHelper.setText("測試更新筆記文")
            updateArticleHelper.setMultiMedia(
                listOf(
                    MediaType(
                        type = Type.IMAGE,
                        url = "https://zh.wikipedia.org/wiki/Google_Chrome#/media/File:Google_Chrome_icon_(September_2014).svg"
                    )
                )
            )
            updateArticle(
                articleId = articleId,
                updateHelper = updateArticleHelper
            ).logResponse(TAG)

            deleteArticleV2(this.toString()).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testQuestion() {
        val questionId = createQuestion(
            Content.Question(
                text = "問答",
                multiMedia = listOf(),
                anonymous = Any(),
                commodityTags = null,
                topics = null,
                openGraph = null
            )
        ).getOrNull()?.articleId

        questionId?.apply {
            getArticleV2(this).logResponse(TAG)
            deleteArticleV2(this.toString()).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testGroup() {
        val groupId = createGroup("測試用社團名稱").getOrNull()?.groupId
        groupId?.apply {
            getGroup(this).logResponse(TAG)
            getGroupsByPosition(
                globalBackend2Manager.getIdentityToken().getMemberId().toLong(),
                0,
                20,
                listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT)
            ).logResponse(TAG)
            getMemberManagedGroups(globalBackend2Manager.getIdentityToken().getMemberId().toLong(), 0, 20).logResponse(
                TAG
            )
            getMemberBelongGroups(globalBackend2Manager.getIdentityToken().getMemberId().toLong(), 0, 20).logResponse(
                TAG
            )
            getMemberJoinAnyGroups(globalBackend2Manager.getIdentityToken().getMemberId().toLong()).logResponse(TAG)
            updateGroup(
                this,
                UpdateGroupRequestBody(joinType = GroupJoinType.Invitation)
            ).logResponse(TAG)
            getGroup(this).logResponse(TAG)
            deleteGroup(this)
        }
    }

    private suspend fun ForumOceanWeb.testOfficials() {
        val builder = DefineChannelName.BotCommodity(CommodityType.Stock.text, "2330")
        val botIdList = getChannelsArticleByWeight(listOf(builder), Long.MAX_VALUE, 50).getOrNull()
            ?.mapNotNull { it.articleContent?.botId }?.distinct()
        if (botIdList != null && botIdList.size >= 2) {
            val firstBotId = botIdList[0]
            val secondBotId = botIdList[1]
            val memberId = globalBackend2Manager.getIdentityToken().getMemberId().toLong()
            getOfficialsByIds(botIdList).logResponse(TAG)

            subscribe(firstBotId).logResponse(TAG)
            subscribe(secondBotId).logResponse(TAG)

            getOfficialSubscribedCount(firstBotId).logResponse(TAG)
            getSubscribedCount(memberId).logResponse(TAG)
            getSubscribed(memberId, 0, 20).logResponse(TAG)

            unsubscribe(firstBotId).logResponse(TAG)
            unsubscribeAll().logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testGroupMember(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(globalBackend2Manager)
        var groupId: Long? = null
        createGroup("測試用社團名稱").logResponse(TAG) {
            groupId = it.groupId
        }

        groupId?.apply {
            user2.changeUser(globalBackend2Manager)
            join(this, "測試api用").logResponse(TAG)
            val groupArticleId = createArticle(
                body = Content.Article.Group(
                    text = "我在社團發文",
                    groupId = this,
                    multiMedia = null,
                    commodityTags = null,
                    position = null,
                    voteOptions = null,
                    voteMinutes = null,
                    openGraph = null
                )
            ).getOrNull()?.articleId
            leave(this).logResponse(TAG)

            user1.changeUser(globalBackend2Manager)

            if (groupArticleId != null) {
                deleteArticleV2(groupArticleId.toString()).logResponse(TAG)
            }

            updateGroup(
                this,
                UpdateGroupRequestBody(joinType = GroupJoinType.Private)
            ).logResponse(TAG)

            user2.changeUser(globalBackend2Manager)
            join(this, "測試api用").logResponse(TAG)

            user1.changeUser(globalBackend2Manager)
            getMembers(
                this,
                0,
                200,
                listOf(GroupPosition.PRESIDENT, GroupPosition.MANAGEMENT, GroupPosition.NORMAL)
            ).logResponse(TAG)
            val needApprovalId = getApprovals(this, 0, 20).getOrNull()?.firstOrNull()?.memberId
            needApprovalId?.let {
                approval(this, needApprovalId, true).logResponse(TAG)
                changeGroupMemberPosition(
                    this,
                    needApprovalId,
                    GroupPosition.PRESIDENT
                ).logResponse(TAG)
                kick(this, needApprovalId).logResponse(TAG)

                user1.changeUser(globalBackend2Manager)
                transferGroup(this, needApprovalId).logResponse(TAG)
                user2.changeUser(globalBackend2Manager)
            }

            deleteGroup(this)
        }
    }

    private suspend fun ForumOceanWeb.testRelation(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(globalBackend2Manager)
        val user1MemberId = globalBackend2Manager.getIdentityToken().getMemberId().toLong()
        user2.changeUser(globalBackend2Manager)
        val user2MemberId = globalBackend2Manager.getIdentityToken().getMemberId().toLong()

        follow(user1MemberId).logResponse(TAG)
        getFollowingList(user2MemberId, 0, 10).logResponse(TAG)
        getFollowers(user2MemberId, 0, 10).logResponse(TAG)
        unfollow(user1MemberId).logResponse(TAG)
        getFollowingList(user2MemberId, 0, 10).logResponse(TAG)

        block(user1MemberId).logResponse(TAG)
        user1.changeUser(globalBackend2Manager)
        block(user2MemberId).logResponse(TAG)
        getBlockers(0, 10).logResponse(TAG)
        getBlockingList(0, 10).logResponse(TAG)
        unblock(user2MemberId).logResponse(TAG)
        user2.changeUser(globalBackend2Manager)
        unblock(user1MemberId).logResponse(TAG)
    }

    private suspend fun ForumOceanWeb.testReport() {
        val articleId = createArticle(
            Content.Article.General(
                title = "測試發文標題",
                text = "需被檢舉文章",
                multiMedia = null,
                commodityTags = null,
                voteOptions = null,
                voteMinutes = null,
                topics = null,
                openGraph = null
            )
        ).getOrNull()?.articleId

        articleId?.also {
            val commentId = createCommentV2(
                id = articleId.toString(),
                text = "需被檢舉的回文",
                multiMedia = null
            ).getOrNull()
            createReport(articleId, 1, commentId?.commentIndex).logResponse(TAG)
            getCommentV2(articleId.toString(), null, null).logResponse(TAG)
        }

        articleId?.apply {
            createReport(articleId, 1, null).logResponse(TAG)
            getArticleV2(articleId).logResponse(TAG)
            deleteReport(articleId, null).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testVote() {
        val articleId = createArticle(
            Content.Article.General(
                title = "測試發文標題",
                text = "投票文章測試",
                multiMedia = null,
                commodityTags = null,
                voteOptions = listOf("A選項", "B選項"),
                voteMinutes = 5,
                topics = null,
                openGraph = null
            )
        ).getOrNull()?.articleId

        articleId?.apply {
            getArticleV2(articleId).logResponse(TAG)
            createVote(articleId, 0).logResponse(TAG)
            getCurrentVote(articleId).logResponse(TAG)
            getArticleV2(articleId).logResponse(TAG)
            deleteArticleV2(articleId.toString()).logResponse(TAG)
        }
    }

    private suspend fun ForumOceanWeb.testGroupArticle(manager: MultiAccountLoginManager) {
        val user1 = manager.accountList[0]
        val user2 = manager.accountList[1]

        user1.changeUser(globalBackend2Manager)
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
                    voteMinutes = null,
                    openGraph = null
                )
            ).logResponse(TAG) {
                presidentGroupArticleId = it.articleId
            }

            presidentGroupArticleId?.apply {
                pinArticle(this).logResponse(TAG)
                unpinArticle(this).logResponse(TAG)
            }

            user2.changeUser(globalBackend2Manager)
            val user2MemberId = globalBackend2Manager.getIdentityToken().getMemberId().toLong()
            join(groupId, "測試").logResponse(TAG)

            presidentGroupArticleId?.apply {
                createCommentV2(
                    id = this.toString(),
                    text = "社團回文應該失敗",
                    multiMedia = null
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
                    voteMinutes = null,
                    openGraph = null
                )
            ).logResponse(TAG)

            user1.changeUser(globalBackend2Manager)

            getApprovals(groupId, 0, 20).logResponse(TAG)
            approval(groupId, user2MemberId, true).logResponse(TAG)

            changeGroupMemberPosition(
                groupId,
                user2MemberId,
                GroupPosition.PRESIDENT
            ).logResponse(TAG)

            user2.changeUser(globalBackend2Manager)
            var groupArticleId: Long? = null
            createArticle(
                Content.Article.Group(
                    text = "社團文章應該成功",
                    groupId = groupId,
                    multiMedia = null,
                    commodityTags = null,
                    position = Any(),
                    voteOptions = null,
                    voteMinutes = null,
                    openGraph = null
                )
            ).logResponse(TAG) {
                groupArticleId = it.articleId
            }

            groupArticleId?.apply {
                createCommentV2(
                    id = this.toString(),
                    text = "社團回文",
                    multiMedia = listOf()
                ).logResponse(TAG)
                getGroupManagerComments(this).logResponse(TAG)
            }

            user1.changeUser(globalBackend2Manager)
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

        user1.changeUser(globalBackend2Manager)
        var articleId: Long? = null
        createArticle(
            Content.Article.General(
                title = "測試發文標題",
                text = "測試回文被阻擋用主文",
                multiMedia = listOf(),
                commodityTags = listOf(),
                voteOptions = listOf(),
                voteMinutes = null,
                topics = null,
                openGraph = null
            )
        ).logResponse(TAG) {
            articleId = it.articleId
        }
        articleId?.apply {
            user2.changeUser(globalBackend2Manager)
            val user2MemberId = globalBackend2Manager.getIdentityToken().getMemberId().toLong()
            createCommentV2(this.toString(), "使用者二的回文", multiMedia = listOf()).logResponse(TAG)

            user1.changeUser(globalBackend2Manager)
            block(user2MemberId).logResponse(TAG)
            getCommentV2(this.toString(), null, null).logResponse(TAG)
            unblock(user2MemberId).logResponse(TAG)
            deleteArticleV2(this.toString())
        }
    }
}
