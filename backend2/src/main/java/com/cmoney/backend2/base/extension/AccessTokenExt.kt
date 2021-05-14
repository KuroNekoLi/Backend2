package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.request.AccessToken

/**
 * 創建Header的Authorization Bear
 *
 */
fun AccessToken.createAuthorizationBearer(): String = "Bearer ${this.originContent}"