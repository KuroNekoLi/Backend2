package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import java.io.File

interface CentralizedImageWeb {

    /**
     * 上傳單張圖片
     *
     * @param genre 分類
     * @param subGenre 子分類
     * @param file 圖片檔案
     * @return
     */
    suspend fun upload(genre : String,subGenre : String,file : File) : Result<UploadResponseBody>
}