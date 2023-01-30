package com.cmoney.backend2.base.model.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * 預設的Dispatcher Provider
 */
@Deprecated(
    message = "Use com.cmoney.core.DefaultDispatcherProvider instead.",
    replaceWith = ReplaceWith("", "com.cmoney.core.DefaultDispatcherProvider"),
    level = DeprecationLevel.ERROR
)
class DefaultDispatcherProvider : DispatcherProvider {
    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun compute(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun unconfined(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}