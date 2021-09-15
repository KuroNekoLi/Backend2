package com.cmoney.backend2.forumocean.service.api.notify.get

import com.cmoney.backend2.forumocean.service.api.variable.request.NotifyType
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 * 解析通知轉導資訊的方法
 */
object NotifyRedirectInfoParser {

    @Throws(JsonSyntaxException::class)
    fun getRedirectInfo(responseBody: GetNotifyResponseBody,jsonParser : Gson): RedirectInfo?{
        val type = NotifyType.getFromString(responseBody.notifyType ?: return null) ?: return null
        val json = responseBody.redirectInfo ?: return null
        return when (type){
            NotifyType.CreateComment,
            NotifyType.NewComment,
            NotifyType.ReactionComment,
            NotifyType.CreateQuestionComment,
            NotifyType.Interest,
            NotifyType.AnswerForCreator,
            NotifyType.AnswerForInterest,
            NotifyType.AnswerForSelected -> {
                jsonParser.fromJson(json,RedirectInfo.RedirectComment::class.java)
            }
            NotifyType.ReactionArticle,
            NotifyType.Donate,
            NotifyType.CreateArticle,
            NotifyType.CreateQuestionArticle -> {
                jsonParser.fromJson(json,RedirectInfo.RedirectArticle::class.java)
            }
            NotifyType.Follow -> {
                jsonParser.fromJson(json,RedirectInfo.RedirectMember::class.java)
            }
            NotifyType.CreateGroupArticle -> {
                jsonParser.fromJson(json,RedirectInfo.RedirectGroupArticle::class.java)
            }
            NotifyType.ApplyGroup,
            NotifyType.AgreeJoinGroup,
            NotifyType.JoinGroup -> {
                jsonParser.fromJson(json,RedirectInfo.RedirectGroup::class.java)
            }
        }

    }

}