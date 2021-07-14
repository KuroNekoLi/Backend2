package com.cmoney.backend2.ocean.service.api.createclub

import com.google.gson.annotations.SerializedName

/**
 * 加入社團的方法
 */
enum class JoinMethod(val value : Int){
    /**
     * 公開社團(自由參加，不需審核)
     */
    @SerializedName("1")
    Join(1),

    /**
     * 非公開社團(需審核)
     */
    @SerializedName("4")
    JoinUnderReview(4);
}