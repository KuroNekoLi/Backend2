package com.cmoney.backend2.mobileocean.service.api.getstockpicture

import com.google.gson.annotations.SerializedName

data class GetStockPictureResponse(
    @SerializedName("Image")
    val image : String?
)