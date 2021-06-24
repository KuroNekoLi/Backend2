package com.cmoney.backend2.mobileocean.service.api.common.article

import com.google.gson.annotations.SerializedName

/**
 *篩選文章性質類型
 *
 */
enum class FilterType(val value: Int) {
    @SerializedName("1")
    /** 討論 */
    DISCUSS(1),
    @SerializedName("2")
    /** 新聞 */
    NEWS(2),
    @SerializedName("4")
    /** 訊號 */
    SIGNAL(4),
    @SerializedName("7")
    /** 全部 */
    ALL(7)
}