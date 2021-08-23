package com.cmoney.backend2.sample.servicecase

import android.os.Build
import android.util.Log
import com.cmoney.backend2.mobileocean.service.MobileOceanWeb
import com.cmoney.backend2.mobileocean.service.api.common.article.FilterType
import com.cmoney.backend2.mobileocean.service.api.common.channel.NeedInfo
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleAppendParam
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleStockTag
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.StockTag
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody.SubmitAdviceParam
import com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles.ChannelCategory
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.requestbody.GetPopularStocksParam
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.PictureType
import com.cmoney.backend2.sample.BuildConfig
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject
import java.io.File

class MobileOceanServiceCase : ServiceCase {

    private val mobileOceanWeb by inject<MobileOceanWeb>()

    override suspend fun testAll() {
//        mobileOceanWeb.createArticleToOcean(
//            SubmitAdviceParam("", "Test", BuildConfig.VERSION_NAME, Build.MODEL)
//        ).fold({
//            Log.d(TAG + "ok", "response: ${it.responseCode}")
//        }, {
//            Log.d(TAG + "fail", "response: ${it.message}")
//        })
//        mobileOceanWeb.getPopularStocks(GetPopularStocksParam(5, 0))
//            .fold(
//                {
//                    Log.d(TAG, "getPopularStocks response(needMoreInfo = 0): $it")
//                },
//                {
//                    it.printStackTrace()
//                }
//            )
//        mobileOceanWeb.getPopularStocks(GetPopularStocksParam(5, 1))
//            .fold(
//                {
//                    Log.d(TAG, "getPopularStocks response(needMoreInfo = 1): $it")
//                },
//                {
//                    it.printStackTrace()
//                }
//            )
//        mobileOceanWeb.createArticle(
//            articleContent = "文章內容測試中",
//            appendParam = ArticleAppendParam(
//                mentionTag = listOf(
//                    ArticleStockTag("2330", StockTag.Unchanged),
//                    ArticleStockTag("1234", StockTag.Unchanged)
//                )
//            )
//        )
//            .fold(
//                {
//                    Log.d(TAG, "response: $it")
//                },
//                {
//                    it.printStackTrace()
//                }
//            )
//
//        mobileOceanWeb.isTodayAskedStockTendency("2330").fold(
//            {
//                Log.d(TAG, "response: $it")
//            }, {
//                it.printStackTrace()
//            })
//
//        mobileOceanWeb.addAskStockTendencyLog("2330").fold(
//            {
//                Log.d(TAG, "response: $it")
//            }, {
//                it.printStackTrace()
//            })
//
//        mobileOceanWeb.getAskStockTendencyAmount("2330").fold(
//            {
//                Log.d(TAG, "response: $it")
//            },
//            {
//                it.printStackTrace()
//            }
//        )

        mobileOceanWeb.likeArticle(5719610)
            .fold( // 此articleId為測試機上的文章 如果失敗請換articleId
                {
                    Log.d(TAG, "response: $it")
                }, {
                    it.printStackTrace()
                })

//        mobileOceanWeb.getStockArticleList(
//            stockId = "1101",
//            articleId = 0,
//            replyFetchSize = 3,
//            fetchSize = 3
//        ).fold(
//            {
//                Log.d(TAG, "response: $it")
//            }, {
//                it.printStackTrace()
//            }
//        )
//
//        mobileOceanWeb.getForumLatestArticles(0, 5).fold(
//            {
//                Log.d(TAG, "response: $it")
//            }, {
//                it.printStackTrace()
//            })
//
//        mobileOceanWeb.getForumPopularArticles(0, 5).fold(
//            {
//                Log.d(TAG, "response: $it")
//            }, {
//                it.printStackTrace()
//            })
//
//        mobileOceanWeb.getReplyArticleList(5719610)
//            .fold(// 此articleId為測試機的文章 如果失敗請換articleId
//                {
//                    Log.d(TAG, "response: $it")
//                },
//                {
//                    it.printStackTrace()
//                })
//
//
//        mobileOceanWeb.getStockLatestArticles(
//            listOf("2330", "1101"),
//            FilterType.DISCUSS,
//            0,
//            2
//        ).fold({
//            Log.d(TAG, "response: $it")
//        }, {
//            it.printStackTrace()
//        })
//
//        mobileOceanWeb.getStockPopularArticles(
//            listOf("2330", "1101"),
//            FilterType.DISCUSS,
//            0,
//            3
//        ).fold({
//            Log.d(TAG, "response: $it")
//        }, {
//            it.printStackTrace()
//        })
//
//        mobileOceanWeb.getFollowedChannelArticles(
//            ChannelCategory.PEOPLE,
//            Long.MAX_VALUE,
//            2,
//            true
//        ).logResponse(TAG)
//
//        mobileOceanWeb.getPopularQuestionArticles(
//            listOf("2330", "1234"),
//            0,
//            3
//        ).logResponse(TAG)
//
//        mobileOceanWeb.getLatestQuestionArticles(
//            listOf("2330", "1234"),
//            0,
//            3
//        ).logResponse(TAG)
//
//        val channelId = 1443597L
//
//        mobileOceanWeb.followChannel(
//            channelId
//        ).logResponse(TAG)
//
//        mobileOceanWeb.leaveChannel(
//            channelId
//        ).logResponse(TAG)
//
//        mobileOceanWeb.updateChannelDescription(
//            "我是敘述"
//        ).logResponse(TAG)
//
        val articleId = 6411802L
//        mobileOceanWeb.giveArticleTip(articleId, 1).logResponse(TAG)

        mobileOceanWeb.addInterestedInArticleInfo(articleId, 1).logResponse(TAG)

//        mobileOceanWeb.dislikeArticle(articleId).logResponse(TAG)
//
//        testGuest()
//
//        mobileOceanWeb.getFansChannel(1727623, NeedInfo.All, 0, 20)
//            .logResponse(TAG)
//        mobileOceanWeb.getAttentionChannel(2283480, NeedInfo.All, 0, 10)
//            .logResponse(TAG)
//        mobileOceanWeb.getSingleArticle(102008040).logResponse(TAG)
//
//        mobileOceanWeb.getStockPicture("2330",PictureType.RealTimeTrend).logResponse(TAG)
//
//        mobileOceanWeb.activeFollow().logResponse(TAG)
//
//        mobileOceanWeb.getMemberMasterRanking().logResponse(TAG)
//
//        mobileOceanWeb.getArticleTips(105449804,60,0).logResponse(TAG)
//
//        mobileOceanWeb.getChannelPopularArticles(
//            channelIdList = listOf(
//                4680526,
//                4143914,
//                6212224,
//                1521976,
//                5370965,
//                4348217,
//                349231,
//                6589774,
//                7355110,
//                6372405,
//                4331221,
//                6911664,
//                5741987,
//                6917411,
//                6329836,
//                6790337,
//                1725810,
//                3858422
//            ),
//            skipCount = 0,
//            fetchCount = 30,
//            isIncludeLimitedAskArticle = false
//        ).logResponse(TAG)
//        mobileOceanWeb.getRepliedArticleIds(
//            articleIds = listOf(65513898, 65513897)
//        ).logResponse(TAG)
//
//        mobileOceanWeb.getSingleArticle(6434164).logResponse(TAG)
    }

    suspend fun testCreateArticle(imageFileList: List<File>) {
        mobileOceanWeb.createArticle(
            "MultiPart測試",
            appendParam = ArticleAppendParam(image = imageFileList)
        )
            .fold(
                {
                    Log.d(TAG, "response: $it")
                },
                {
                    it.printStackTrace()
                }
            )
    }

    /**
     * 測試回應文章
     *
     * @param stockId
     * @param articleId
     * @param isUseClubToReplay
     * @param image
     */
    suspend fun testReplayArticle(
        stockId: String,
        articleId: Long,
        isUseClubToReplay: Boolean,
        image: File? = null
    ) {
        mobileOceanWeb.replyArticle(
            stockId,
            articleId,
            "測試",
            isUseClubToReplay,
            image
        ).fold(
            {
                Log.d(TAG, "response: $it")
            }, {
                it.printStackTrace()
            })
    }

    private suspend fun testGuest() {
        mobileOceanWeb.getStockLatestArticles(
            listOf("2330", "1101"), FilterType.DISCUSS,
            0,
            2
        ).fold({
            Log.d(TAG, "訪客 response: $it")
        }, {
            it.printStackTrace()
        })

        mobileOceanWeb.getStockPopularArticles(
            listOf("2330", "1101"), FilterType.DISCUSS,
            0,
            2
        ).fold({
            Log.d(TAG, "訪客 response: $it")
        }, {
            it.printStackTrace()
        })
    }

    companion object {
        private const val TAG = "MobileOcean"
    }
}