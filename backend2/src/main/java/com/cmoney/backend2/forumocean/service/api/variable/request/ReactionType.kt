package com.cmoney.backend2.forumocean.service.api.variable.request

enum class ReactionType(val value : Int) {
    /**
     * 按讚
     *
     */
    LIKE(1),

    /**
     * 按噓
     *
     */
    DISLIKE(2),

    /**
     * 感到好笑
     *
     */
    Laugh(3),

    /**
     * 感到有價值
     *
     */
    Rich(4),

    /**
     * 感到震驚
     *
     */
    Shock(5),

    /**
     * 感到悲傷
     *
     */
    Sad(6),

    /**
     * 感到懷疑
     *
     */
    Doubt(7),

    /**
     * 感到生氣
     *
     */
    Angry(8);
}