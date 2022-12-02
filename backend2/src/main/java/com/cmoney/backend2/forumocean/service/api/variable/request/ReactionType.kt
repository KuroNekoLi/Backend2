package com.cmoney.backend2.forumocean.service.api.variable.request

enum class ReactionType(
    val value: Int,
    val stringValue: String
) {
    /**
     * 按讚
     *
     */
    LIKE(1,"like"),

    /**
     * 按噓
     *
     */
    DISLIKE(2,"dislike"),

    /**
     * 感到好笑
     *
     */
    Laugh(3,"laugh"),

    /**
     * 感到有價值
     *
     */
    Rich(4,"money"),

    /**
     * 感到震驚
     *
     */
    Shock(5,"shock"),

    /**
     * 感到悲傷
     *
     */
    Sad(6,"cry"),

    /**
     * 感到懷疑
     *
     */
    Doubt(7,"think"),

    /**
     * 感到生氣
     *
     */
    Angry(8,"angry");
}