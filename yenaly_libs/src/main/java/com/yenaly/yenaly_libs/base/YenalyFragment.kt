package com.yenaly.yenaly_libs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.yenaly.yenaly_libs.base.frame.FrameFragment
import com.yenaly.yenaly_libs.utils.application

/**
 * @ProjectName : YenalyModule
 * @Author : Yenaly Liew
 * @Time : 2022/04/16 016 20:25
 * @Description : Description...
 */
abstract class YenalyFragment<DB : ViewDataBinding, VM : YenalyViewModel> : FrameFragment() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initSetView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        liveDataObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    private fun initSetView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding = DataBindingUtil.inflate(
            inflater,
            initContentView(inflater, container, savedInstanceState),
            container,
            false
        )

        viewModel = ViewModelProvider(
            initViewModelStoreOwner(),
            ViewModelProvider.AndroidViewModelFactory(application)
        )[initViewModel()]
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
     * 初始化布局文件
     */
    @LayoutRes
    abstract fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int

    /**
     * 初始化ViewModel
     */
    abstract fun initViewModel(): Class<VM>

    /**
     * 初始化数据
     */
    abstract fun initData()
}