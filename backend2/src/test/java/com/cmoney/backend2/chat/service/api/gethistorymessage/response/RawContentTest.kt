package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class RawContentTest(private val contentJson: String, private val type: String, private val expectContent: Content) {

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Test
    fun asReal_equal_to_expect() {
        val rawContent = gson.fromJson(contentJson, RawContent::class.java)
        val content = rawContent.asReal(type)
        Truth.assertThat(content).isNotNull()
        requireNotNull(content)
        Truth.assertThat(content).isEqualTo(expectContent)
    }

    companion object {

        @Parameterized.Parameters(name = "Check {1}")
        @JvmStatic
        fun getData(): Iterable<Array<Any?>> {
            return listOf(
                arrayOf<Any?>(
                    """
                    {
                      "text":"測試"
                    }
                    """.trimIndent(),
                    "Text",
                    Content.Text(
                        text = "測試"
                    )
                ),
                arrayOf<Any?>(
                    """
                    {
                        "originalContentUrl": "http://192.168.10.103/document/image/483d10b5-ea97-434a-b24d-711e130aa537.jpg",
                        "previewImageUrl": "http://192.168.10.103/document/image/537d63dc-d2f6-43f7-b776-98b272c5bf34.jpg",
                        "width": 1080,
                        "height": 809
                    }    
                    """.trimIndent(),
                    "Image",
                    Content.Image(
                        originalContentUrl = "http://192.168.10.103/document/image/483d10b5-ea97-434a-b24d-711e130aa537.jpg",
                        previewImageUrl = "http://192.168.10.103/document/image/537d63dc-d2f6-43f7-b776-98b272c5bf34.jpg",
                        width = 1080,
                        height = 809
                    )
                ),
                arrayOf<Any?>(
                    """
                    {
                        "packageId": 3,
                        "stickerId": 2
                    }
                    """.trimIndent(),
                    "Sticker",
                    Content.Sticker(
                        packageId = 3,
                        stickerId = 2
                    )
                ),
                arrayOf<Any?>(
                    """
                    {
                        "destination": 1111,
                        "type": "Text",
                        "content": {
                            "text": "test3"
                        }
                    }
                    """.trimIndent(),
                    "Reply",
                    Content.Reply.Text(
                        destination = 1111,
                        type = "Text",
                        content = Content.Text(
                            text = "test3"
                        )
                    )
                )
            )
        }
    }
}