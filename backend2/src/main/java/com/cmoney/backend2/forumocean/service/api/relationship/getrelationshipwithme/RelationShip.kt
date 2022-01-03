package com.cmoney.backend2.forumocean.service.api.relationship.getrelationshipwithme

/**
 * 提供轉換用的類型列舉
 *
 * @property type
 */
enum class RelationShip(val type : Int) {
    /**
     * 0 無
     *
     */
    None(0),

    /**
     * 1 我追蹤的使用者
     */
    Following(1),

    /**
     * 2 追蹤我的使用者
     *
     */
    Follower(2),

    /**
     * 3 互相追蹤
     *
     */
    FollowEachOther(3),

    /**
     * 4 我封鎖的使用者
     *
     */
    Blocking(4),

    /**
     * 5 封鎖我的使用者
     *
     */
    Blocker(5),

    /**
     * 6 互相封鎖
     *
     */
    BlockEachOther(6);

    companion object{

        /**
         * 從Int轉過來
         *
         * @param type
         * @return
         */
        fun fromValue(type : Int?) : RelationShip?{
            for (relationShip in values()){
                if (relationShip.type == type){
                    return relationShip
                }
            }
            return null
        }
    }
}