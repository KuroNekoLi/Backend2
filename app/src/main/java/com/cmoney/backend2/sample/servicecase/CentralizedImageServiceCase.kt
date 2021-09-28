package com.cmoney.backend2.sample.servicecase

import android.content.Context
import com.cmoney.backend2.centralizedimage.service.CentralizedImageWeb
import com.cmoney.backend2.centralizedimage.service.api.upload.GenreAndSubGenre
import com.cmoney.backend2.sample.extension.logResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.get
import org.koin.core.inject
import java.io.File

/**
 * 圖片上傳服務測試案例
 */
class CentralizedImageServiceCase() : ServiceCase {

    private val imageWebImpl by inject<CentralizedImageWeb>()

    override suspend fun testAll() = withContext(Dispatchers.IO) {
        val context = get<Context>()
        val mapleFile = context.assets.open("maple-leaf.jpeg").use { inputStream ->
            File(context.cacheDir, "tempFile.jpeg").apply {
                outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        // 往測試資料夾上傳
        imageWebImpl.upload(GenreAndSubGenre.ServiceTestSwagger, mapleFile).logResponse(TAG)
        mapleFile.delete()
        Unit
    }

    companion object {
        private const val TAG = "CentralizedImage"
    }
}