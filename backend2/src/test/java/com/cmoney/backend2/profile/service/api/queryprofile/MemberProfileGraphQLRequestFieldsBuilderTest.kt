package com.cmoney.backend2.profile.service.api.queryprofile

import com.google.common.truth.Truth

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class MemberProfileGraphQLRequestFieldsBuilderTest(
    @Suppress("unused")
    private val caseName: String,
    private val params: MemberProfileQueryParams,
    private val expected: String
) {

    private lateinit var fieldsBuilder: MemberProfileGraphQLRequestFieldsBuilder

    @Before
    fun setUp() {
        fieldsBuilder = MemberProfileGraphQLRequestFieldsBuilder(
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
                // empty
                arrayOf<Any?>(
                    "empty",
                    MemberProfileQueryBuilder().build(),
                    "{ }"
                ),
                arrayOf<Any?>(
                    "name",
                    MemberProfileQueryBuilder().apply {
                        name
                    }
                        .build(),
                    "{ name }"
                ),
                arrayOf<Any?>(
                    "name nickname",
                    MemberProfileQueryBuilder().apply {
                        name
                        nickname
                    }
                        .build(),
                    "{ name nickname }"
                ),
                arrayOf<Any?>(
                    "name nickname with reverse set",
                    MemberProfileQueryBuilder().apply {
                        nickname
                        name
                    }
                        .build(),
                    "{ name nickname }"
                ),
                arrayOf<Any?>(
                    "account detail email",
                    MemberProfileQueryBuilder().apply {
                        account {
                            email
                        }
                    }
                        .build(),
                    "{ account{ email } }"
                ),
                arrayOf<Any?>(
                    "nickname with empty account",
                    MemberProfileQueryBuilder().apply {
                        nickname
                        account
                    }
                        .build(),
                    "{ nickname }"
                ),
                arrayOf<Any?>(
                    "nickname account detail appleId",
                    MemberProfileQueryBuilder().apply {
                        nickname
                        account {
                            appleId
                        }
                    }
                        .build(),
                    "{ nickname account{ appleId } }"
                ),
                arrayOf<Any?>(
                    "account detail cellphone detail code number",
                    MemberProfileQueryBuilder().apply {
                        account {
                            cellphone {
                                code
                                number
                            }
                        }
                    }
                        .build(),
                    "{ account{ cellphone{ code number } } }"
                ),
                arrayOf<Any?>(
                    "account detail empty cellphone email",
                    MemberProfileQueryBuilder().apply {
                        account {
                            cellphone
                            email
                        }
                    }
                        .build(),
                    "{ account{ email } }"
                )
            )
        }
    }
}