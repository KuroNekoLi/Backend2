package com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse

import com.google.gson.annotations.SerializedName

enum class CommentState {

    /**
     * 已被刪除
     *
     */
    @SerializedName("deleted")
    Deleted,

    /**
     * 已被加入封鎖清單
     *
     */
    @SerializedName("blocked")
    Blocked
}