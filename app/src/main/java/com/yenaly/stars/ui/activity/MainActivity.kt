package com.yenaly.stars.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yenaly.stars.R
import com.yenaly.stars.databinding.ActivityMainBinding
import com.yenaly.stars.logic.UniverseRepo
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyActivity
import com.yenaly.yenaly_libs.utils.setSystemBarIconLightMode

class MainActivity : YenalyActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun setUiStyle() {
        super.setUiStyle()
        window.setSystemBarIconLightMode(statusBar = true, navBar = true)
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initData() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fcv) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBnv.setupWithNavController(navController)


        UniverseRepo.getInstance().getAllUniverse().observe(this) { uni ->
            viewModel.uniList.clear()
            viewModel.uniList.addAll(uni)
        }
    }
}