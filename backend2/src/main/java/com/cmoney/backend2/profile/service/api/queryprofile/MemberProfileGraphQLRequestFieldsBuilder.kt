package com.cmoney.backend2.profile.service.api.queryprofile

import com.cmoney.backend2.profile.extension.appendField
import com.cmoney.backend2.profile.extension.appendParent

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
}