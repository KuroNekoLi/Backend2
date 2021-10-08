package com.cmoney.backend2.base.model.typeadapter

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException


/**
 * Gson 用來轉換 [ULong] 的 TypeAdapter
 */
@ExperimentalUnsignedTypes
class ULongTypeAdapter : TypeAdapter<ULong>() {

    override fun write(out: JsonWriter, value: ULong?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.jsonValue(value.toString())
        }
    }

    /**
     * 讀取時需要注意使用 [JsonReader.nextString] 而非 [JsonReader.nextLong]
     * 因為讀取的值超過 Long 的區間，會造成溢位而直接 throw [NumberFormatException]
     */
    override fun read(`in`: JsonReader): ULong? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        return try {
            `in`.nextString().toULong()
        } catch (e: IOException) {
            throw JsonSyntaxException(e)
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }
    }

}
