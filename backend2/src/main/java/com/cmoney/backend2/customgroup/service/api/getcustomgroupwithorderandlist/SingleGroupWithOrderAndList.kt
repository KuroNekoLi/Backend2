package com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist

import com.google.gson.annotations.SerializedName

data class SingleGroupWithOrderAndList(
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
    @SerializedName("ItemList")
    val itemList: List<String>?,
    @SerializedName("Order")
    val order: Int?,
    @SerializedName("SP_CMID")
    val spCmId: String?
)