package com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody

import com.google.gson.annotations.SerializedName
import java.io.File

data class CreateArticleParam(
    @SerializedName("action")
    val action :String = "CreateArticle",

    @SerializedName("appId")
    val appId : Int,

    @SerializedName("guid")
    val guid : String,

    @SerializedName("authtoken")
    val authToken : String,

    @SerializedName("Content")
    val content : String,

    @SerializedName("ArticleType")
    val articleType : Int,

    @SerializedName("AskBouns")
    val askBonus : Int? = null,

    @SerializedName("IsAnonymous")
    val isAnonymous : Boolean? = null,

    @SerializedName("StockId")
    val stockId : String? = null,

    @SerializedName("MentionTags")
    val mentionTag : String? = null,

    @SerializedName("Image")
    val image : List<File>? = null,

    @SerializedName("StockImage")
    val stockImage : String? = null,

    @SerializedName("ClubChannelId")
    val clubChannelId : Long? = null,

    @SerializedName("IsUseClubToPost")
    val isUseClubToPost : Boolean? = null,

    @SerializedName("videoUrl")
    val videoUrl : String? = null
)