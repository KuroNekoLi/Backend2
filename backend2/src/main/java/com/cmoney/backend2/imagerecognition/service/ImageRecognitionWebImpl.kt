package com.cmoney.backend2.imagerecognition.service

import android.util.Base64
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsRequestBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsResponseBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.UsingService
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class ImageRecognitionWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: ImageRecognitionService,
    private val gson: Gson,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider,
) : ImageRecognitionWeb {

    override suspend fun getPictureWords(
        photoBytes: ByteArray,
        expectLength: Int,
        usingService: UsingService,
        domain: String,
        url: String
    ): Result<PictureWordsResponseBody> = withContext(dispatcherProvider.io()) {
        runCatching {
            val requestBody = PictureWordsRequestBody(
                verifyLength = expectLength,
                usingService = usingService.name,
                pictureContent = Base64.encodeToString(photoBytes, Base64.DEFAULT)
            )
            service.getPictureWords(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = requestBody
            ).checkResponseBody(gson)
        }
    }
}