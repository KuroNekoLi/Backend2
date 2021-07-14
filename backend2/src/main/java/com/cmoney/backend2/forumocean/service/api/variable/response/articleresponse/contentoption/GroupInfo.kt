package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * @SerializedName("GroupId")
 * val groupId: Long?,
 * @SerializedName("Position")
 * val position: Any?
 */
interface GroupInfo {
    /**
     * 社團Id
     */
    val groupId: Long?

    /**
     * 有帶此參數，代表以社團名義發文
     */
    val position: Any?
}