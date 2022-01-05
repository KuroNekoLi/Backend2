package com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo

import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData.Builder
import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField
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
    private val bio : String?,
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
        private val bio : String? = null,
        private var image: String? = null,
        private var city: City? = null,
        private var education: Education? = null,
        private var profession: String? = null,
        private var investmentExperience: String? = null,
        private var investmentProperty: String? = null,
        private var investmentTools: List<String>? = null
    ) {
        fun build() = MutationData(
                name = name,
                nickname = nickname,
                gender = gender,
                birthday = birthday,
                address = address,
                bio = bio,
                image = image,
                city = city?.text,
                education = education?.text,
                profession = profession,
                investmentExperience = investmentExperience,
                investmentProperty = investmentProperty,
                investmentTools = investmentTools
        )
    }

    /**
     * 取得[MemberProfileField]對應的更新欄位值，以Json格式
     *
     * @return
     */
    fun toJsonString(): String {
        val jsonObject = JSONObject()
        if (name != null) {
            jsonObject.put(MemberProfileField.NAME.value, name)
        }
        if (nickname != null) {
            jsonObject.put(MemberProfileField.NICKNAME.value, nickname)
        }
        if (gender != null) {
            jsonObject.put(MemberProfileField.GENDER.value, gender)
        }
        if (birthday != null) {
            jsonObject.put(MemberProfileField.BIRTHDAY.value, birthday)
        }
        if (address != null) {
            jsonObject.put(MemberProfileField.ADDRESS.value, address)
        }
        if (image != null) {
            jsonObject.put(MemberProfileField.IMAGE.value, image)
        }
        if (bio != null){
            jsonObject.put(MemberProfileField.BIO.value, bio)
        }
        if (city != null) {
            jsonObject.put(MemberProfileField.CITY.value, city)
        }
        if (education != null) {
            jsonObject.put(MemberProfileField.EDUCATION.value, education)
        }
        if (profession != null) {
            jsonObject.put(MemberProfileField.PROFESSION.value, profession)
        }
        if (investmentExperience != null) {
            jsonObject.put(MemberProfileField.INVESTMENT_EXPERIENCE.value, investmentExperience)
        }
        if (investmentProperty != null) {
            jsonObject.put(MemberProfileField.INVESTMENT_PROPERTY.value, investmentProperty)
        }
        if (investmentTools != null) {
            jsonObject.put(MemberProfileField.INVESTMENT_TOOLS.value, investmentTools)
        }
        return jsonObject.toString()
    }

    /**
     * 取得API request需要的GraphQL-[MemberProfileField]字串，中間以空白間隔
     *
     * EX: name nickname gender...
     *
     * @return
     */
    fun getFieldsString(): String {
        val fieldSet = mutableSetOf<MemberProfileField>()
        if (name != null) {
            fieldSet.add(MemberProfileField.NAME)
        }
        if (nickname != null) {
            fieldSet.add(MemberProfileField.NICKNAME)
        }
        if (gender != null) {
            fieldSet.add(MemberProfileField.NICKNAME)
        }
        if (birthday != null) {
            fieldSet.add(MemberProfileField.BIRTHDAY)
        }
        if (address != null) {
            fieldSet.add(MemberProfileField.ADDRESS)
        }
        if (image != null) {
            fieldSet.add(MemberProfileField.IMAGE)
        }
        if (bio != null){
            fieldSet.add(MemberProfileField.BIO)
        }
        if (city != null) {
            fieldSet.add(MemberProfileField.CITY)
        }
        if (education != null) {
            fieldSet.add(MemberProfileField.EDUCATION)
        }
        if (profession != null) {
            fieldSet.add(MemberProfileField.PROFESSION)
        }
        if (investmentExperience != null) {
            fieldSet.add(MemberProfileField.INVESTMENT_EXPERIENCE)
        }
        if (investmentProperty != null) {
            fieldSet.add(MemberProfileField.INVESTMENT_PROPERTY)
        }
        if (investmentTools != null) {
            fieldSet.add(MemberProfileField.INVESTMENT_TOOLS)
        }
        return fieldSet.joinToString(" ") { field ->
            field.value
        }
    }
}