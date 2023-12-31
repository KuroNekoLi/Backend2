package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption


/**
 * 請加入以下欄位
 * @SerializedName("@value-reportCount")
 * val totalReportCount: Int?
 * @SerializedName("report", alternate = ["hasReport"])
 * val report: Any?
 *
 */
interface ReportInfo {
    /**
     * 總檢舉數
     */
    val totalReportCount: Int?

    /**
     * 是否檢舉
     */
    val report: Any? // TODO: Change this field to boolean after all api migrate to boolean type.
}