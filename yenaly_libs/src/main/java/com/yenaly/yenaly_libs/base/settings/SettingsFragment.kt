package com.yenaly.yenaly_libs.base.settings

import android.os.Bundle
import androidx.annotation.XmlRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.preference.PreferenceFragmentCompat

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/17 017 19:26
 * @Description : Description...
 */
abstract class SettingsFragment<VM : ViewModel> : PreferenceFragmentCompat() {

    protected lateinit var viewModel: VM

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(initPreferencesResource(), rootKey)
        viewModel = ViewModelProvider(initViewModelStoreOwner())[initViewModel()]
        initPreferencesVariable(this)
        onPreferencesCreated(savedInstanceState)
        liveDataObserve()
    }

    /**
     * 初始化ViewModel储存域 (optional)
     *
     * 默认值为当前Fragment所属Activity
     */
    open fun initViewModelStoreOwner(): ViewModelStoreOwner = requireActivity()

    /**
     * Livedata监听位 (optional)
     */
    open fun liveDataObserve() {
    }

    /**
     * 初始化xml设置列表
     */
    @XmlRes
    abstract fun initPreferencesResource(): Int

    /**
     * 初始化ViewModel
     */
    abstract fun initViewModel(): Class<VM>

    /**
     * 在此处使用[pref].[findPreference]初始化设置中的变量
     */
    abstract fun initPreferencesVariable(pref: PreferenceFragmentCompat)

    /**
     * 界面与xml设置列表绑定后从此处进行view操作
     */
    abstract fun onPreferencesCreated(savedInstanceState: Bundle?)
}