@file:Suppress("unused")

package com.yenaly.yenaly_libs.utils

/**
 * 通过dp获取相应px值
 */
val Number.dp get() = DeviceUtil.dp2px(applicationContext, this.toFloat())

/**
 * 通过sp获取相应px值
 */
val Number.sp get() = DeviceUtil.sp2px(applicationContext, this.toFloat())

/**
 * 获取本地储存状态栏高度px
 */
val statusBarHeight get() = DeviceUtil.getStatusBarHeight(applicationContext)

/**
 * 获取本地储存导航栏高度px
 */
val navBarHeight get() = DeviceUtil.getNavigationBarHeight(applicationContext)