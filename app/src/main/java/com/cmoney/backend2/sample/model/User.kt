package com.cmoney.backend2.sample.model

class User {
    var account = ""
    var guid = ""
    var authToken = ""
    var role: Role =
        Role.Guest
        get() {
            return if (guid.isEmpty() || authToken.isEmpty()) {
                Role.Guest
            } else {
                Role.Member
            }
        }
        private set
    val isMember = role == Role.Member
}