package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

/**
 * 會員在這個社團的身份
 * 0.沒有加入過社團
 * 1.黑名單
 * 2.離開社團
 * 4.審核中
 * 8.邀請中
 * 24.可以瀏覽(未加入)
 * 32.一般會員(已加入社團)
 * 512.幹部
 * 2048.社長
 *
 */
enum class MemberPosition(val value : Int) {
    /**
     * 沒有加入過社團
     *
     */
    @SerializedName("0")
    NeverJoined(0),
    /**
     * 黑名單
     */
    @SerializedName("1")
    BlackList(1),
    /**
     * 審核中
     */
    @SerializedName("4")
    Reviewing(4),
    /**
     * 可以瀏覽(未加入)
     */
    @SerializedName("24")
    CanBrowse(24),
    /**
     * 一般會員(已加入社團)
     */
    @SerializedName("32")
    Member(32),
    /**
     * 幹部
     */
    @SerializedName("512")
    Cadre(512),
    /**
     * 社長
     */
    @SerializedName("2048")
    President(2048);
}