package com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles

/**
 * 頻道分類
 * (0:全部, 101:人物, 601:訊號, 6: 最愛 , 1:財經, 2:娛樂, 4:科技,
 * 8:美食, 32:休閒, 64:新聞, 128:旅行 , 256:運動 , 512:政治, 16:其他)
 *
 * @property value
 */
enum class ChannelCategory(val value : Int) {
    /**
     * 全部
     */
    ALL(0),
    /**
     * 人物
     */
    PEOPLE(101),
    /**
     * 訊號
     */
    SIGNAL(601),
    /**
     * 最愛
     */
    FAVORITE(6) ,
    /**
     * 財經
     */
    FINANCE(1),
    /**
     * 娛樂
     */
    ENTERTAINMENT(2),
    /**
     * 科技
     */
    TECHNOLOGY(4),
    /**
     * 美食
     */
    FOOD(8),
    /**
     * 休閒
     */
    LEISURE(32),
    /**
     * 新聞
     */
    NEWS(64),
    /**
     * 旅行
     */
    TRAVEL(128) ,
    /**
     * 運動
     */
    SPORT(256),
    /**
     * 政治
     */
    POLITICAL(512),
    /**
     * 其他
     */
    OTHER(16);
}
