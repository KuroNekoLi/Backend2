package com.cmoney.backend2.chipk

import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcher : DispatcherProvider {
    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun compute(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun unconfined(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}