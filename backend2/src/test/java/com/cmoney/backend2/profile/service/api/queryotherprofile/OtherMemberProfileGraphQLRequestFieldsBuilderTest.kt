package com.cmoney.backend2.profile.service.api.queryotherprofile

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class OtherMemberProfileGraphQLRequestFieldsBuilderTest(
    @Suppress("unused")
    private val caseName: String,
    private val params: OtherMemberProfileQueryParams,
    private val expected: String
) {

    private lateinit var fieldsBuilder: OtherMemberProfileGraphQLRequestFieldsBuilder

    @Before
    fun setUp() {
        fieldsBuilder = OtherMemberProfileGraphQLRequestFieldsBuilder(
            queryParams = params
        )
    }

    @Test
    fun getRequestFields() {
        val result = fieldsBuilder.build()
        Truth.assertThat(result).isEqualTo(expected)
    }

    companion object {
        @Parameterized.Parameters(name = "case: {0}")
        @JvmStatic
        fun getTestCases(): Iterable<Array<Any?>> {
            return listOf(
                arrayOf<Any?>(
                    "empty",
                    OtherMemberProfileQueryBuilder().build(),
                    "{ id }"
                ),
                arrayOf<Any?>(
                    "nickname",
                    OtherMemberProfileQueryBuilder().apply {
                        nickname
                    }
                        .build(),
                    "{ id nickname }"
                ),
                arrayOf<Any?>(
                    "nickname bio with reverse set",
                    OtherMemberProfileQueryBuilder().apply {
                        bio
                        nickname
                    }
                        .build(),
                    "{ bio id nickname }"
                ),
                arrayOf<Any?>(
                    "nickname bio with reverse set",
                    OtherMemberProfileQueryBuilder().apply {
                        nickname
                        bio
                    }
                        .build(),
                    "{ bio id nickname }"
                )
            )
        }
    }
}