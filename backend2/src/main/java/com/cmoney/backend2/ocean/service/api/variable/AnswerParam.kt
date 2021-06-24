package com.cmoney.backend2.ocean.service.api.variable


import com.google.gson.annotations.SerializedName

data class AnswerParam(
    @SerializedName("AnswerContent")
    val answerContent: String,
    @SerializedName("QuestionId")
    val questionId: Long
)