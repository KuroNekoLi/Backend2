package com.cmoney.backend2.profile.extension

import com.cmoney.backend2.profile.service.api.profilefield.MemberProfileField

internal inline fun <reified T>StringBuilder.appendParent(
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

internal fun StringBuilder.appendField(field: MemberProfileField?) {
    field ?: return
    this.append(" ")
    this.append(field.value)
}