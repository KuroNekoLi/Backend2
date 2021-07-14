package com.cmoney.backend2.forumocean.service.api.comment.update

import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.google.gson.annotations.SerializedName

class UpdateCommentHelper {

    private val  editComment = EditComment()

    private val deletePropertyList = mutableListOf<DeleteCommentProperty>()

    /**
     * 修改文章內容
     *
     * @param text
     */
    fun setText(text : String){
        editComment.text = text
    }

    /**
     * 修改多媒體資訊
     *
     * @param list
     */
    fun setMultiMedia(list : List<MediaType>){
        editComment.multiMedia = list
        deletePropertyList.removeAll {
            it == DeleteCommentProperty.MultiMedia
        }
    }

    /**
     * 刪除多媒體資訊
     *
     */
    fun deleteMultiMedia(){
        editComment.multiMedia = null
        deletePropertyList.add(DeleteCommentProperty.MultiMedia)
    }

    /**
     * 建立修改回文的requestBody
     *
     * @return
     */
    fun create() : UpdateCommentRequestBody{
        return UpdateCommentRequestBody(editComment,deletePropertyList.map { it.value })
    }

    /**
     * 可編輯回文 為了做為修改回文的output
     *
     * @property text
     * @property multiMedia
     */
    private inner class EditComment(
        @SerializedName("text")
        var text : String? = null,
        @SerializedName("multiMedia")
        var multiMedia : List<MediaType>? = null
    )
}