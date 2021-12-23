package com.cmoney.backend2.imagerecognition.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsRequestBody
import com.cmoney.backend2.imagerecognition.service.api.getpicturewords.PictureWordsResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ImageRecognitionService {

    @RecordApi
    @POST
    suspend fun getPictureWords(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: PictureWordsRequestBody
    ): Response<PictureWordsResponseBody>
}