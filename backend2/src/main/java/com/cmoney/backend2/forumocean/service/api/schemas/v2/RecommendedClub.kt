package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 * 後端所定義的 RecommendedGroupResponse，更名為 RecommendedClub
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class RecommendedClub(
    /** 社團編號 */
    @SerializedName("id")
    val id: Int,

    /** 社團名稱 */
    @SerializedName("name")
    val name: String,

    /** 加入社團是否需審核 */
    @SerializedName("needApproval")
    val needReview: Boolean,

    /** 社團描述 */
    @SerializedName("description")
    val description: String,

    /** 是否為審核狀態 */
    @SerializedName("isPending")
    val underReview: Boolean,

    /** 社團圖片 */
    @SerializedName("imgURL")
    val imgURL: String,

    /** 會員數 */
    @SerializedName("memberCount")
    val memberCount: Long
)
