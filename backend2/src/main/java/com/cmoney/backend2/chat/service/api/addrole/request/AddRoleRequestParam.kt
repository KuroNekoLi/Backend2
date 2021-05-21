package com.cmoney.backend2.chat.service.api.addrole.request

import com.cmoney.backend2.chat.service.api.variable.Role
import com.cmoney.backend2.chat.service.api.variable.Subject

data class AddRoleRequestParam(
       val subject: Subject,
       val subjectId: Long,
       val userId: Long,
       val role: Role
)