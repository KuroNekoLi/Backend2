package com.cmoney.backend2.image.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.image.service.api.upload.UploadResponseBody
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ImageWebImpl(
    private val service: ImageService,
    private val setting: Setting,
    private val jsonParser: Gson,
    private val ioDispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : ImageWeb {

    override suspend fun upload(file: File): Result<UploadResponseBody> =
        withContext(ioDispatcher.io()) {
            kotlin.runCatching {
                val kb = file.length() / 1024
                if (kb > 1024){
                    //圖片限制1MB
                    error("圖片大小限制1MB")
                }

                val requestBody = file.asRequestBody("image/*".toMediaType())
                val part = MultipartBody.Part.createFormData("image", file.name, requestBody)
                service.upload(
                    setting.accessToken.createAuthorizationBearer(),
                    part
                ).checkResponseBody(jsonParser)
            }
        }


}