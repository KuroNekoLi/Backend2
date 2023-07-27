package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.centralizedimage.service.api.upload.GenreAndSubGenre
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CentralizedImageWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: CentralizedImageService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : CentralizedImageWeb {

    override suspend fun upload(
        genreAndSubGenre: GenreAndSubGenre,
        file: File,
        domain: String,
        url: String
    ): Result<UploadResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val kb = file.length() / 1024
            require(kb <= 1024L) {
                "圖片大小限制1MB"
            }

            val requestBody = file.asRequestBody("image/*".toMediaType())
            val part = MultipartBody.Part.createFormData("image", file.name, requestBody)
            service.upload(
                url = url,
                manager.getAccessToken().createAuthorizationBearer(),
                part
            ).checkResponseBody(gson)
        }
    }

}