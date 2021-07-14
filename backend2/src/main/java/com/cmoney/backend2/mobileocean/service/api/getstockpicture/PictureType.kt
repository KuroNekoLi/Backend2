package com.cmoney.backend2.mobileocean.service.api.getstockpicture

enum class PictureType(val value : Int) {
    RealTimeTrend(1),
    DayKLine(2),
    WeekKLine(3),
    MonthKLine(4),
    ReductionKLine(6);
}