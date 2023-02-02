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

inline fun <reified T: Any> DtnoData.toListOfType(gson: Gson): List<T> {
    val titleIndexMap = getTitleIndexMapping()
    val type = T::class
    val primaryConstructor = type.primaryConstructor!!
    val constructorParameterMap = primaryConstructor.parameters.associateBy {
        it.name
    }
    return data?.map { items ->
        val parameterWithArgs = type.memberProperties.mapNotNull { property ->
            val parameter = constructorParameterMap[property.name] ?: return@mapNotNull null
            val serializedName = property.javaField?.annotations?.filterIsInstance<SerializedName>()
                ?.first()
                ?.value
            val argument = items?.getOrNull(titleIndexMap[serializedName] ?: 0)
            val kClass = parameter.type.jvmErasure
            val jvmClass = kClass.java
            val value = when (kClass) {
                String::class -> {
                    if (argument == null) {
                        null
                    } else if (argument.isEmpty()) {
                        ""
                    } else {
                        gson.fromJson(argument, jvmClass)
                    }
                }
                else -> {
                    gson.fromJson(argument, jvmClass)
                }
            }
            parameter to value
        }.toMap()
        primaryConstructor.callBy(parameterWithArgs)
    }.orEmpty()
}
