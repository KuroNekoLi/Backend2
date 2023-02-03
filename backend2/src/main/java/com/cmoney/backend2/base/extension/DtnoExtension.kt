package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.jvmErasure

fun DtnoData.getTitleIndexMapping(): Map<String, Int> {
    return title?.mapIndexed { index, title ->
        title to index
    }?.toMap()
        .orEmpty()
}

fun DtnoData.toJsonArrayString(): String {
    val titles = getTitleIndexMapping()
    val list = JsonArray()
    data?.forEach { items ->
        val item = JsonObject()
        titles.forEach { (title, index) ->
            val value = items?.get(index).orEmpty()
            item.addProperty(title, value)
        }
        list.add(item)
    }
    return list.toString()
}

@Throws(JsonSyntaxException::class, JsonParseException::class)
inline fun <reified T> DtnoData.toListOfSomething(gson: Gson): List<T> {
    return gson.fromJson(toJsonArrayString(), object : TypeToken<List<T>>() {}.type)
}

/**
 * 將原始資料轉換為資料物件
 * 需要設定 [SerializedName] 來定義物件欄位與原始資料的關係
 *
 * @param T 要轉換的類型
 * @param gson 轉換欄位的物件
 * @return 資料物件的集合
 */
@Throws(IllegalArgumentException::class, JsonParseException::class)
inline fun <reified T: Any> DtnoData.toListOfType(gson: Gson): List<T> {
    val titleIndexMap = getTitleIndexMapping()
    val type = T::class
    val primaryConstructor = requireNotNull(type.primaryConstructor)
    val memberPropertyMap = type.memberProperties.associateBy { property ->
        property.name
    }
    return data?.map { items ->
        val constructorArgs = primaryConstructor.parameters.map argMap@ { parameter ->
            // 取得建構式參數名稱相同之欄位
            val property = memberPropertyMap[parameter.name] ?: return@argMap null
            val serializedName = property.javaField?.let { field ->
                val annotation = field.annotations.filterIsInstance<SerializedName>()
                    .firstOrNull()
                requireNotNull(annotation) {
                    "Field ${field.name} not set the @SerializedName."
                }
                annotation.value
            } ?: return@argMap null
            // 若物件的序列化名稱在資料中不存在則回傳原始資料為 null
            val argument = titleIndexMap[serializedName]?.let { index ->
                items?.getOrNull(index)
            }
            val kClass = parameter.type.jvmErasure
            val jvmClass = kClass.java
            val value = when (kClass) {
                String::class -> {
                    if (argument.isNullOrEmpty()) {
                        argument
                    } else {
                        gson.fromJson(argument, jvmClass)
                    }
                }
                else -> {
                    gson.fromJson(argument, jvmClass)
                }
            }
            value
        }
        primaryConstructor.call(*constructorArgs.toTypedArray())
    }.orEmpty()
}
