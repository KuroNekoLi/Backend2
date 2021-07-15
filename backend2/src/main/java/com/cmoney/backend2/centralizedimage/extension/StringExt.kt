package com.cmoney.backend2.centralizedimage.extension

/**
 * 取得60px的縮圖網址
 *
 * @return
 */
fun String?.to60pxUrl() : String?{
    val original = this
    return if (original == null){
        original
    } else {
        original + "_xs"
    }
}

/**
 * 取得180px的縮圖網址
 *
 * @return
 */
fun String?.to180pxUrl() : String?{
    val original = this
    return if (original == null){
        original
    } else {
        original + "_s"
    }
}

/**
 * 取得360px的縮圖網址
 *
 * @return
 */
fun String?.to360pxUrl() : String?{
    val original = this
    return if (original == null){
        original
    } else {
        original + "_m"
    }
}

/**
 * 取得480px的縮圖網址
 *
 * @return
 */
fun String?.to480pxUrl() : String?{
    val original = this
    return if (original == null){
        original
    } else {
        original + "_l"
    }
}

/**
 * 取得720px的縮圖網址
 *
 * @return
 */
fun String?.to720pxUrl() : String?{
    val original = this
    return if (original == null){
        original
    } else {
        original + "_xl"
    }
}