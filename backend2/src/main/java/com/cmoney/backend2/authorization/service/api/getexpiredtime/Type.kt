package com.cmoney.backend2.authorization.service.api.getexpiredtime

enum class Type(val value: String) {
    RICH_POWER_PAID("RichPowerPaid"),
    RICH_POWER_TRY_OUT("RichPowerTryOut"),
    RICH_POWER_FREE("RichPowerFree"),
    RICH_POWER_FUNCTION("RichPowerFunction"),

    /**
     * subjectId代入AppId，等同於[MobileService.getAuth]
     */
    MOBILE_PAID("MobilePaid"),
    MEDIA_PAID("MediaPaid"),
    RICH_POWER_WEB_PAID("RichPowerWebPaid"),
    MOBILE_FUNCTION_PAID("MobileFunctionPaid")
}