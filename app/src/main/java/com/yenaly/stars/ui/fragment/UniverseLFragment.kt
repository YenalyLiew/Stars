package com.yenaly.stars.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yenaly.stars.R
import com.yenaly.stars.databinding.FragmentChildUniverseBinding
import com.yenaly.stars.ui.adapter.UniverseLAdapter
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyFragment

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 17:41
 * @Description : Description...
 */
class UniverseLFragment : YenalyFragment<FragmentChildUniverseBinding, MainViewModel>() {

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_child_universe
    }

    override fun initViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initData() {
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val uniList = viewModel.uniList.filter { it.isLight }
        binding.recyclerView.adapter = UniverseLAdapter(this, uniList)
    }
}