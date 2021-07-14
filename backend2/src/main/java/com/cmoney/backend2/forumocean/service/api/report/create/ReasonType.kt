package com.cmoney.backend2.forumocean.service.api.report.create

/**
 * 1=騷擾或霸凌/人身攻擊
 * 2=垃圾廣告/這是垃圾廣告
 * 3=詐欺或誘導/根本詐騙集團
 * 4=與投資無關
 * -1=其他
 *
 */
enum class ReasonType(val value : Int) {
    Other(-1),
    Harass(1),
    AD(2),
    Fraud(3),
    InvestmentIrrelevant(4)
}