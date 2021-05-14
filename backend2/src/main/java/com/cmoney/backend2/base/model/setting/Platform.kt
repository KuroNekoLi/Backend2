package com.cmoney.backend2.base.model.setting

/**
 * 平台
 *
 * @property code 代號
 *
 */
sealed class Platform(open val code: Int) {
    object IOS: Platform(1)
    object Android: Platform(2)
    object Web: Platform(3)
    object Pc: Platform(4)
    object Huawei: Platform(5)
    data class Other(override val code: Int): Platform(code)
}