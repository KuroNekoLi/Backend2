package com.cmoney.backend2.ocean.service.api.variable

/**
 * 跟社團的關係
 */
enum class Relation(val value : Int){

    /**
     * 我加入的社團(包含建立的、加入的、有管理權限的)
     */
    AllClub(3),

    /**
     * 我建立的社團
     */
    ClubCreated(4);
}