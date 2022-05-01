package com.yenaly.stars.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yenaly.stars.R
import com.yenaly.stars.databinding.FragmentPersonalBinding
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyFragment
import com.yenaly.yenaly_libs.utils.span.SpannedTextGenerator

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 15:22
 * @Description : Description...
 */
class PersonalFragment : YenalyFragment<FragmentPersonalBinding, MainViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_personal
    }

    override fun initViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initData() {
        SpannedTextGenerator.KotlinBuilder()
            .addText("Yenaly ", isBold = true, isItalic = true, isNewLine = false)
            .addText("Liew ", isItalic = true, isNewLine = false)
            .showIn(binding.username)
        SpannedTextGenerator.KotlinBuilder()
            .addText("What you like, ", isNewLine = false)
            .addText("is your life.", blurRadius = 10F)
            .addText("Bekki kawaii~~", isStrikeThrough = true, isNewLine = false)
            .showIn(binding.motto)
    }
}