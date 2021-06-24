package com.cmoney.backend2.ocean.service.api.getmasters

enum class MasterType(val value: Int) {
    /**
     * 1:熱門值
     */
    Popularity(1),
    /**
     * 2:粉絲增長
     */
    FansIncrease(2),
    /**
     * 3:鑽石增長
     */
    DiamondIncrease(3)
}