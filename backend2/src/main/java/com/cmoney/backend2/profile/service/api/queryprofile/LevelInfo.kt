package com.cmoney.backend2.profile.service.api.queryprofile


/**
 * 等級資訊
 *
 * @property exp 經驗值
 * @property level 等級
 * @property levelExp 當前等級累計經驗值
 * @property levelExpToNext 到下一級所需經驗值
 */
class LevelInfo private constructor(
    private val params: LevelInfoQueryParams,
    private val _exp: Double?,
    private val _level: Long?,
    private val _levelExp: Long?,
    private val _levelExpToNext: Long?
) {
    internal constructor(
        params: LevelInfoQueryParams,
        raw: RawLevelInfo
    ) : this(
        params = params,
        _exp = raw.exp,
        _level = raw.level,
        _levelExp = raw.levelExp,
        _levelExpToNext = raw.levelExpToNext
    )

    val exp: Double?
        get() {
            requireNotNull(params.exp) {
                "LevelInfoQueryBuilder.exp not request"
            }
            return _exp
        }

    val level: Long?
        get() {
            requireNotNull(params.level) {
                "LevelInfoQueryBuilder.level not request"
            }
            return _level
        }

    val levelExp: Long?
        get() {
            requireNotNull(params.levelExp) {
                "LevelInfoQueryBuilder.levelExp not request"
            }
            return _levelExp
        }

    val levelExpToNext: Long?
        get() {
            requireNotNull(params.levelExpToNext) {
                "LevelInfoQueryBuilder.levelExpToNext not request"
            }
            return _levelExpToNext
        }
}
