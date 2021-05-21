package com.cmoney.backend2.sample.view.main.data

sealed class LoginEvent {
    object Success : LoginEvent()
}