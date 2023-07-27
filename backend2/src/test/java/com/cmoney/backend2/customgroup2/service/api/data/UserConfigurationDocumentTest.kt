package com.cmoney.backend2.customgroup2.service.api.data

import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class UserConfigurationDocumentTest {
    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @Test
    fun `UserConfigurationDocument_default id is _configuration`() = testScope.runTest {
        val document = UserConfigurationDocument(
            documentOrders = mapOf()
        )
        Truth.assertThat(document.id).isEqualTo("_configuration")
    }

    @Test
    fun `UserConfigurationDocument_default type is UserConfiguration`() = testScope.runTest {
        val document = UserConfigurationDocument(
            documentOrders = mapOf()
        )
        Truth.assertThat(document.type).isEqualTo("UserConfiguration")
    }
}