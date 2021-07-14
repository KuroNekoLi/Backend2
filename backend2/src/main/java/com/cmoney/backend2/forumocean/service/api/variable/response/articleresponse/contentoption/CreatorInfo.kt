package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * @SerializedName("AppId")
 * val appId : Int?
 * @SerializedName("CreatorId")
 * val creatorId: Long?
 *
 */
interface CreatorInfo {

    /**
     * 發文平台
     */
    val appId : Int?

    /**
     * 發文者Id
     */
    val creatorId: Long?
}