package com.cmoney.backend2.profile.service.api.queryotherprofile

import com.cmoney.backend2.profile.extension.appendField

internal class OtherMemberProfileGraphQLRequestFieldsBuilder(
    private val queryParams: OtherMemberProfileQueryParams
) {
    fun build(): String {
        val sb = StringBuilder()
        sb.append("{")
        sb.appendField(queryParams.badges)
        sb.appendField(queryParams.bio)
        sb.appendField(queryParams.communityRoles)
        sb.appendField(queryParams.id)
        sb.appendField(queryParams.image)
        sb.appendField(queryParams.isBindingCellphone)
        sb.appendField(queryParams.level)
        sb.appendField(queryParams.nickname)
        sb.append(" ")
        sb.append("}")
        return sb.toString()
    }
}