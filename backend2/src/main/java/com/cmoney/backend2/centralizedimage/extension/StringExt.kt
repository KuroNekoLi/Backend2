package com.cmoney.backend2.centralizedimage.extension

/**
 * 取得60px的縮圖網址
 *
 * @return
 */
fun String?.to60pxUrl() : String?{
    return this?.plus("_xs")
}

/**
 * 取得180px的縮圖網址
 *
 * @return
 */
fun String?.to180pxUrl() : String?{
    return this?.plus("_s")
}

/**
 * 取得360px的縮圖網址
 *
 * @return
 */
fun String?.to360pxUrl() : String?{
    return this?.plus("_m")
}

/**
 * 取得480px的縮圖網址
 *
 * @return
 */
fun String?.to480pxUrl() : String?{
    return this?.plus("_l")
}

/**
 * 取得720px的縮圖網址
 *
 * @return
 */
fun String?.to720pxUrl() : String?{
    return this?.plus("_xl")
}