package com.cmoney.backend2.ocean.service.api.variable

/**
 * 頻道的附加資訊
 *
 */
class ChannelNeedInfo<T : ChannelInfoOption> {

    /**
     * 需附加的資訊清單
     */
    private val needInfo = mutableListOf<T>()

    /**
     * 加入附加資訊
     *
     * @param info
     */
    fun add(info: T) = needInfo.add(info)

    /**
     * 移除附加資訊
     *
     * @param info
     */
    fun remove(info: T) = needInfo.remove(info)

    /**
     * 加入附加資訊清單
     *
     * @param infoList
     */
    fun addAll(infoList: Collection<T>) = needInfo.addAll(infoList)

    /**
     * 取得所有頻道附加資訊加總的結果
     *
     */
    fun getCombinedResult() = needInfo.distinct().sumBy { it.value }
}