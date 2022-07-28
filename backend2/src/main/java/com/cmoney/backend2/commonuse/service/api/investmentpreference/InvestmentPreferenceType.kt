package com.cmoney.backend2.commonuse.service.api.investmentpreference

/**
 * 投資偏好種類
 *
 * @property ids 投資偏好id編號
 */
enum class InvestmentPreferenceType(val ids: List<Int>) {
    /**
     * 短線
     */
    Short(listOf(1)),

    /**
     * 波段
     */
    Band(listOf(2)),

    /**
     * 長期
     */
    Long(listOf(3)),

    /**
     * 短線+波段+長期
     */
    All(listOf(1, 2, 3))
}