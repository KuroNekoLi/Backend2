package com.cmoney.backend2.sample.model.logger

import com.orhanobut.logger.AndroidLogAdapter

class ApplicationLoggerAdapter: AndroidLogAdapter() {
    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return tag == "PRETTY_LOGGER"
    }
}