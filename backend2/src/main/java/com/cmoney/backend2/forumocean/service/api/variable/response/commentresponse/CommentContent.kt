package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo
import com.google.gson.annotations.SerializedName

/**
 * 回文內容
 *
 * @property creatorId 發文者
 * @property text 內容
 * @property multiMedia 多媒體資訊
 * @property position 社團位置(官方回文才會帶)
 * @property commentState 回文屬性
 * @property reactionState 會員反應(自己的反應)
 * @property reaction 反應類別 對應 反應總數
 * @property createTime 發回文時間
 * @property modifyTime 修改回文時間
 * @property report 是否有檢舉
 */
data class CommentContent(
    @SerializedName("creatorId")
    val creatorId : Long?,
    @SerializedName("text")
    val text : String?,
    @SerializedName("multiMedia")
    val multiMedia : List<MediaTypeInfo>?,
    @SerializedName("position")
    val position : Any?,
    @SerializedName("commentState")
    val commentState : CommentState?,
    @SerializedName("reactionState")
    val reactionState : Int?,
    @SerializedName("reactions")
    val reaction : Map<String,Int>?,
    @SerializedName("createTime")
    val createTime : Long?,
    @SerializedName("modifyTime")
    val modifyTime : Long?,
    @SerializedName("report")
    val report: Any?
)