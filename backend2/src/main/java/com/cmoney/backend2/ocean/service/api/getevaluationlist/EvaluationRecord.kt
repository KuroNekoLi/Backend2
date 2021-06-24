package com.cmoney.backend2.ocean.service.api.getevaluationlist

/**
 * 評價紀錄資訊
 */
import com.google.gson.annotations.SerializedName

data class EvaluationRecord(
    /**
     * 評價內容
     */
    @SerializedName("Content")
    val content: String?,
    /**
     * 評價時間
     */
    @SerializedName("EvaluationTime")
    val evaluationTime: Long?,
    /**
     * 評價者頻道編號
     */
    @SerializedName("EvaluatorChannelId")
    val evaluatorChannelId: Long?,
    /**
     * 評價者頭像
     */
    @SerializedName("EvaluatorImage")
    val evaluatorImage: String?,
    /**
     * 評價者名稱
     */
    @SerializedName("EvaluatorName")
    val evaluatorName: String?,
    /**
     * 會員評價分數
     */
    @SerializedName("Score")
    val score: Int?
)