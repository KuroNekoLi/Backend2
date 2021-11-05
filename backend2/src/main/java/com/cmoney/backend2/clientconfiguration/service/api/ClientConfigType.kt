package com.cmoney.backend2.clientconfiguration.service.api

import com.google.gson.annotations.SerializedName

sealed class ClientConfigType{

data class KOL(
    @SerializedName("expert")
    val expert: List<ClientConfigKolInfo>?
) : ClientConfigType()

}