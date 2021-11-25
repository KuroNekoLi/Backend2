package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.gson.annotations.SerializedName

/**
 * 集結所有可做為回覆訊息的[Content]解析物件
 *
 * @property text
 */
data class ReplyRawContent(
    @SerializedName(Content.Text.PROPERTY_TEXT)
    val text: String?
) {
    fun asReal(type: String?): Content? {
        return when (type) {
            Content.Text.TYPE_NAME -> {
                Content.Text(text = text)
            }
            Content.Empty.TYPE_NAME -> {
                Content.Empty
            }
            else -> {
                null
            }
        }
    }
}