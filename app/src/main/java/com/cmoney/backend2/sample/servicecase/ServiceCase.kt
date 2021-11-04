package com.cmoney.backend2.sample.servicecase

import org.koin.core.component.KoinComponent

interface ServiceCase : KoinComponent {
    suspend fun testAll()
}