package com.cmoney.backend2.videochannel.service.api

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class YoutubeVideo(
    @SerializedName("title")
    val title : String?,
    @SerializedName("description")
    val description : String?,
    @SerializedName("type")
    val type : String?,
    @SerializedName("youtubeVideoId")
    val youtubeVideoId : String?,
    @SerializedName("thumbNail")
    val thumbNail : String?,
    @SerializedName("pubDate")
    val pubDate : Long?,
    @SerializedName("seconds")
    val seconds : Int?,
    @SerializedName("videoChannel")
    val videoChannel : VideoChannel?,
    @SerializedName("views")
    val views : Int?,
    @SerializedName("categories")
    val categories : List<Categories>?
) : CMoneyError() {
    data class VideoChannel(
        @SerializedName("id")
        val id : String?,
        @SerializedName("description")
        val description : String?,
        @SerializedName("name")
        val name : String?,
        @SerializedName("thumbNail")
        val thumbNail : String?,
        @SerializedName("isFollowed")
        val isFollowed : Boolean?
    )

    data class Categories(
        @SerializedName("id")
        val id : Int?,
        @SerializedName("name")
        val name : String?,
        @SerializedName("image")
        val image : String?
    )
}