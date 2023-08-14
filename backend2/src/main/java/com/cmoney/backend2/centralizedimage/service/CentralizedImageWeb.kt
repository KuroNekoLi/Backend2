package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.centralizedimage.service.api.upload.GenreAndSubGenre
import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import retrofit2.HttpException
import java.io.File

interface CentralizedImageWeb {

    val manager: GlobalBackend2Manager

    /**
     * 上傳單張圖片，上傳大小限制為1MB
     *
     * @throws [IllegalArgumentException](大小限制1MB), [HttpException]code: 401、404、411、413、415、500,
     * [EmptyBodyException], [ServerException]
     *
     * @param genreAndSubGenre 分類與子分類
     * @param file 圖片檔案
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun upload(
        genreAndSubGenre: GenreAndSubGenre,
        file: File,
        domain: String = manager.getCentralizedImageSettingAdapter().getDomain(),
        url: String = "${domain}centralizedImage/v1/upload/${genreAndSubGenre.genre}/${genreAndSubGenre.subgenre}"
    ): Result<UploadResponseBody>
}