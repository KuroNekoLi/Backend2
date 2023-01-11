package com.cmoney.backend2.virtualtrading2.web.createaccount

/**
 * 建立帳號回應
 *
 * @property accountId 帳戶編號
 * @property name 名稱
 * @property groupId 競技場編號
 * @property memberId 會員編號
 * @property defaultFunds 預設帳戶資金
 * @property funds 帳戶資金
 * @property isNeedFee 需要收取手續費
 * @property isNeedTax 需要收取交易稅
 * @property canWatch 是否可查看
 * @property isDefault 是否為預設帳戶
 * @property isDelete 是否已刪除
 * @property accountType 現金交易: 1 / 信用交易: 2 / 權證: 4 / 證券: 5 / 期貨: 8 / 選擇權: 16 / 期權: 24
 * @property createTime 創建時間
 * @property updateTime 更新時間
 * @property viewTime 上次查看時間
 * @property accountPayType 免費帳戶: 0 / 道具卡租用: 1 / 道具卡租用已到期: 2 / 競技帳戶: 3 / 競技帳戶凍結: 4
 * @property maxReadSn 未讀數
 * @property isEmail 是否要接收Email
 * @property averageTradingCountInMonth 月平均交易數
 * @property totalPunishment 帳戶懲罰
 * @property tradedWarrantDate 最初交易權證時間
 * @property extendFunds 擴充現金
 * @property stockIncomeLoss 股票損益
 * @property warrantIncomeLoss 權證損益
 * @property futureIncomeLoss 期貨損益
 * @property optionIncomeLoss 選擇權損益
 * @property borrowFunds 帳戶融資金額
 * @property borrowLimit 帳戶融資借貸上限
 *
 */
data class CreateAccountResponse(
    val accountId: Long?,
    val name: String?,
    val groupId: Long?,
    val memberId: Long?,
    val defaultFunds: Double?,
    val funds: Double?,
    val isNeedFee: Boolean?,
    val isNeedTax: Boolean?,
    val canWatch: Boolean?,
    val isDefault: Boolean?,
    val isDelete: Boolean?,
    val accountType: Int?,
    val createTime: String?,
    val updateTime: String?,
    val viewTime: String?,
    val accountPayType: AccountPayType?,
    val maxReadSn: Long?,
    val isEmail: Boolean?,
    val averageTradingCountInMonth: Double?,
    val totalPunishment: Double?,
    val tradedWarrantDate: Int?,
    val extendFunds: Double?,
    val stockIncomeLoss: Double?,
    val warrantIncomeLoss: Double?,
    val futureIncomeLoss: Double?,
    val optionIncomeLoss: Double?,
    val borrowFunds: Double?,
    val borrowLimit: Double?
) {
    sealed class AccountPayType(
        val code: Int
    ) {
        /**
         * 免費帳號
         */
        object Free : AccountPayType(code = 0)

        /**
         * 道具卡租用
         */
        object CardInUse : AccountPayType(code = 1)

        /**
         * 道具卡租用到期
         */
        object CardExpired : AccountPayType(code = 2)

        /**
         * 競技帳戶
         */
        object Sports : AccountPayType(code = 3)

        /**
         * 競技帳戶凍結
         */
        object SportsFreeze : AccountPayType(code = 4)

        companion object {

            fun getAll(): List<AccountPayType> {
                return listOf(
                    Free,
                    CardInUse,
                    CardExpired,
                    Sports,
                    SportsFreeze
                )
            }

            fun valueOf(code: Int): AccountPayType? {
                return getAll().find {
                    it.code == code
                }
            }
        }
    }
}