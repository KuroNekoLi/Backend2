package com.cmoney.backend2.chat.service.api.variable

enum class Subject : IGetName {

    CHAT_ROOM {
        override val subjectName: String
            get() = "Chatroom"
    },

    USER_PROFILE{
        override val subjectName: String
            get() = "UserProfile"
    };
}

interface IGetName {
    val subjectName: String
}
