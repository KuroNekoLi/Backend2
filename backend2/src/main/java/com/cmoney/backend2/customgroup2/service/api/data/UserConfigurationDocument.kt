package com.cmoney.backend2.customgroup2.service.api.data


import com.google.gson.annotations.SerializedName

/**
 * 使用者設定文件
 *
 * @property id 文件編號，預設不可動
 * @property type 文件類型，預設不可動
 * @property documentOrders 自選股文件排序，key為文件編號，value是文件排序的比較子
 */
class UserConfigurationDocument(
    @SerializedName("id")
    val id: String? = ID,
    @SerializedName("type")
    val type: String? = TYPE,
    @SerializedName("documentOrder")
    val documentOrders: Map<String, Int>?
) {
    companion object {
        const val ID = "_configuration"
        const val TYPE = "UserConfiguration"
    }
}