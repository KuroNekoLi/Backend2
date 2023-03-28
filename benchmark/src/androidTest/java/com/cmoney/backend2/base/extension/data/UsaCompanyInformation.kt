package com.cmoney.backend2.base.extension.data

import com.google.gson.annotations.SerializedName

data class UsaCompanyInformation(
    @SerializedName("代號")
    val code: String?,
    @SerializedName("名稱")
    val name: String?,
    @SerializedName("公司名稱")
    val companyName: String?,
    @SerializedName("中文名稱")
    val chineseName: String?,
    @SerializedName("中文公司簡介")
    val chineseIntroduction: String?
)
