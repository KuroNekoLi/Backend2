package com.cmoney.backend2.profile.service.api.queryprofile


import com.google.gson.annotations.SerializedName

data class RawMemberProfile(
    @SerializedName("account")
    val account: Account?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("badges")
    val badges: List<Badges>?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("contactEmail")
    val contactEmail: String?,
    @SerializedName("customerId")
    val customerId: Int?,
    @SerializedName("education")
    val education: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("investmentExperience")
    val investmentExperience: String?,
    @SerializedName("investmentProperty")
    val investmentProperty: String?,
    @SerializedName("investmentTools")
    val investmentTools: List<String>?,
    @SerializedName("levelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("pCoin")
    val pCoin: Int?,
    @SerializedName("profession")
    val profession: String?,
    @SerializedName("signupDate")
    val signupDate: String?
)