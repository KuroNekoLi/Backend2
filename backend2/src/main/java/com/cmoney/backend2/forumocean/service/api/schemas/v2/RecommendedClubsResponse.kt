package com.cmoney.backend2.forumocean.service.api.schemas.v2

import com.google.gson.annotations.SerializedName

/**
 *  後端所定義的 RecommendedGroupsResponse，更名為 RecommendedClubsResponse
 *
 * API schemas
 * http://outpost.cmoney.net.tw/ForumOcean/swagger/index.html?urls.primaryName=v2
 */
data class RecommendedClubsResponse(
    @SerializedName("recommendedGroups")
    val clubs: List<RecommendedClub>?
)
