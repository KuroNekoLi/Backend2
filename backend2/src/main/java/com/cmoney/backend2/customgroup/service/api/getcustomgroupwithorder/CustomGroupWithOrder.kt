package com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder

import com.google.gson.annotations.SerializedName

data class CustomGroupWithOrder(
    @SerializedName("Group")
    val group: List<SingleGroupWithOrder>?
)