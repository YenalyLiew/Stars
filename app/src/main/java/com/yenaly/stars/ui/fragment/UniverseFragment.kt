package com.yenaly.stars.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yenaly.stars.R
import com.yenaly.stars.databinding.FragmentUniverseBinding
import com.yenaly.stars.ui.adapter.UniverseVpAdapter
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyFragment

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 15:22
 * @Description : Description...
 */
class UniverseFragment : YenalyFragment<FragmentUniverseBinding, MainViewModel>() {

    private val uniTab = listOf(R.string.universe, R.string.wait_for_light)

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_universe
    }

    override fun initViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initData() {
        binding.uniVp.adapter = UniverseVpAdapter(this)
        TabLayoutMediator(
            binding.uniTab,
            binding.uniVp
        ) { tab, position -> tab.setText(uniTab[position]) }.attach()
    }
}