package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.cellphone.service.CellphoneWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class CellphoneServiceCase : ServiceCase {

    private val web by inject<CellphoneWeb>()

    override suspend fun testAll() {
//        web.getVerifyCode(CellphoneParam(countryCode = "886", cellphoneNumber = ""))
//            .logResponse(TAG)
//        web.checkVerifyCode(CellphoneParam(countryCode = "886", cellphoneNumber = ""), "123")
//            .logResponse(TAG)
//        web.registerByCellphone(CellphoneParam("886", ""), "1234")
//            .logResponse(TAG)
//        web.forgotPasswordForCellphone(CellphoneParam("886", ""))
//            .logResponse(TAG)
        web.getAccountInfo()
            .logResponse(TAG)
//        web.bindCellphone(CellphoneParam("886", ""))
//            .logResponse(TAG)
//        web.checkCellphoneBindingVerifyCode(CellphoneParam("886", ""), "")
//        web.unbindCellphone()
//            .logResponse(TAG)
    }

    companion object {
        private const val TAG = "Cellphone"
    }
}