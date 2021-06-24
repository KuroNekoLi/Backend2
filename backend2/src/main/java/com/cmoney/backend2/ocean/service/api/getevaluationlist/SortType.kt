package com.cmoney.backend2.ocean.service.api.getevaluationlist

enum class SortType(val value: Int) { //排序方式
    /**
     * 1:最新到最舊
     */
    LatestToOldest(1),
    /**
     * 2:星星數高到低
     */
    StarHighToLow(2),
    /**
     * 3:星星數低到高
     */
    StarLowToHigh(3)
}