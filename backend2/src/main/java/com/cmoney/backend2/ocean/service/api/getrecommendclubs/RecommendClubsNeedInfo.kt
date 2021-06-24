package com.cmoney.backend2.ocean.service.api.getrecommendclubs

class RecommendClubsNeedInfo {
    /**
     * 推薦社團附加資訊列舉
     * 註:經Postman測試發現沒ViewerClubInfo這個資料,所以在此做enum以做限制,所以之後使用此API時能正確拿到需要的資訊
     * 註:目前同學會專案是停用但保留此功能,如果後續有其他專案用到此功能,再麻煩請跟"服務"確認是否需要ViewerClubInfo再做修改
     * @property value
     */
    enum class NeedInfo(val value: Int) {
        /**
         * 頻道介紹
         */
        Description(1),

        /**
         * 頻道頭像
         */
       Image(2),

        /**
         * 社團資訊
         */
        ClubInfo(2048),

        /**
         * 會員在這個社團的資訊
         */
       MemberClubInfo(4096)
    }

    /**
     * 設定的推薦社團附加資訊清單
     */
    private val needInfo = ArrayList<NeedInfo>()

    /**
     * 加入附加資訊
     *
     * @param info
     */
    fun add(info: NeedInfo) = needInfo.add(info)

    /**
     * 移除附加資訊
     *
     * @param info
     */
    fun remove(info: NeedInfo) = needInfo.remove(info)

    /**
     * 取得所有推薦社團附加資訊加總的結果
     *
     */
    fun getCombinedResult() = needInfo.distinct().sumBy { it.value }
}