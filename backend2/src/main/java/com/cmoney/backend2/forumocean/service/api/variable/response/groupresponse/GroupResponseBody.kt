package com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse
import com.cmoney.backend2.forumocean.service.api.variable.response.GroupJoinTypeInfo
import com.google.gson.annotations.SerializedName

/**
 * 社團資訊
 *
 * @property description 社團描述
 * @property id 社團Id
 * @property imageUrl 社團圖片
 * @property isPublic 是否為公開社團（文章是否公開）
 * @property joinType 加入方式
 * @property name 社團名稱
 * @property ownerId 社團創始人
 * @property searchable 是否可搜尋
 * @property memberCount 社團成員數
 */
data class GroupResponseBody(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("isPublic")
    val isPublic: Boolean?,
    @SerializedName("joinType")
    val joinType: GroupJoinTypeInfo?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ownerId")
    val ownerId: Long?,
    @SerializedName("searchable")
    val searchable: Boolean?,
    @SerializedName("memberCount")
    val memberCount : Int?
)