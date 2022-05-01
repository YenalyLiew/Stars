@file:Suppress("unused")

package com.yenaly.yenaly_libs.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 *  快捷启动Activity
 *
 *  @param Ava    泛型，继承于Activity
 *  @param values 需要传过去的值
 *  @param flag   flag (optional)
 *  @param extra  附带的bundle (optional)
 */
inline fun <reified Ava : Activity> Activity.startActivity(
    vararg values: Pair<String, Any>,
    flag: Int? = null,
    extra: Bundle? = null
) = startActivity(getIntent<Ava>(flag, extra, *values))

/**
 *  快捷启动Activity
 *
 *  @param Bella  泛型，继承于Activity
 *  @param flag   flag (optional)
 *  @param extra  附带的bundle (optional)
 *  @param values 需要传过去的值
 */
inline fun <reified Bella : Activity> Fragment.startActivity(
    flag: Int? = null,
    extra: Bundle? = null,
    vararg values: Pair<String, Any>
) = activity?.let {
    startActivity(it.getIntent<Bella>(flag, extra, *values))
}

/**
 *  快捷启动Activity
 *
 *  @param Carol  泛型，继承于Activity
 *  @param flag   flag (optional)
 *  @param extra  附带的bundle (optional)
 *  @param values 需要传过去的值
 */
inline fun <reified Carol : Activity> Context.startActivity(
    flag: Int? = null,
    extra: Bundle? = null,
    vararg values: Pair<String, Any>
) = startActivity(getIntent<Carol>(flag, extra, *values))

/**
 * 快捷获取一个携带各种参数的intent
 *
 * @param Diana  泛型，继承于Activity
 * @param flag   flag (optional)
 * @param extra  附带的bundle (optional)
 * @param pairs  需要传过去的值
 */
inline fun <reified Diana : Context> Context.getIntent(
    flag: Int? = null,
    extra: Bundle? = null,
    vararg pairs: Pair<String, Any>
): Intent = Intent(this, Diana::class.java).apply {
    flag?.let { flags = it }
    extra?.let { putExtras(it) }
    pairs.forEach { pair ->
        val name = pair.first
        when (val value = pair.second) {
            is Int -> putExtra(name, value)
            is Byte -> putExtra(name, value)
            is Char -> putExtra(name, value)
            is Short -> putExtra(name, value)
            is Boolean -> putExtra(name, value)
            is Long -> putExtra(name, value)
            is Float -> putExtra(name, value)
            is Double -> putExtra(name, value)
            is String -> putExtra(name, value)
            is CharSequence -> putExtra(name, value)
            is Parcelable -> putExtra(name, value)
            is Array<*> -> putExtra(name, value)
            is ArrayList<*> -> putExtra(name, value)
            is Serializable -> putExtra(name, value)
            is BooleanArray -> putExtra(name, value)
            is ByteArray -> putExtra(name, value)
            is ShortArray -> putExtra(name, value)
            is CharArray -> putExtra(name, value)
            is IntArray -> putExtra(name, value)
            is LongArray -> putExtra(name, value)
            is FloatArray -> putExtra(name, value)
            is DoubleArray -> putExtra(name, value)
            is Bundle -> putExtra(name, value)
            is Intent -> putExtra(name, value)
            else -> {
            }
        }
    }
}

/**
 * 用委托方式获取activity传来的extra
 *
 * @param Eileen 泛型
 * @param name 传值的名字
 */
@Suppress("UNCHECKED_CAST")
fun <Eileen> Activity.intentExtra(name: String) = lazy {
    intent.extras?.get(name) as? Eileen
}

/**
 * 用委托方式获取activity传来的extra
 *
 * @param Eileen 泛型
 * @param name 传值的名字
 * @param default 缺省值
 */
@Suppress("UNCHECKED_CAST")
fun <Eileen> Activity.intentExtra(name: String, default: Eileen) = lazy {
    intent.extras?.get(name) as? Eileen ?: default
}

/**
 * 用委托方式获取activity传来的extra，
 * 若空则直接报错
 *
 * @param Eileen 泛型
 * @param name 传值的名字
 */
@Suppress("UNCHECKED_CAST")
fun <Eileen> Activity.safeIntentExtra(name: String) = lazy {
    val extra = intent.extras?.get(name) as? Eileen
    checkNotNull(extra) { "No intent value for key \"$name\"" }
}

/**
 * 用委托方式获取fragment所属activity传来的extra
 *
 * @param Eileen 泛型
 * @param name 传值的名字
 */
@Suppress("UNCHECKED_CAST")
fun <Eileen> Fragment.activityIntentExtra(name: String) = lazy {
    activity?.intent?.extras?.get(name) as? Eileen
}

/**
 * 用委托方式获取fragment所属activity传来的extra
 *
 * @param Eileen 泛型
 * @param name 传值的名字
 * @param default 缺省值
 */
@Suppress("UNCHECKED_CAST")
fun <Eileen> Fragment.activityIntentExtra(name: String, default: Eileen) = lazy {
    activity?.intent?.extras?.get(name) as? Eileen ?: default
}

/**
 * 用委托方式获取fragment所属activity传来的extra，
 * 若空则
 *
 * @param Eileen 泛型
 * @param name 传值的名字
 */
@Suppress("UNCHECKED_CAST")
fun <Eileen> Fragment.safeActivityIntentExtra(name: String) = lazy {
    val extra = activity?.intent?.extras?.get(name) as? Eileen
    checkNotNull(extra) { "No intent value for key \"$name\"" }
}

infix fun Activity.browse(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}