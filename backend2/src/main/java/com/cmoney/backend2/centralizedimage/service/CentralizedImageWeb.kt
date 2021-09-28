package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.centralizedimage.service.api.upload.GenreAndSubGenre
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import retrofit2.HttpException
import java.io.File

interface CentralizedImageWeb {

    /**
     * 上傳單張圖片，上傳大小限制為1MB
     *
     * @throws [IllegalArgumentException](大小限制1MB), [HttpException]code: 401、404、411、413、415、500,
     * [EmptyBodyException], [ServerException]
     *
     * @param genreAndSubGenre 分類與子分類
     * @param file 圖片檔案
     * @return
     */
    suspend fun upload(genreAndSubGenre: GenreAndSubGenre, file: File): Result<UploadResponseBody>
}