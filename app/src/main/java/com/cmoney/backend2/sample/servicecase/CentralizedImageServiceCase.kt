package com.cmoney.backend2.sample.servicecase

import android.content.Context
import com.cmoney.backend2.centralizedimage.service.CentralizedImageWeb
import com.cmoney.backend2.sample.extension.logResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.get
import org.koin.core.inject
import java.io.File

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
        imageWebImpl.upload(
            CentralizedImageWeb.Destination.ATTACHMENT_POST,
            mapleFile
        ).logResponse(TAG)
        mapleFile.delete()
        Unit
    }

    companion object {
        private const val TAG = "RealTime"
    }
}