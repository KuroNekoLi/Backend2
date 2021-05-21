package com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist

import com.google.gson.annotations.SerializedName

data class CustomGroupWithOrderAndList(

    /**
     * 自選股清單
     */
    @SerializedName("Group")
    val group: List<SingleGroupWithOrderAndList>?
)