package com.cmoney.backend2.forumocean.service.api.variable.request

/**
 * 通知類型的列舉
 *
 * @property typeString
 */
enum class NotifyType(val typeString : String) {
    CreateComment("createComment"),
    NewComment("newComment"),
    ReactionArticle("reactionArticle"),
    ReactionComment("reactionComment"),
    Donate("donate"),
    CreateQuestionComment("createQuestionComment"),
    Interest("interest"),
    AnswerForCreator("answerForCreator"),
    AnswerForInterest("answerForInterest"),
    AnswerForSelected("answerForSelected"),
    CreateArticle("createArticle"),
    CreateQuestionArticle("createQuestionArticle"),
    Follow("follow"),
    CreateGroupArticle("createGroupArticle"),
    ApplyGroup("applyGroup"),
    AgreeJoinGroup("agreeJoinGroup"),
    JoinGroup("joinGroup");

    companion object{
        fun getFromString(type : String) :NotifyType?{
            for (notifyType in values()){
                if (notifyType.typeString == type){
                    return notifyType
                }
            }
            return null
        }
    }
}