package com.cmoney.backend2.forumocean.service.api.article.update

import com.cmoney.backend2.forumocean.service.api.variable.request.mediatype.MediaType
import com.google.gson.annotations.SerializedName

class UpdateArticleHelper : IUpdateArticleHelper {

    private var editArticle: EditArticle = EditArticle()

    private val deletePropertyList = mutableListOf<DeleteArticleProperty>()

    /**
     * 修改文章標題
     *
     * @param title
     */
    fun setTitle(title: String) {
        editArticle.title = title
    }

    /**
     * 修改文章內容
     *
     * @param text
     */
    fun setText(text: String) {
        editArticle.text = text
    }

    /**
     * 修改多媒體資訊
     *
     * @param list
     */
    fun setMultiMedia(list: List<MediaType>) {
        editArticle.multiMedia = list
        deletePropertyList.removeAll {
            it == DeleteArticleProperty.MultiMedia
        }
    }

    /**
     * 刪除多媒體資訊
     *
     */
    fun deleteMultiMedia() {
        editArticle.multiMedia = null
        deletePropertyList.add(DeleteArticleProperty.MultiMedia)
    }

    /**
     * 刪除標籤
     *
     */
    fun deleteTopics() {
        deletePropertyList.add(DeleteArticleProperty.Topics)
    }

    /**
     * 建立修改文章的requestBody
     *
     * @return
     */
    override fun create(): UpdateArticleRequestBody {
        return UpdateArticleRequestBody(editArticle, deletePropertyList.map { it.value })
    }

    /**
     * 可編輯文章 為了做為修改文章的output
     *
     * @property title 標題
     * @property text 內文
     * @property multiMedia 多媒體
     */
    private inner class EditArticle(
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("text")
        var text: String? = null,
        @SerializedName("multiMedia")
        var multiMedia: List<MediaType>? = null
    )
}