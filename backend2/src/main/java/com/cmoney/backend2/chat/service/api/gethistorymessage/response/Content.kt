package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.gson.annotations.SerializedName

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
        val text: String?
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            const val PROPERTY_TEXT = "text"
            const val TYPE_NAME = "Text"
        }
    }

    /**
     * 圖片訊息
     */
    data class Image(
        @SerializedName(PROPERTY_ORIGINAL_CONTENT_URL)
        val originalContentUrl: String?,
        @SerializedName(PROPERTY_PREVIEW_IMAGE_URL)
        val previewImageUrl: String?,
        @SerializedName(PROPERTY_WIDTH)
        val width: Int?,
        @SerializedName(PROPERTY_HEIGHT)
        val height: Int?
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            const val PROPERTY_ORIGINAL_CONTENT_URL = "originalContentUrl"
            const val PROPERTY_PREVIEW_IMAGE_URL = "previewImageUrl"
            const val PROPERTY_WIDTH = "width"
            const val PROPERTY_HEIGHT = "height"
            const val TYPE_NAME = "Image"
        }
    }

    /**
     * 貼圖訊息
     */
    data class Sticker(
        @SerializedName(PROPERTY_PACKAGE_ID)
        val packageId: Int?,
        @SerializedName(PROPERTY_STICKER_ID)
        val stickerId: Int?
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            const val PROPERTY_PACKAGE_ID = "packageId"
            const val PROPERTY_STICKER_ID = "stickerId"
            const val TYPE_NAME: String = "Sticker"
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
        val originalReason: String?
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
            const val PROPERTY_REASON = "reason"
            const val TYPE_NAME = "@Reload"
        }
    }

    /**
     * 刪除指定訊息
     */
    data class Delete(
        @SerializedName(PROPERTY_MESSAGE_ID)
        val messageId: Long?
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            const val PROPERTY_MESSAGE_ID = "messageId"
            const val TYPE_NAME: String = "@Delete"
        }
    }

    /**
     * 無法預期的錯誤
     */
    class Exception(
        @SerializedName(PROPERTY_EXCEPTION)
        val exception: String?,
        @SerializedName(PROPERTY_ORIGINAL_MESSAGE)
        val originalMessage: String?
    ) : Content() {

        override val typeName: String = TYPE_NAME

        companion object {
            const val PROPERTY_EXCEPTION = "exception"
            const val PROPERTY_ORIGINAL_MESSAGE = "originalMessage"
            const val TYPE_NAME: String = "@Exception"
        }
    }
}
