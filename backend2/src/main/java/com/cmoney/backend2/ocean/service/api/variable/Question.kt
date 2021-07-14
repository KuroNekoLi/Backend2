package com.cmoney.backend2.ocean.service.api.variable
import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("AskQuestion")
    val askQuestion: String?,
    @SerializedName("Id")
    val id: Long?,
    @SerializedName("QuestionType")
    val questionType: Int?
)