package com.cmoney.backend2.base.model.request

/**
 * [https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language]
 * Accept-Language的參數值物件
 *
 * @property value 語系類別(en-US, zh-TW, zh)
 * @property quality 加權值，需介於0-1之前，預設為1.0
 */
data class Language(
    val value: String = "*",
    val quality: Double = 1.0
) {
    init {
        if (quality !in (0.0..1.0)) {
            throw IllegalArgumentException("The quality must between 0 and 1, but is $quality")
        }
    }

    companion object {
        private const val ZH_TW = "zh-TW"
        private const val EN_US = "en-US"

        /**
         * 中文-台灣語系
         *
         * @param quality
         * @return
         */
        fun zhTw(quality: Double = 1.0): Language {
            return Language(ZH_TW, quality)
        }

        /**
         * 英文-美國育系
         *
         * @param quality
         * @return
         */
        fun enUs(quality: Double = 1.0): Language {
            return Language(EN_US, quality)
        }
    }
}
