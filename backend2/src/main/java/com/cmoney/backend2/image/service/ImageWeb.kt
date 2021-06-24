package com.cmoney.backend2.image.service

import com.cmoney.backend2.image.service.api.upload.UploadResponseBody
import java.io.File

interface ImageWeb {
    suspend fun upload(file : File) : Result<UploadResponseBody>
}