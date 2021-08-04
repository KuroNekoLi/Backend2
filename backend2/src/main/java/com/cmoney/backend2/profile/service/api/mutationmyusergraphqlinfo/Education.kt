package com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo

enum class Education(val text : String) {

    /**
     * 中學以下
     *
     */
    SecondaryOrBelow("中學以下"),

    /**
     * 高中、高職
     *
     */
    SeniorHighSchool("高中、高職"),

    /**
     * 專科
     *
     */
    JuniorCollege("專科"),

    /**
     * 大學
     *
     */
    University("大學"),

    /**
     * 研究所以上
     *
     */
    GraduateSchoolOrAbove("研究所以上")
}