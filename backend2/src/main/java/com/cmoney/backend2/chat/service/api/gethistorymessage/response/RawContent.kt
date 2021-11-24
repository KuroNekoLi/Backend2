package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.gson.annotations.SerializedName

/**
 * 集結所有[Content]的訊息解析物件
 *
 * @property originalContentUrl
 * @property previewImageUrl
 * @property width
 * @property height
 * @property text
 * @property packageId
 * @property stickerId
 * @property reason
 * @property messageId
 * @property exception
 * @property originalMessage
 */
data class RawContent(
    @SerializedName(Content.Image.PROPERTY_ORIGINAL_CONTENT_URL)
    val originalContentUrl: String?,
    @SerializedName(Content.Image.PROPERTY_PREVIEW_IMAGE_URL)
    val previewImageUrl: String?,
    @SerializedName(Content.Image.PROPERTY_WIDTH)
    val width: Int?,
    @SerializedName(Content.Image.PROPERTY_HEIGHT)
    val height: Int?,
    @SerializedName(Content.Text.PROPERTY_TEXT)
    val text: String?,
    @SerializedName(Content.Sticker.PROPERTY_PACKAGE_ID)
    val packageId: Int?,
    @SerializedName(Content.Sticker.PROPERTY_STICKER_ID)
    val stickerId: Int?,
    @SerializedName(Content.Reload.PROPERTY_REASON)
    val reason: String?,
    @SerializedName(Content.Delete.PROPERTY_MESSAGE_ID)
    val messageId: Long?,
    @SerializedName(Content.Exception.PROPERTY_EXCEPTION)
    val exception: String?,
    @SerializedName(Content.Exception.PROPERTY_ORIGINAL_MESSAGE)
    val originalMessage: String?
) {
    fun asReal(type: String?): Content? {
        return when (type) {
            Content.Text.TYPE_NAME -> {
                Content.Text(text = text)
            }
            Content.Image.TYPE_NAME -> {
                Content.Image(
                    originalContentUrl = originalContentUrl,
                    previewImageUrl = previewImageUrl,
                    width = width,
                    height = height
                )
            }
            Content.Sticker.TYPE_NAME -> {
                Content.Sticker(packageId = packageId, stickerId = stickerId)
            }
            Content.Empty.TYPE_NAME -> {
                Content.Empty
            }
            Content.Reload.TYPE_NAME -> {
                Content.Reload(originalReason = reason)
            }
            Content.Delete.TYPE_NAME -> {
                Content.Delete(messageId = messageId)
            }
            Content.Exception.TYPE_NAME -> {
                Content.Exception(exception = exception, originalMessage = originalMessage)
            }
            else -> {
                null
            }
        }
    }
}