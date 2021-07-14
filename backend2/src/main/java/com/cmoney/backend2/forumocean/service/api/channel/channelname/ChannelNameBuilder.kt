package com.cmoney.backend2.forumocean.service.api.channel.channelname

class ChannelNameBuilder {

    companion object{
        private const val CONNECT_STRING = "-"

        /**
         * 頻道名稱清單轉成request body字串的方法 用逗點分隔  但不確定為何單用 "," 會導致500 使用", " 就沒問題
         *
         * @return
         */
        fun List<ChannelNameBuilder>.createChannelNameList() : String{
            return joinToString() { it.create() }
        }
    }

    /**
     * 順序類型
     */
    var orderType : OrderType = OrderType.None

    /**
     * 系統分類
     */
    var systemClassification : SystemClassification = SystemClassification.None

    /**
     * 產品類型
     */
    var product : Product = Product.None

    /**
     * 股票Tag分類
     */
    var stockClassification : StockClassification = StockClassification.None

    /**
     * 自訂Tag
     */
    var userTagClassification : UserTagClassification = UserTagClassification.None

    /**
     * 生成ChannelName
     * 頻道名稱的產生有順序限制  不能換程式碼的前後位置
     *
     * @return
     */
    fun create() : String{
        return buildString {

            orderType.getChannelName()?.let {
                append(it)
            }

            systemClassification.getChannelName()?.let {
                if(this.isNotEmpty()){
                    append(CONNECT_STRING)
                }
                append(it)
            }


            product.getChannelName()?.let {
                if(this.isNotEmpty()){
                    append(CONNECT_STRING)
                }
                append(it)
            }

            stockClassification.getChannelName()?.let {
                if(this.isNotEmpty()){
                    append(CONNECT_STRING)
                }
                append(it)
            }

            userTagClassification.getChannelName()?.let {
                if(this.isNotEmpty()){
                    append(CONNECT_STRING)
                }
                append(it)
            }
        }
    }
}