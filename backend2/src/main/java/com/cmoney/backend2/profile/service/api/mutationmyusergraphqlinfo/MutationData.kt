package com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo

import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData.Builder
import com.cmoney.backend2.profile.service.api.variable.GraphQLFieldDefinition
import org.json.JSONObject

/**
 * 更新完結果的物件資料，請使用[Builder]進行物件的創建
 *
 * @property name 名稱
 * @property nickname 你稱
 * @property gender 性別
 * @property birthday 生日
 * @property address 地址
 * @property image 頭像
 * @property city 城市
 * @property education 教育程度
 * @property profession 職業
 * @property investmentExperience 投資經驗
 * @property investmentProperty 投資標的
 * @property investmentTools 投資工具
 */
class MutationData private constructor(
    private val name: String?,
    private val nickname: String?,
    private val gender: String?,
    private val birthday: String?,
    private val address: String?,
    private val image: String?,
    private val city: String?,
    private val education: String?,
    private val profession: String?,
    private val investmentExperience: String?,
    private val investmentProperty: String?,
    private val investmentTools: List<String>?
) {
    data class Builder(
        private var name: String? = null,
        private var nickname: String? = null,
        private var gender: String? = null,
        private var birthday: String? = null,
        private var address: String? = null,
        private var image: String? = null,
        private var city: String? = null,
        private var education: String? = null,
        private var profession: String? = null,
        private var investmentExperience: String? = null,
        private var investmentProperty: String? = null,
        private var investmentTools: List<String>? = null
    ) {
        fun build() = MutationData(
            name,
            nickname,
            gender,
            birthday,
            address,
            image,
            city,
            education,
            profession,
            investmentExperience,
            investmentProperty,
            investmentTools
        )
    }

    /**
     * 取得[GraphQLFieldDefinition]對應的更新欄位值，以Json格式
     *
     * @return
     */
    fun toJsonString(): String {
        val jsonObject = JSONObject()
        if (name != null) {
            jsonObject.put(GraphQLFieldDefinition.Name.value, name)
        }
        if (nickname != null) {
            jsonObject.put(GraphQLFieldDefinition.NickName.value, nickname)
        }
        if (gender != null) {
            jsonObject.put(GraphQLFieldDefinition.Gender.value, gender)
        }
        if (birthday != null) {
            jsonObject.put(GraphQLFieldDefinition.Birthday.value, birthday)
        }
        if (address != null) {
            jsonObject.put(GraphQLFieldDefinition.Address.value, address)
        }
        if (image != null) {
            jsonObject.put(GraphQLFieldDefinition.Image.value, image)
        }
        if (city != null) {
            jsonObject.put(GraphQLFieldDefinition.City.value, city)
        }
        if (education != null) {
            jsonObject.put(GraphQLFieldDefinition.Education.value, education)
        }
        if (profession != null) {
            jsonObject.put(GraphQLFieldDefinition.Profession.value, profession)
        }
        if (investmentExperience != null) {
            jsonObject.put(GraphQLFieldDefinition.InvestmentExperience.value, investmentExperience)
        }
        if (investmentProperty != null) {
            jsonObject.put(GraphQLFieldDefinition.InvestmentProperty.value, investmentProperty)
        }
        if (investmentTools != null) {
            jsonObject.put(GraphQLFieldDefinition.InvestmentTools.value, investmentTools)
        }
        return jsonObject.toString()
    }

    /**
     * 取得API request需要的[GraphQLFieldDefinition]字串
     *
     * @return
     */
    fun getFieldsString(): String {
        val setOfGraphQLFieldDefinition = mutableSetOf<GraphQLFieldDefinition>()
        if (name != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Name)
        }
        if (nickname != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.NickName)
        }
        if (gender != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Gender)
        }
        if (birthday != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Birthday)
        }
        if (address != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Address)
        }
        if (image != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Image)
        }
        if (city != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.City)
        }
        if (education != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Education)
        }
        if (profession != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.Profession)
        }
        if (investmentExperience != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.InvestmentExperience)
        }
        if (investmentProperty != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.InvestmentProperty)
        }
        if (investmentTools != null) {
            setOfGraphQLFieldDefinition.add(GraphQLFieldDefinition.InvestmentTools)
        }
        return setOfGraphQLFieldDefinition.joinToString(" ") { it.value }
    }
}