package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal class MemberProfileGraphQLRequestFieldsBuilder(
    private val queryParams: MemberProfileQueryParams
) {
    fun build(): String {
        val sb = StringBuilder()
        sb.append("{")
        sb.appendField(queryParams.pCoin)
        sb.appendField(queryParams.name)
        sb.appendField(queryParams.nickname)
        sb.appendField(queryParams.gender)
        sb.appendField(queryParams.birthday)
        sb.appendField(queryParams.address)
        sb.appendField(queryParams.signupDate)
        sb.appendField(queryParams.bio)
        sb.appendField(queryParams.contactEmail)
        sb.appendField(queryParams.image)
        sb.appendField(queryParams.city)
        sb.appendField(queryParams.education)
        sb.appendField(queryParams.profession)
        sb.appendField(queryParams.investmentExperience)
        sb.appendField(queryParams.investmentProperty)
        sb.appendField(queryParams.investmentTools)
        sb.appendField(queryParams.customerId)
        sb.appendParent(queryParams.account, queryParams.account?.parentField) { account ->
            appendField(account.email)
            appendParent(account.cellphone, account.cellphone?.parentField) { cellphone ->
                appendField(cellphone.code)
                appendField(cellphone.number)
            }
            appendParent(account.facebook, account.facebook?.parentField) { facebook ->
                appendField(facebook.fbId)
                appendField(facebook.email)
                appendField(facebook.name)
            }
            appendField(account.appleId)
            appendField(account.guestId)
        }
        sb.appendParent(queryParams.levelInfo, queryParams.levelInfo?.parentField) { levelInfo ->
            appendField(levelInfo.exp)
            appendField(levelInfo.level)
            appendField(levelInfo.levelExp)
            appendField(levelInfo.levelExpToNext)
        }
        sb.appendParent(queryParams.badges, queryParams.badges?.parentField) { badges ->
            appendField(badges.badgeId)
            appendField(badges.isEquipped)
            appendField(badges.hasRead)
        }
        sb.append(" ")
        sb.append("}")
        return sb.toString()
    }

    private inline fun <reified T>StringBuilder.appendParent(
        params: T?,
        parentField: MemberProfileField?,
        block: StringBuilder.(T) -> Unit
    ) {
        params ?: return
        parentField ?: return
        this.append(" ")
        this.append(parentField.value)
        this.append("{")
        this.block(params)
        this.append(" ")
        this.append("}")
    }

    private fun StringBuilder.appendField(field: MemberProfileField?) {
        field ?: return
        this.append(" ")
        this.append(field.value)
    }
}