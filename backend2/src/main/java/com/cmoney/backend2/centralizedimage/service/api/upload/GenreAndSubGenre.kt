package com.cmoney.backend2.centralizedimage.service.api.upload

/**
 * 上傳的分類與子分類
 *
 * @property genre 主分類
 * @property subgenre 子分類
 */
sealed class GenreAndSubGenre(val genre: String, val subgenre: String) {
    /**
     * 網誌文章附圖
     */
    object AttachmentBlog : GenreAndSubGenre("attachment", "blog")

    /**
     * 課程文章附圖
     */
    object AttachmentCourse : GenreAndSubGenre("attachment", "course")

    /**
     * 聊天室發圖/發言附圖
     */
    object AttachmentMessage : GenreAndSubGenre("attachment", "message")

    /**
     * 討論區發文/回文附圖
     */
    object AttachmentPost : GenreAndSubGenre("attachment", "post")

    /**
     * 徽章
     */
    object CommonUseBadge : GenreAndSubGenre("commonuse", "badge")

    /**
     * 貼圖
     */
    object CommonUseSticker : GenreAndSubGenre("commonuse", "sticker")

    /**
     * 圖標
     */
    object OfficialIcon : GenreAndSubGenre("official", "icon")

    /**
     * 產品標識
     */
    object OfficialLogo : GenreAndSubGenre("official", "logo")

    /**
     * 宣傳圖
     */
    object OfficialPromotion : GenreAndSubGenre("official", "promotion")

    /**
     * 預設大頭貼
     */
    object ProfileDefault : GenreAndSubGenre("profile", "default")

    /**
     * 競技場logo
     */
    object ProfileArena : GenreAndSubGenre("profile", "arena")

    /**
     * 聊天室圖像
     */
    object ProfileChatroom : GenreAndSubGenre("profile", "chatroom")

    /**
     * 社團大頭貼
     */
    object ProfileClub : GenreAndSubGenre("profile", "club")

    /**
     * 用戶大頭貼
     */
    object ProfileMember : GenreAndSubGenre("profile", "member")

    /**
     * 測試專用
     */
    object ServiceTestSwagger: GenreAndSubGenre("servicetest", "swagger")

    /**
     * 測試專用
     */
    object ServiceTestPostman: GenreAndSubGenre("servicetest", "postman")

    companion object {
        /**
         * 用於取得所有可用分類與子分類(不含測試)
         *
         * @return
         */
        fun values(): List<GenreAndSubGenre> {
            return listOf(
                AttachmentBlog,
                AttachmentCourse,
                AttachmentMessage,
                AttachmentPost,
                CommonUseBadge,
                CommonUseSticker,
                OfficialIcon,
                OfficialLogo,
                OfficialPromotion,
                ProfileDefault,
                ProfileArena,
                ProfileChatroom,
                ProfileClub,
                ProfileMember
            )
        }
    }
}
