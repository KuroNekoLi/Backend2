package com.cmoney.backend2.forumocean.service.api.comment.update

interface IUpdateCommentHelper {
    /**
     * 建立修改回文的requestBody
     *
     * @return
     */
    fun create(): UpdateCommentRequestBody
}