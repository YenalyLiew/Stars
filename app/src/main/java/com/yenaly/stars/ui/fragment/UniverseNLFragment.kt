package com.yenaly.stars.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yenaly.stars.R
import com.yenaly.stars.databinding.FragmentChildUniverseBinding
import com.yenaly.stars.ui.adapter.UniverseNLAdapter
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyFragment
import com.yenaly.yenaly_libs.utils.isAppDarkMode

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 17:42
 * @Description : Description...
 */
class UniverseNLFragment : YenalyFragment<FragmentChildUniverseBinding, MainViewModel>() {

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

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel.uniListLiveData.observe(viewLifecycleOwner) { allUniList ->
            val uniList = allUniList.filter { !it.isLight }
            binding.recyclerView.adapter = UniverseNLAdapter(this, uniList)
        }
    }
}