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
     * @throws 400參數錯誤, 401身分驗證錯誤, 404不合法的分類或子分類,
     * 411檔案大小為0或小於0, 413檔案太大, 415不支援的檔案格式
     * 500未知的錯誤
     *
     * @param authorization 權限Token
     * @param file 圖片檔案
     *
     */
    @RecordApi
    @Multipart
    @POST
    suspend fun upload(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part
    ): Response<UploadResponseBody>
}