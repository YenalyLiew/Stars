package com.yenaly.yenaly_libs.base

import android.content.Intent
import android.os.Looper
import com.yenaly.yenaly_libs.ActivitiesManager
import com.yenaly.yenaly_libs.base.dialog.CrashDialogActivity
import com.yenaly.yenaly_libs.utils.applicationContext
import com.yenaly.yenaly_libs.utils.startActivity
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.concurrent.thread

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/21 021 21:15
 * @Description : Description...
 */
class YenalyCrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    private val mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    companion object {
        @JvmStatic
        @Volatile
        private var yenalyCrashHandler: YenalyCrashHandler? = null

        @JvmStatic
        fun getInstance(): YenalyCrashHandler {
            if (yenalyCrashHandler == null) {
                synchronized(YenalyCrashHandler::class.java) {
                    if (yenalyCrashHandler == null) {
                        yenalyCrashHandler = YenalyCrashHandler()
                    }
                }
            }
            return yenalyCrashHandler!!
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (!handleError(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e)
        } else {
            val errorWriter = StringWriter()
            errorWriter.appendLine("These errors occurred:")
            errorWriter.appendLine()
            e.printStackTrace(PrintWriter(errorWriter))
            applicationContext.startActivity<CrashDialogActivity>(
                flag = Intent.FLAG_ACTIVITY_NEW_TASK,
                values = arrayOf("yenaly_throwable" to errorWriter.toString())
            )
            ActivitiesManager.exitAppWithKillingProcess()
        }
    }

    private fun handleError(throwable: Throwable?): Boolean {
        if (throwable == null) {
            return false
        }

        thread {
            Looper.prepare()
            // TODO
            Looper.loop()
        }
        return true
    }
}