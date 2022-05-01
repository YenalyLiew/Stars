@file:Suppress("unused")

package com.yenaly.yenaly_libs.utils

import android.view.Window
import androidx.annotation.ColorInt

/**
 * 取消当前透明状态栏，再加一个带颜色的状态栏
 *
 * @param color 想要的状态栏颜色
 */
fun Window.addStatusBarWithColor(@ColorInt color: Int) =
    SystemStatusUtil.statusColor(this, color)

/**
 * 获取当前实际状态栏高度
 */
val Window.currentStatusBarHeight get() = SystemStatusUtil.getCurrentStatusBarHeight(this)

/**
 * 获取当前实际导航栏高度
 */
val Window.currentNavBarHeight get() = SystemStatusUtil.getCurrentNavBarHeight(this)

/**
 * 当前状态栏是否可见
 */
val Window.isStatusBarVisible get() = SystemStatusUtil.isStatusBarVisible(this)

/**
 * 当前导航栏是否可见
 */
val Window.isNavBarVisible get() = SystemStatusUtil.isNavBarVisible(this)

/**
 * 是否显示系统栏
 *
 * @param statusBar 显示状态栏
 * @param navBar    显示导航栏
 */
fun Window.showSystemBar(statusBar: Boolean, navBar: Boolean) {
    SystemStatusUtil.showSystemBar(this, statusBar, navBar)
}

/**
 * 是否将系统栏图标切换成亮色模式
 *
 * @param statusBar 设置状态栏图标为亮色模式
 * @param navBar    设置导航栏图标为亮色模式
 */
fun Window.setSystemBarIconLightMode(statusBar: Boolean, navBar: Boolean) {
    SystemStatusUtil.setSystemBarIconLightMode(this, statusBar, navBar)
}

/**
 * 当前软键盘是否可见
 *
 * @return 是否可见
 */
val Window.isImeVisible get() = SystemStatusUtil.isImeVisible(this)

/**
 * 是否显示软键盘
 *
 * @param ime    是否显示软键盘
 */
fun Window.showIme(ime: Boolean) {
    SystemStatusUtil.showIme(this, ime)
}

/**
 * 当前是否为夜间模式
 */
val isAppDarkMode get() = SystemStatusUtil.isAppDarkMode(applicationContext)