package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.google.gson.*
import com.google.gson.reflect.TypeToken

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


