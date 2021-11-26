package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.reflect.KClass

@RunWith(Parameterized::class)
class RawMessageTest(
    private val jsonString: String,
    private val expectClass: KClass<*>
) {

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Test
    fun parseMessageFromJson() {
        val rawMessage = gson.fromJson(jsonString, RawMessage::class.java)
        val message = rawMessage.asReal()
        Truth.assertThat(message).isNotNull()
        requireNotNull(message)
        Truth.assertThat(message::class).isEqualTo(expectClass)
    }

    @Test
    fun parseContentToJson() {
        val rawMessage = gson.fromJson(jsonString, RawMessage::class.java)
        Truth.assertThat(rawMessage).isNotNull()
        requireNotNull(rawMessage)
        val rawContent = rawMessage.content
        Truth.assertThat(rawContent).isNotNull()
        requireNotNull(rawContent)
        val content = rawContent.asReal(rawMessage.type)
        gson.toJson(content)
    }

    companion object {
        @Parameterized.Parameters(name = "Check Parse {1}")
        @JvmStatic
        fun getData(): Iterable<Array<Any?>> {
            return listOf(
                arrayOf<Any?>(
                    """
                    {
                      "id": 899210,
                      "senderId": 15695,
                      "chatroomId": 2,
                      "type": "Text",
                      "content": {
                        "text": "測試"
                      },
                      "timestamp": 1637726262974,
                      "state": 0
                    }   
                    """.trimIndent(),
                    TextMessage::class
                ),
                arrayOf<Any?>(
                    """
                    {
                      "id": 899210,
                      "senderId": 15695,
                      "chatroomId": 2,
                      "type": "Image",
                      "content": {
                        "originalContentUrl": "http://192.168.10.103/document/image/483d10b5-ea97-434a-b24d-711e130aa537.jpg",
                        "previewImageUrl": "http://192.168.10.103/document/image/537d63dc-d2f6-43f7-b776-98b272c5bf34.jpg",
                        "width": 1080,
                        "height": 809
                      },
                      "timestamp": 1637726262974,
                      "state": 0
                    }
                    """.trimIndent(),
                    ImageMessage::class
                ),
                arrayOf<Any?>(
                    """
                    {
                      "id": 899210,
                      "senderId": 15695,
                      "chatroomId": 2,
                      "type": "Sticker",
                      "content": {
                        "packageId": 3,
                        "stickerId": 2
                      },
                      "timestamp": 1637726262974,
                      "state": 0
                    }
                    """.trimIndent(),
                    StickerMessage::class
                ),
                arrayOf<Any?>(
                    """
                    {
                      "id": 899210,
                      "senderId": 15695,
                      "chatroomId": 2,
                      "type": "Reply",
                      "content": {
                          "destination": 1111,
                          "type": "Text",
                          "content": {
                              "text": "test3"
                          }
                      },
                      "timestamp": 1637726262974,
                      "state": 0
                    }    
                    """.trimIndent(),
                    ReplyTextMessage::class
                )
            )
        }
    }
}