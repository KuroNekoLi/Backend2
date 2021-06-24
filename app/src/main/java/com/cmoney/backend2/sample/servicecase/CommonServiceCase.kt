package com.cmoney.backend2.sample.servicecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.cmoney.backend2.common.service.CommonWeb
import com.cmoney.backend2.sample.R
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject
import java.io.File
import java.io.FileOutputStream

class CommonServiceCase(
    private val context: Context
) : ServiceCase {

    private val commonServiceImpl by inject<CommonWeb>()

    override suspend fun testAll() {
        commonServiceImpl.getAppConfig()
            .logResponse(TAG)
        commonServiceImpl.forgotPasswordForEmail("fu_chen@cmoney.com.tw")
            .logResponse(TAG)
        commonServiceImpl.getMemberProfile()
            .logResponse(TAG)
        commonServiceImpl.registerByEmail("qqj36983@cuoly.com", "123456")
            .logResponse(TAG)
        commonServiceImpl.loginReward()
            .logResponse(TAG)
        commonServiceImpl.hasSentLoginRewardToday()
            .logResponse(TAG)
        commonServiceImpl.invocationSerialNumber("6JE-LMK-7SL")
            .logResponse(TAG)
        commonServiceImpl.getAccessToken()
            .logResponse(TAG)
        commonServiceImpl.addDeviceIdentification("")
            .logResponse(TAG)
        commonServiceImpl.getDailyHeadLine(0, 1, 5)
            .logResponse(TAG)
        val result = commonServiceImpl.getStockRssArticlesWithFilterType(
            stockId = "2330",
            baseArticleId = 0,
            condition = "1_2330|台機電,2",
            fromDate = "2021/01/01",
            beforeDays = 365,
            filterType = 1,
            fetchSize = 10
        )
        result.logResponse(TAG)
        result.getOrNull()?.firstOrNull()?.let { stockNews ->
            commonServiceImpl.addStockRssArticleClickCount(
                0,
                stockNews.id?.toLongOrNull() ?: return
            )
                .logResponse(TAG)
        }
        commonServiceImpl.changeNickname( "中二病患者LV1")
            .logResponse(TAG)

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.upload_image)
        val image = File(context.cacheDir, "upload_image.jpg")
        val fos = FileOutputStream(image)
        fos.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        commonServiceImpl.changeUserImage( true, image)
            .fold(
                {
                    Log.d(TAG, "response: $it")
                },
                {
                    it.printStackTrace()
                }
            )
        commonServiceImpl.getMemberBonus().logResponse(TAG)
        commonServiceImpl.hasReceivedCellphoneBindReward().logResponse(TAG)
        commonServiceImpl.updateIsNeedPush(true).logResponse(TAG)
    }

    companion object {
        private const val TAG = "Common"
    }
}