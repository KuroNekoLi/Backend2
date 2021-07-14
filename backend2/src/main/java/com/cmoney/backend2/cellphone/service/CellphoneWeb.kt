package com.cmoney.backend2.cellphone.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.cellphone.service.api.CellphoneParam
import com.cmoney.backend2.cellphone.service.api.bindcellphone.BindCellphoneResponseBody
import com.cmoney.backend2.cellphone.service.api.checkcellphonebindingverifycode.CheckCellphoneBindingVerifyCodeResponseBody
import com.cmoney.backend2.cellphone.service.api.checkverifycode.CellphoneCheckVerifyCode
import com.cmoney.backend2.cellphone.service.api.forgotpassword.CellphoneForgotPassword
import com.cmoney.backend2.cellphone.service.api.getaccountinfo.AccountInfo
import com.cmoney.backend2.cellphone.service.api.getverifycode.CellphoneGetVerifyCode
import com.cmoney.backend2.cellphone.service.api.register.CellphoneRegister
import com.cmoney.backend2.cellphone.service.api.unbindcellphone.UnbindCellphoneResponseBody
import com.cmoney.backend2.cellphone.service.api.updatepassword.UpdatePasswordResponseBody
import java.security.NoSuchAlgorithmException

interface CellphoneWeb {
    /**
     * 服務1. 取得驗證碼 / 重寄驗證碼
     */
    suspend fun getVerifyCode(cellphoneParam: CellphoneParam): Result<CellphoneGetVerifyCode>

    /**
     * 服務2. 驗證註冊驗證碼
     */
    suspend fun checkVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String
    ): Result<CellphoneCheckVerifyCode>

    /**
     * 服務3. 手機號碼註冊
     */
    @Throws(NoSuchAlgorithmException::class)
    suspend fun registerByCellphone(
        cellphoneParam: CellphoneParam,
        password: String
    ): Result<CellphoneRegister>

    /**
     * 服務5. 忘記密碼 by手機
     */
    suspend fun forgotPasswordForCellphone(cellphoneParam: CellphoneParam): Result<CellphoneForgotPassword>

    /**
     * 服務6. 更改密碼
     */
    @Deprecated("ApiParam no longer required")
    suspend fun updatePassword(
        apiParam: MemberApiParam,
        oldPassword: String,
        oldHasMd5: Boolean,
        newPassword: String
    ): Result<UpdatePasswordResponseBody>

    /**
     * 服務6. 更改密碼
     */
    suspend fun updatePassword(
        oldPassword: String,
        oldHasMd5: Boolean,
        newPassword: String
    ): Result<UpdatePasswordResponseBody>

    /**
     * 服務7. 取得帳號資訊
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAccountInfo(apiParam: MemberApiParam): Result<AccountInfo>

    /**
     * 服務7. 取得帳號資訊
     */
    suspend fun getAccountInfo(): Result<AccountInfo>

    /**
     * 服務8. 觸發手機綁定
     *
     */
    @Deprecated("ApiParam no longer required")
    suspend fun bindCellphone(
        apiParam: MemberApiParam,
        cellphoneParam: CellphoneParam
    ): Result<BindCellphoneResponseBody>

    /**
     * 服務8. 觸發手機綁定
     *
     */
    suspend fun bindCellphone(
        cellphoneParam: CellphoneParam
    ): Result<BindCellphoneResponseBody>

    /**
     * 服務9. 執行手機綁定驗證手機綁定驗證碼
     *
     */
    @Deprecated("ApiParam no longer required")
    suspend fun checkCellphoneBindingVerifyCode(
        apiParam: MemberApiParam,
        cellphoneParam: CellphoneParam,
        verifyCode: String?
    ): Result<CheckCellphoneBindingVerifyCodeResponseBody>

    /**
     * 服務9. 執行手機綁定驗證手機綁定驗證碼
     *
     */
    suspend fun checkCellphoneBindingVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String?
    ): Result<CheckCellphoneBindingVerifyCodeResponseBody>

    /**
     * 服務10. 取消手機綁定
     *
     */
    @Deprecated("ApiParam no longer required")
    suspend fun unbindCellphone(apiParam: MemberApiParam): Result<UnbindCellphoneResponseBody>

    /**
     * 服務10. 取消手機綁定
     *
     */
    suspend fun unbindCellphone(): Result<UnbindCellphoneResponseBody>
}