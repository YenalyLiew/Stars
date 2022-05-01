package com.yenaly.yenaly_libs.base.settings

import android.os.Bundle
import android.view.MenuItem
import com.yenaly.yenaly_libs.R
import com.yenaly.yenaly_libs.base.YenalyActivity
import com.yenaly.yenaly_libs.base.YenalyViewModel
import com.yenaly.yenaly_libs.databinding.YenalySettingsDataBinding

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/17 017 17:13
 * @Description : Description...
 */
abstract class SettingsActivity<VM : YenalyViewModel> :
    YenalyActivity<YenalySettingsDataBinding, VM>() {

    override fun initContentView(savedInstanceState: Bundle?) = R.layout.activity_yenaly_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container_view, initFragmentContainer())
                .commit()
        }
    }

    override fun initData() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    abstract fun initFragmentContainer(): SettingsFragment<VM>
}