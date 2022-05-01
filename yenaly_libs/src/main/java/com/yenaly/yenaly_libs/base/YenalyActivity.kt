package com.yenaly.yenaly_libs.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.yenaly.yenaly_libs.base.frame.FrameActivity

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/16 016 20:20
 * @Description : Description...
 */
abstract class YenalyActivity<DB : ViewDataBinding, VM : YenalyViewModel> : FrameActivity() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        setUiStyle()
        super.onCreate(savedInstanceState)
        initSetView(savedInstanceState)
        initData()
        liveDataObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun initSetView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[initViewModel()]
        binding.lifecycleOwner = this
    }

    /**
     * 主题界面风格相关可以在这里设置 (optional)
     */
    open fun setUiStyle() {
    }

    /**
     * Livedata监听位 (optional)
     */
    open fun liveDataObserve() {
    }

    /**
     * 初始化布局文件
     */
    @LayoutRes
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel
     */
    abstract fun initViewModel(): Class<VM>

    /**
     * 初始化数据
     */
    abstract fun initData()
}