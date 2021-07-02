package com.cmoney.backend2.image.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.image.service.api.upload.UploadResponseBody
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {

    /**
     * 上傳單張圖片
     *
     * @param authorization
     * @param file 圖片檔案
     * @return
     */
    @RecordApi(isLogRequestBody = false)
    @Multipart
    @POST("image/v1/upload")
    suspend fun upload(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part
    ): Response<UploadResponseBody>
}