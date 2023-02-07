package com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory


import com.cmoney.backend2.virtualtrading2.service.api.data.tseotc.inventory.Inventory
import com.google.gson.annotations.SerializedName

/**
 * 取得上市上櫃庫存回應
 *
 * @property content 資料
 *
 */
data class GetAllInventoryResponseBody(
    @SerializedName("data")
    val content: Data?
) {
    /**
     * 資料
     *
     * @property inventoryList 庫存列表
     *
     */
    data class Data(
        @SerializedName("tseOtcPosition")
        val inventoryList: List<Inventory>?
    )
}