package com.cmoney.backend2.chat.service.api.variable

enum class Role: IGetRoleName {

    OWNER {
        override val definitionName: String
            get() = "@owner"
        override val roleName: String
            get() = "創建者"
    },

    LIMIT {
        override val definitionName: String
            get() = "limit"

        override val roleName: String
            get() = "黑名單"
    },

    VISIT {
        override val definitionName: String
            get() = "visit"
        override val roleName: String
            get() = "訪客"
    },

    USER {
        override val definitionName: String
            get() = "user"
        override val roleName: String
            get() = "一般"
    },

    SUPERVISOR {
        override val definitionName: String
            get() = "supervisor"
        override val roleName: String
            get() = "管理員"
    },

    ADMIN {
        override val definitionName: String
            get() = "admin"
        override val roleName: String
            get() = "擁有者"
    },

    DEV {
        override val definitionName: String
            get() = "dev"
        override val roleName: String
            get() = "開發人員"
    };

    companion object {
        fun getRoleByName(role: String): Role {
            for (item in values()) {
                if (item.definitionName.equals(role, ignoreCase = true)) {
                    return item
                }
            }
            return VISIT
        }
    }
}

interface IGetRoleName {

    val definitionName: String

    val roleName: String
}