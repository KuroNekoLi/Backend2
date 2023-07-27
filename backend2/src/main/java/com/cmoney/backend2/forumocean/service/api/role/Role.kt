package com.cmoney.backend2.forumocean.service.api.role

enum class Role(val value: Int) {
    /**
     * CMoney工程部門
     */
    DEVELOPER(0),

    /**
     * 藍勾勾
     */
    BLUE_BADGE(1),

    /**
     * 官方人員帳號
     */
    OFFICIAL(2),

    /**
     * 非藍勾勾的認可用戶
     */
    CONFIRM_USER(3),

    /**
     * 專欄文章作者
     */
    COLUMNIST(4),

    /**
     * 語音直播白名單
     */
    CLUB_HOUSE(5)
}