package com.cmoney.backend2.base.model.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Coroutine Dispatcher提供者，主要拿來模擬Dispatchers的切換。
 */
@Deprecated(
    message = "Use com.cmoney.core.DispatcherProvider instead.",
    replaceWith = ReplaceWith("", "com.cmoney.core.DispatcherProvider")
)
interface DispatcherProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun compute(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}