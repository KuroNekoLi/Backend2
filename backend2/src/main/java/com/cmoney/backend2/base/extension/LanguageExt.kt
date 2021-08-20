package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.request.Language

fun List<Language>.asRequestHeader(): String {
    return if (this.isEmpty()) {
        Language().asRequestHeader()
    } else {
        this.joinToString(",") { language ->
            language.asRequestHeader()
        }
    }
}

fun Language.asRequestHeader(): String {
    return "${value};q=${quality}"
}