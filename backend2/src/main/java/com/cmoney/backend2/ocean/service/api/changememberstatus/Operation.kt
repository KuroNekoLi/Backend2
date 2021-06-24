package com.cmoney.backend2.ocean.service.api.changememberstatus

/**
 * 1.通過申請成員 2.拒絕申請成員 3.加入黑名單 4.移除黑名單 5.指派幹部 6.卸除幹部 7.踢除成員
 */
enum class Operation(val value : Int){
    /**
     * 1.通過申請成員
     */
    AcceptMember(1),

    /**
     * 2.拒絕申請成員
     */
    RejectMember(2),

    /**
     * .加入黑名單
     */
    MoveBlackList(3),

    /**
     * 4.移除黑名單
     */
    MoveOutBlackList(4),

    /**
     * 5.指派幹部
     */
    AssignManager(5),

    /**
     * 6.卸除幹部
     */
    AssignMember(6),

    /**
     * 7.踢除成員
     */
    KickOutMember(7);
}