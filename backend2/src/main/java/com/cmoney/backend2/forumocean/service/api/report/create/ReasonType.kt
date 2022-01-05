package com.cmoney.backend2.forumocean.service.api.report.create

/**
 * 1 廣告、商業宣傳
 * 2 歧視或謾罵他人
 * 3 相同內容重複洗版
 * 4 其他原因
 */
enum class ReasonType(val value : Int) {
    AD(1),
    Abuse(2),
    Spamming(3),
    Other(4)
}