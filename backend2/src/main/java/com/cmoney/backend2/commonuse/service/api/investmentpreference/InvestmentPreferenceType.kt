package com.cmoney.backend2.commonuse.service.api.investmentpreference

/**
 * 投資偏好種類
 *
 * @property ids 投資偏好id編號
 */
enum class InvestmentPreferenceType(val ids: IntArray) {
    /**
     * 短線
     */
    Short(intArrayOf(1)),

    /**
     * 波段
     */
    Band(intArrayOf(2)),

    /**
     * 長期
     */
    Long(intArrayOf(3)),

    /**
     * 短線+波段+長期
     */
    All(intArrayOf(1, 2, 3)),

    /**
     * 無
     */
    None(intArrayOf())
}