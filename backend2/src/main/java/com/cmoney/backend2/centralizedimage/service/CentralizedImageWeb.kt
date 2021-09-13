package com.cmoney.backend2.centralizedimage.service

import com.cmoney.backend2.centralizedimage.service.api.upload.UploadResponseBody
import java.io.File

interface CentralizedImageWeb {
    enum class Destination(val value: String) {
        /**
         * 網誌文章附圖
         */
        ATTACHMENT_BLOG("attachment/blog"),

        /**
         * 課程文章附圖
         */
        ATTACHMENT_COURSE("attachment/course"),

        /**
         * 聊天室發圖/發言附圖
         */
        ATTACHMENT_MESSAGE("attachment/message"),

        /**
         * 討論區發文/回文附圖
         */
        ATTACHMENT_POST("attachment/post"),

        /**
         * 徽章
         */
        COMMONUSE_BADGE("commonuse/badge"),

        /**
         * 貼圖
         */
        COMMONUSE_STICKER("commonuse/sticker"),

        /**
         * 圖標
         */
        OFFICIAL_ICON("official/icon"),

        /**
         * 產品標識
         */
        OFFICIAL_LOGO("official/logo"),

        /**
         * 宣傳圖
         */
        OFFICIAL_PROMOTION("official/promotion"),

        /**
         * 預設大頭貼
         */
        PROFILE_DEFAULT("profile/default"),

        /**
         * 競技場logo
         */
        PROFILE_ARENA("profile/arena"),

        /**
         * 聊天室圖像
         */
        PROFILE_CHATROOM("profile/chatroom"),

        /**
         * 社團大頭貼
         */
        PROFILE_CLUB("profile/club"),

        /**
         * 用戶大頭貼
         */
        PROFILE_MEMBER("profile/member"),

        /**
         * 服務組測試專用-swagger
         */
        SERVICETEST_SWAGGER("servicetest/swagger"),

        /**
         * 服務組測試專用-postman
         */
        SERVICETEST_POSTMAN("servicetest/postman");
    }

    /**
     * 上傳單張圖片,目標位置
     */
    suspend fun upload(destination: Destination, file: File): Result<UploadResponseBody>
}