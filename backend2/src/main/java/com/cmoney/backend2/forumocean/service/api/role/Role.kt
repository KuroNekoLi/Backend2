package com.cmoney.backend2.forumocean.service.api.role

enum class Role(val value: Int) {
    DEVELOPER(0), // CMoney工程部門
    BLUE_BADGE(1), // 藍勾勾
    OFFICIAL(2), // 官方人員帳號
    CONFIRM_USER(3), // 非藍勾勾的認可用戶
    COLUMNIST(4) // 專欄文章作者
}