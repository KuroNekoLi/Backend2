package com.cmoney.backend2.portal.service.api.gettarget

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CmPortalTargetWithError(

    /**
     * 台股上市櫃-股票清單
     */
    @SerializedName("TseAndOtc")
    val tseAndOtc: List<String>?,

    /**
     * 發行股期樣本-股票清單
     */
    @SerializedName("StockFutures")
    val stockFutures: List<String>?,

    /**
     * 可當沖樣本~-股票清單
     */
    @SerializedName("DayTrade")
    val dayTrade: List<String>?,

    @SerializedName("IssuanceOfConvertibleBonds")
    val issuanceOfConvertibleBonds: List<String>?,

    @SerializedName("IssuanceOfWarrants")
    val issuanceOfWarrants: List<String>?
): CMoneyError(),
    IWithError<CmPortalTarget> {

    override fun toRealResponse(): CmPortalTarget {
        return CmPortalTarget(
            tseAndOtc, stockFutures, dayTrade, issuanceOfConvertibleBonds, issuanceOfWarrants
        )
    }
}