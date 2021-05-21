package com.cmoney.backend2.customgroup.service.api.getcustomgroupandlist

import com.google.gson.annotations.SerializedName

data class CustomGroupAndList(
    @SerializedName("Group")
    val group: List<SingleGroupAndList>?
)