/**
 * 委託狀態
 *
 * @property code 狀態碼
 *
 */
sealed class DelegateStatus(
    val code: Int
) {
    /**
     * 無定義
     */
    object None : DelegateStatus(code = 0)

    /**
     * 委託送出
     */
    object OrderSend : DelegateStatus(code = 1)

    /**
     * 委託成功
     */
    object OrderOK : DelegateStatus(code = 2)

    /**
     * 已成交
     */
    object DealDone : DelegateStatus(code = 3)

    /**
     * 部分成交
     */
    object PartOfOK : DelegateStatus(code = 4)

    /**
     * 部分取消
     */
    object PartOfHandCancel : DelegateStatus(code = 5)

    /**
     * 委託失敗
     */
    object InventoryNotEnough : DelegateStatus(code = 6)

    /**
     * 委託失敗
     */
    object CashNotEnough : DelegateStatus(code = 7)

    /**
     * 改單成功
     */
    object CutOK : DelegateStatus(code = 8)

    /**
     * 已刪單
     */
    object DeleteOk : DelegateStatus(code = 9)

    /**
     * 刪單失敗
     */
    object DeleteError : DelegateStatus(code = 10)

    /**
     * 改單失敗
     */
    object CutError : DelegateStatus(code = 11)

    /**
     * 委託價超過漲跌停價格
     */
    object OrderPriceOver : DelegateStatus(code = 12)

    /**
     * 停止融券
     */
    object StockCantShortSelling : DelegateStatus(code = 13)

    /**
     * 停止融資
     */
    object StockCantMargin : DelegateStatus(code = 14)

    /**
     * ...
     */
    object UnderProcessing : DelegateStatus(code = 15)

    /**
     * 股票不存在或今日停止交易
     */
    object StockCeaseTrading : DelegateStatus(code = 16)

    /**
     * 信用借貸超額
     */
    object OverBorrowLimit : DelegateStatus(code = 17)

    /**
     * 預約單成功
     */
    object NewOrder : DelegateStatus(code = 20)

    /**
     * 預約單全部取消
     */
    object NewOrderCancelAll : DelegateStatus(code = 21)

    /**
     * 預約單部份取消
     */
    object NewOrderCancel : DelegateStatus(code = 22)

    /**
     * 委託結束
     */
    object OrderEnd : DelegateStatus(code = 50)

    /**
     * 無此委託書號
     */
    object DecNone : DelegateStatus(code = 151)

    /**
     * 無此委託書號
     */
    object DelNone : DelegateStatus(code = 152)

    /**
     * 減量股數大於未成交股數
     */
    object DecLess : DelegateStatus(code = 157)

    /**
     * 委託書號重覆
     */
    object MultiOrd : DelegateStatus(code = 160)

    /**
     * 無剩餘未成交股數
     */
    object DelNoAct : DelegateStatus(code = 161)

    /**
     * 預約單轉換錯誤
     */
    object PreOrderError : DelegateStatus(code = 254)

    /**
     * 委託失敗
     */
    object SystemError : DelegateStatus(code = 255)

    companion object {

        fun getAll(): List<DelegateStatus> {
            return listOf(
                None,
                OrderSend,
                OrderOK,
                DealDone,
                PartOfOK,
                PartOfHandCancel,
                InventoryNotEnough,
                CashNotEnough,
                CutOK,
                DeleteOk,
                DeleteError,
                CutError,
                OrderPriceOver,
                StockCantShortSelling,
                StockCantMargin,
                UnderProcessing,
                StockCeaseTrading,
                OverBorrowLimit,
                NewOrder,
                NewOrderCancelAll,
                NewOrderCancel,
                OrderEnd,
                DecNone,
                DelNone,
                DecLess,
                MultiOrd,
                DelNoAct,
                PreOrderError,
                SystemError
            )
        }

        fun valueOf(code: Int): DelegateStatus? {
            return getAll().find { it.code == code }
        }
    }
}