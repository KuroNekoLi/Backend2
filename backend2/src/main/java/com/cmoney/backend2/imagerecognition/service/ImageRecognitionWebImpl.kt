package com.cmoney.backend2.imagerecognition.service

import android.util.Base64
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsRequestBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsResponseBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.UsingService
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class ImageRecognitionWebImpl(
    override val baseHost: String,
    private val setting: Setting,
    private val service: ImageRecognitionService,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ImageRecognitionWeb {

    override suspend fun getPictureWords(
        host: String,
        photoBytes: ByteArray,
        expectLength: Int,
        usingService: UsingService
    ): Result<PictureWordsResponseBody> = withContext(dispatcherProvider.io()) {
        kotlin.runCatching {
            val requestUrl = "${host}ImageRecognitionService/getpicturewords"
            val requestBody = PictureWordsRequestBody(
                verifyLength = expectLength,
                usingService = usingService.name,
                pictureContent = Base64.encodeToString(photoBytes, Base64.DEFAULT)
            )
            service.getPictureWords(
                url = requestUrl,
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = requestBody
            )
                .checkResponseBody(gson)
        }
    }
}