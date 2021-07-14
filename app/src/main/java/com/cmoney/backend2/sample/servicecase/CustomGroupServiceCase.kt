package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.customgroup.service.CustomGroupWeb
import com.cmoney.backend2.customgroup.service.api.common.CustomGroupType
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.inject

class CustomGroupServiceCase : ServiceCase {

    private val customGroupServiceImpl by inject<CustomGroupWeb>()

    override suspend fun testAll() {
        customGroupServiceImpl.getCustomGroupListIncludeOrder(groupType = groupType)
        customGroupServiceImpl.getCustomGroupListIncludeOrderAndContent(groupType = groupType)
        val newCustomGroup = customGroupServiceImpl.addCustomGroup(groupType = groupType, docName = "Test before")
        val newDocNo = newCustomGroup.getOrThrow().docNo ?: return
        customGroupServiceImpl.getCustomGroupContent(newDocNo)
        customGroupServiceImpl.updateCustomGroupNameAndContent(
            groupType = groupType,
            docNo = newDocNo,
            docName = "Test before",
            list = listOf("2330")
        )
        customGroupServiceImpl.getCustomGroupContent(newDocNo)
        customGroupServiceImpl.renameCustomGroup(groupType = groupType, docNo = newDocNo, newDocName = "Test change name")
        customGroupServiceImpl.deleteCustomGroup(newDocNo)
        customGroupServiceImpl.searchStocks("A").logResponse(TAG)
    }

    companion object {
        private const val TAG = "CustomGroup"
        private val groupType = CustomGroupType.UsStock
    }
}