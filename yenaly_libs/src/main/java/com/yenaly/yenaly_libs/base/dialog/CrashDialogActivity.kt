package com.yenaly.yenaly_libs.base.dialog

import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yenaly.yenaly_libs.ActivitiesManager
import com.yenaly.yenaly_libs.R
import com.yenaly.yenaly_libs.base.YenalyActivity
import com.yenaly.yenaly_libs.base.YenalyViewModel
import com.yenaly.yenaly_libs.databinding.YenalyCrashDialogDataBinding
import com.yenaly.yenaly_libs.utils.intentExtra

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/21 021 22:23
 * @Description : Description...
 */
class CrashDialogActivity : YenalyActivity<YenalyCrashDialogDataBinding, YenalyViewModel>() {

    private val yenalyThrowable by intentExtra("yenaly_throwable", "null")

    override fun setUiStyle() {
        setTheme(R.style.YenalyDialog)
    }

    override fun initContentView(savedInstanceState: Bundle?) =
        R.layout.activity_yenaly_crash_dialog

    override fun initViewModel() = YenalyViewModel::class.java

    override fun initData() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error_title)
            .setMessage(yenalyThrowable)
            .setCancelable(false)
            .setPositiveButton(R.string.restart_app) { _, _ ->
                ActivitiesManager.restartAppWithKillingProcess()
            }
            .setNegativeButton(R.string.exit_app) { _, _ ->
                ActivitiesManager.exitAppWithKillingProcess()
            }
            .show()
    }
}