package com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder

import com.google.gson.annotations.SerializedName

/**
 * 群組
 *
 * @property createTime 清單建立時間
 * @property customerID 自選股清單擁有者的CustomerId
 * @property docName 清單的名稱
 * @property docNo 該自選股清單的編號
 * @property docType 群組類別
 *
 */
data class SingleGroupWithOrder(
    @SerializedName("CreateTime")
    val createTime: String?,
    @SerializedName("CustomerID")
    val customerID: Int?,
    @SerializedName("DocName")
    val docName: String?,
    @SerializedName("DocNo")
    val docNo: Int?,
    @SerializedName("DocRight")
    val docRight: String?,
    @SerializedName("DocType")
    val docType: Int?,
    @SerializedName("Flag")
    val flag: Int?,
    @SerializedName("Order")
    val order: Int?,
    @SerializedName("SP_CMID")
    val spCmId: String?
)