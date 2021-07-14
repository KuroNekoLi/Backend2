package com.cmoney.backend2.sample.model

sealed class Role {
    object Guest : Role()
    object Member : Role()

    inline fun fold(guest: () -> Unit, member: () -> Unit) = when (this) {
        Guest -> {
            guest()
        }
        Member -> {
            member()
        }
    }
}