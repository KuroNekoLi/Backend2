package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CentralizedImageService {

    /**
     * 上傳單張圖片
     *
     * @param authorization
     * @param genre 分類
     * @param subGenre 子分類
     * @param file 圖片檔案
     * @return
     */
    @RecordApi
    @Multipart
    @POST("centralizedImage/v1/upload/{destination}")
    suspend fun upload(
        @Header("Authorization") authorization: String,
        @Path(value = "destination", encoded = true) destination: String,
        @Part file: MultipartBody.Part
    ): Response<UploadResponseBody>
}