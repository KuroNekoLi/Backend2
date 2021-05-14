package com.cmoney.backend2.base.model.exception

import java.io.IOException

/**
 * Indicates the user not authorized.
 */
class UnauthorizedException(override val message: String) : IOException(message)

