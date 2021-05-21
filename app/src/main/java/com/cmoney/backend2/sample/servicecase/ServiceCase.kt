package com.cmoney.backend2.sample.servicecase

import org.koin.core.KoinComponent

interface ServiceCase : KoinComponent {
    suspend fun testAll()
}