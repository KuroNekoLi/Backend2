package com.cmoney.backend2.notification2

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

/**
 * TestCoroutineScope:
 * A scope which provides detailed control over the execution of coroutines for tests.
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher() , TestCoroutineScope by TestCoroutineScope(){
    /**
     * 測試開始前(before Before)
     * 會call starting
     * 開一個coroutine dispatcher
     */
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    /**
     * 測試結束後(after After)
     * 會call finish
     * 把coroutine dispatcher 關掉
     */
    override fun finished(description: Description?){
        super.finished(description)
        Dispatchers.resetMain()
    }
}