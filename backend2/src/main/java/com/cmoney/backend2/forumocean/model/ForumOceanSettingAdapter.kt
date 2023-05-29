package com.cmoney.backend2.forumocean.model

import com.cmoney.backend2.forumocean.service.ForumOceanWeb

/**
 * ForumOcean(討論區)服務設定轉接器
 */
interface ForumOceanSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String

    /**
     * 取得替換的Path，只提供特定位置的Path。
     * 需要加上 "/"，例如: "ForumOcean/"
     * 如果要個別改變，請使用[ForumOceanWeb]方法的url參數。
     */
    fun getPathName(): String
}