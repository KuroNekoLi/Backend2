package com.cmoney.backend2.customgroup2.service.api.data

/**
 * 使用者設定
 *
 * @property customGroupOrders 自選股群組排序，[Map.Entry.key]為群組編號，[Map.Entry.value]為群組排序比較子
 */
data class UserConfiguration(
    val customGroupOrders: Map<String, Int>?
)
