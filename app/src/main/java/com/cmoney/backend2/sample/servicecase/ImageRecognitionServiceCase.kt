package com.cmoney.backend2.sample.servicecase

import android.content.Context
import com.cmoney.backend2.imagerecognition.di.BACKEND2_IMAGE_RECOGNITION_DEBUG
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.StringQualifier
import java.io.ByteArrayOutputStream

class ImageRecognitionServiceCase(qualifier: StringQualifier = BACKEND2_IMAGE_RECOGNITION_DEBUG) : ServiceCase {

    private val web by inject<ImageRecognitionWeb>(qualifier)

    override suspend fun testAll() {
        val context = get<Context>()
        val outputByteArray = ByteArrayOutputStream().use { outputStream ->
            context.assets.open("Captcha_01.png").use { inputStream ->
                inputStream.copyTo(outputStream)
            }
            outputStream.toByteArray()
        }
        val result = web.getPictureWords(
            photoBytes = outputByteArray,
        )
        result.logResponse(TAG)
    }

    companion object {
        private const val TAG = "ImageRecognition"
    }
}