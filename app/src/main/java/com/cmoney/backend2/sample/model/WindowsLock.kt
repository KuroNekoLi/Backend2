package com.cmoney.backend2.sample.model

sealed class WindowsLock {
    object Lock : WindowsLock()
    object Unlock : WindowsLock()

    inline fun fold(lock: () -> Unit, unlock: () -> Unit) = when (this) {
        Lock -> {
            lock()
        }
        Unlock -> {
            unlock()
        }
    }
}