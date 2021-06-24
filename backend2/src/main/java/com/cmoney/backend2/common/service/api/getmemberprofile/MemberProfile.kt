package com.cmoney.backend2.common.service.api.getmemberprofile

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.ISuccess
import com.google.gson.annotations.SerializedName

/**
 * @param channelId 會員頻道代號
 * @param channelImage 追訊頻道頭像路徑(會員預設頭像)
 * @param email 會員帳號
 * @param email2 通訊用Email
 * @param fBImage FB頭像路徑(有綁FB才有)
 * @param registerTime 註冊時間 UnixTIme(秒)
 */

data class MemberProfile(
    @SerializedName("ChannelId")
    val channelId: Long?,
    @SerializedName("ChannelImage")
    val channelImage: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("Email2")
    val email2: String?,
    @SerializedName("FBImage")
    val fBImage: String?,
    @SerializedName("HasBindedFb")
    val hasBindedFb: Boolean?,
    @SerializedName("ImagePath")
    val imagePath: String?,
    @SerializedName("IsUseFbImage")
    val isUseFbImage: Boolean?,
    @SerializedName("NickName")
    val nickName: String?,
    @SerializedName("RegisterTime")
    val registerTime: Long?,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
) : CMoneyError(), ISuccess {
    override fun getErrorMessage(): String {
        return responseMsg ?: ISuccess.DEFAULT_ERROR_MESSAGE
    }

    override fun getErrorCode(): Int {
        return responseCode ?: ISuccess.DEFAULT_ERROR_CODE
    }

    override fun isResponseSuccess(): Boolean {
        return responseCode == 1
    }
}