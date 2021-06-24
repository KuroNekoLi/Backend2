package com.cmoney.backend2.ocean.service.api.variable


import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("Answer")
    val answer: String?,
    @SerializedName("Question")
    val question: String?,
    @SerializedName("QuestionId")
    val questionId: Long?
)