package com.cmoney.backend2.customgroup2.service.api.getcustomgroup


import com.cmoney.backend2.customgroup2.service.api.data.Document
import com.google.gson.annotations.SerializedName

/**
 * 自選股文件清單物件
 *
 * @property documents 自選股文件
 */
data class Documents(
    @SerializedName("documents")
    val documents: List<Document>?
)