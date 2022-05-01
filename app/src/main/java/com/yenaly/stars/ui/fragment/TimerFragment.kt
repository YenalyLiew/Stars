package com.yenaly.stars.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.yenaly.stars.R
import com.yenaly.stars.databinding.FragmentTimerBinding
import com.yenaly.stars.ui.service.NotificationService
import com.yenaly.stars.ui.view.CircularSeekBar
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyFragment
import com.yenaly.yenaly_libs.utils.secondToTimeCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 15:22
 * @Description : Description...
 */
class TimerFragment : YenalyFragment<FragmentTimerBinding, MainViewModel>() {

    private val mottoList = listOf("你好，世界", "Hello World!", "宇宙为你而闪烁")

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_timer
    }

    override fun initViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initData() {
        val random = Random()
        val randMotto = random.nextInt(mottoList.size)
        binding.motto.text = mottoList[randMotto]
        binding.timer.text = binding.seekBar.progress.toLong().secondToTimeCase()
        binding.seekBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.timer.text = progress.toLong().secondToTimeCase()
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {
                seekBar.isTouchEnabled = false
                flow {
                    for (i in seekBar.progress downTo 0) {
                        emit(i)
                        if (i != 0) {
                            delay(1000)
                        }
                    }
                }.flowOn(Dispatchers.Main)
                    .onStart {}
                    .onCompletion {
                        seekBar.isTouchEnabled = true
                        val intent = Intent(activity, NotificationService::class.java)
                        activity?.startService(intent)
                    }
                    .onEach { seekBar.progress = it }
                    .launchIn(lifecycleScope)
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {

            }
        })
    }
}