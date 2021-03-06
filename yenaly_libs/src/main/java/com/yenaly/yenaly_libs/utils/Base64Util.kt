@file:Suppress("unused")
@file:JvmName("Base64Util")

package com.yenaly.yenaly_libs.utils

import android.util.Base64

/**
 * Base64加密
 */
fun String.encodeToStringByBase64(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)
}

/**
 * Base64解密
 */
fun String.decodeToStringByBase64(): String {
    return String(Base64.decode(this.toByteArray(), Base64.DEFAULT))
}