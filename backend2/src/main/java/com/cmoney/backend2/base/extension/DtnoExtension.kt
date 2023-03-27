package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
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
@Deprecated("使用toListOfType代替", replaceWith = ReplaceWith("this.toListOfType<T>(gson = gson)", "com.cmoney.backend2.base.extension"))
inline fun <reified T> DtnoData.toListOfSomething(gson: Gson): List<T> {
    return toListOfType(gson)
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
    primaryConstructor.isAccessible = true
    val memberPropertyMap = type.memberProperties.associateBy { property ->
        property.name
    }
    val typeWithItemIndexList = primaryConstructor.parameters.mapNotNull paramMap@ { parameter ->
        // 取得建構式參數名稱相同之欄位
        val property = memberPropertyMap[parameter.name] ?: return@paramMap null
        property.javaField?.let indexLet@ { field ->
            // 取得對應序列化名稱集合之資料索引位置，若無對應索引位置則令其值為-1
            // 索引位置回傳時須配上對應之資料類型
            val annotation = field.annotations.filterIsInstance<SerializedName>()
                .firstOrNull()
            requireNotNull(annotation) {
                "Field ${field.name} not set the @SerializedName."
            }
            // 處理 annotation value
            titleIndexMap[annotation.value]?.let { index ->
                return@indexLet parameter.type.jvmErasure to index
            }
            // 處理 alternate
            for (value in annotation.alternate) {
                titleIndexMap[value]?.let { index ->
                    return@indexLet parameter.type.jvmErasure to index
                }
            }
            parameter.type.jvmErasure to -1
        } ?: return@paramMap null
    }
    return data?.map { items ->
        val constructorArgs = typeWithItemIndexList.map argMap@ { (kClass, index) ->
            val argument = items?.getOrNull(index)
            val jvmClass = kClass.java
            val value = when (kClass) {
                String::class -> {
                    argument
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
