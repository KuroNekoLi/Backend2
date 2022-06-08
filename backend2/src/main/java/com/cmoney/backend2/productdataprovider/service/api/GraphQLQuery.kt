package com.cmoney.backend2.productdataprovider.service.api

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class GraphQLQuery(
    @SerializedName("Query", alternate = ["query"])
    val query: String,
    @SerializedName("Variables", alternate = ["variables"])
    val variables: JsonObject
)