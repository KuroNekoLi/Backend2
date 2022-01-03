package com.cmoney.backend2.forumocean.service.api.relationship.getrelationshipwithme

import com.google.gson.annotations.SerializedName

/**
 * 取得指定的會員清單與我目前的關係
 *
 * @property memberId 會員Id
 * @property relationshipType 與我目前的關係
 * @see RelationShip
 */
data class RelationshipWithMe(
    @SerializedName("memberId")
    val memberId : Long?,
    @SerializedName("relationshipType")
    val relationshipType : Int?
)