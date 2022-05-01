package com.yenaly.stars.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yenaly.stars.ui.fragment.UniverseLFragment
import com.yenaly.stars.ui.fragment.UniverseNLFragment

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 17:37
 * @Description : Description...
 */
class UniverseVpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UniverseLFragment()
            1 -> UniverseNLFragment()
            else -> Fragment()
        }
    }
}