package com.cmoney.backend2.mobileocean.service.api.getstockpicture

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetStockPictureResponseWithError(
    @SerializedName("Image")
    val image : String?
): CMoneyError(),
    IWithError<GetStockPictureResponse> {
    override fun toRealResponse(): GetStockPictureResponse {
        return GetStockPictureResponse(image)
    }
}