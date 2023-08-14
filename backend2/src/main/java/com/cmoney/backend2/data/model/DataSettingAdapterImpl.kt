package com.cmoney.backend2.data.model

class DataSettingAdapterImpl : DataSettingAdapter {

    override fun getDomain(): String {
        return "https://datasv.cmoney.tw:5001/"
    }
}