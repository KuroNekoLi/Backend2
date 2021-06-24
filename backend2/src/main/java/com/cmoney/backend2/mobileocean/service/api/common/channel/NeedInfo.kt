package com.cmoney.backend2.mobileocean.service.api.common.channel

enum class NeedInfo(val value: Int) {

    Description(1 shl 0),

    Image(1 shl 1),

    FansCount(1 shl 2),

    LikeCount(1 shl 3),

    ArticleCount(1 shl 4),

    IsFollowed(1 shl 5),

    LevelInfo(1 shl 6),

    DiamondInfo(1 shl 7),

    AnswersCount(1 shl 8),

    AttentionCount(1 shl 9),

    MemberRegisterTime(1 shl 10),

    All(-0x1);

    companion object {
        fun valueOf(vararg needInfo: NeedInfo) = needInfo.map { it.value }.reduce { sum, value ->
            sum or value
        }
    }

}
