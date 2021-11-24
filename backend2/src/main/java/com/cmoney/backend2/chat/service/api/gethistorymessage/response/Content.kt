package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.gson.annotations.SerializedName
import org.json.JSONException
import org.json.JSONObject

/**
 * 聊天室內容類型
 */
sealed class Content {

    /**
     * Type辨識名稱
     */
    abstract val typeName: String

    /**
     * 文字訊息
     */
    data class Text(
        @SerializedName(PROPERTY_TEXT)
        val text: String
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            private const val PROPERTY_TEXT = "text"
            const val TYPE_NAME = "Text"

            fun fromJson(json: JSONObject): Text? {
                return try {
                    val text = json.getString(PROPERTY_TEXT)
                    Text(text = text)
                } catch (exception: JSONException) {
                    null
                }
            }
        }
    }

    /**
     * 圖片訊息
     */
    data class Image(
        @SerializedName(PROPERTY_ORIGINAL_CONTENT_URL)
        val originalContentUrl: String,
        @SerializedName(PROPERTY_PREVIEW_IMAGE_URL)
        val previewImageUrl: String,
        @SerializedName(PROPERTY_WIDTH)
        val width: Int,
        @SerializedName(PROPERTY_HEIGHT)
        val height: Int
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            private const val PROPERTY_ORIGINAL_CONTENT_URL = "originalContentUrl"
            private const val PROPERTY_PREVIEW_IMAGE_URL = "previewImageUrl"
            private const val PROPERTY_WIDTH = "width"
            private const val PROPERTY_HEIGHT = "height"
            const val TYPE_NAME = "Image"

            fun fromJson(json: JSONObject): Image? {
                return try {
                    val originUrl = json.getString(PROPERTY_ORIGINAL_CONTENT_URL)
                    val previewUrl = json.getString(PROPERTY_PREVIEW_IMAGE_URL)
                    val width = json.getInt(PROPERTY_WIDTH)
                    val height = json.getInt(PROPERTY_HEIGHT)
                    Image(
                        originalContentUrl = originUrl,
                        previewImageUrl = previewUrl,
                        width = width,
                        height = height
                    )
                } catch (exception: JSONException) {
                    null
                }
            }
        }
    }

    /**
     * 貼圖訊息
     */
    data class Sticker(
        @SerializedName(PROPERTY_PACKAGE_ID)
        val packageId: Int,
        @SerializedName(PROPERTY_STICKER_ID)
        val stickerId: Int
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            private const val PROPERTY_PACKAGE_ID = "packageId"
            private const val PROPERTY_STICKER_ID = "stickerId"
            const val TYPE_NAME: String = "Sticker"

            fun fromJson(json: JSONObject): Sticker? {
                return try {
                    val packageId = json.getInt(PROPERTY_PACKAGE_ID)
                    val stickerId = json.getInt(PROPERTY_STICKER_ID)
                    Sticker(packageId = packageId, stickerId = stickerId)
                } catch (exception: JSONException) {
                    null
                }
            }
        }
    }

    /**
     * 空白訊息
     */
    object Empty : Content() {
        const val TYPE_NAME: String = "Empty"
        override val typeName: String = TYPE_NAME
    }

    /**
     * 重新讀取通知
     */
    data class Reload(
        @SerializedName(PROPERTY_REASON)
        val originalReason: String
    ) : Content() {

        override val typeName: String = TYPE_NAME

        fun getReason(): Reason? {
            for (reason in Reason.values()) {
                if (originalReason == reason.name) {
                    return reason
                }
            }
            return null
        }

        enum class Reason {

            /**
             * 目前使用者角色在該聊天室更新, 需要重新讀取角色
             */
            Role,

            /**
             * 聊天室設定更新, 需要重新讀取聊天室設定
             */
            Configuration,

            /**
             * 聊天室所使用的規則有所更改
             */
            RuleSet,

            /**
             * 聊天室綁定新的規則或是解除規則的綁定
             */
            RuleBinding
        }

        companion object {
            private const val PROPERTY_REASON = "reason"
            const val TYPE_NAME = "@Reload"

            fun fromJson(json: JSONObject): Reload? {
                val reason = try {
                    json.getString(PROPERTY_REASON)
                } catch (exception: JSONException) {
                    return null
                }
                return Reload(originalReason = reason)
            }
        }
    }

    /**
     * 刪除指定訊息
     */
    data class Delete(
        @SerializedName(PROPERTY_MESSAGE_ID)
        val messageId: Long
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            private const val PROPERTY_MESSAGE_ID = "messageId"
            const val TYPE_NAME: String = "@Delete"

            fun fromJson(json: JSONObject): Delete? {
                val messageId = json.optLong(PROPERTY_MESSAGE_ID, -1L)
                return if (messageId != -1L) {
                    Delete(messageId = messageId)
                } else {
                    null
                }
            }
        }
    }

    /**
     * 無法預期的錯誤
     */
    class Exception(
        @SerializedName(PROPERTY_EXCEPTION)
        val exception: String,
        @SerializedName(PROPERTY_ORIGINAL_MESSAGE)
        val originalMessage: String
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            private const val PROPERTY_EXCEPTION = "exception"
            private const val PROPERTY_ORIGINAL_MESSAGE = "originalMessage"
            const val TYPE_NAME: String = "@Exception"

            fun fromJson(json: JSONObject): Exception? {
                return try {
                    val exception = json.getString(PROPERTY_EXCEPTION)
                    val originalMessage = json.getString(PROPERTY_ORIGINAL_MESSAGE)
                    Exception(
                        exception = exception,
                        originalMessage = originalMessage
                    )
                } catch (exception: JSONException) {
                    null
                }
            }
        }
    }

    companion object {

        fun fromJson(type: String, json: JSONObject): Content? {
            return when (type) {
                Text.TYPE_NAME -> {
                    Text.fromJson(json)
                }
                Image.TYPE_NAME -> {
                    Image.fromJson(json)
                }
                Sticker.TYPE_NAME -> {
                    Sticker.fromJson(json)
                }
                Empty.TYPE_NAME -> {
                    Empty
                }
                Reload.TYPE_NAME -> {
                    Reload.fromJson(json)
                }
                Delete.TYPE_NAME -> {
                    Delete.fromJson(json)
                }
                Exception.TYPE_NAME -> {
                    Exception.fromJson(json)
                }
                else -> {
                    null
                }
            }
        }
    }
}
