package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo

/**
 * 請加入以下欄位
 * @SerializedName("MultiMedia")
 * val multiMedia: List<MediaType>?
 */
interface MultiMediaInfo {
    /**
     * 多媒體資訊
     */
    val multiMedia: List<MediaTypeInfo>?
}