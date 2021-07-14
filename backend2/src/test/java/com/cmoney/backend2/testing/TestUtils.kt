package com.cmoney.backend2.testing

import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

inline fun runBlockingWithCheckLog(
    logDataRecorder: LogDataRecorder,
    crossinline block: suspend CoroutineScope.() -> Unit
) = runBlocking {
    this.block()
    verify(exactly = 1) {
        logDataRecorder.logApi(apiLog = any())
    }
}