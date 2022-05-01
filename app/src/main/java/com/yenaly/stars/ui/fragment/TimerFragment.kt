package com.yenaly.stars.ui.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.yenaly.stars.R
import com.yenaly.stars.databinding.FragmentTimerBinding
import com.yenaly.stars.logic.UniverseRepo
import com.yenaly.stars.ui.activity.MainActivity
import com.yenaly.stars.ui.dialog.SelectBottomDialog
import com.yenaly.stars.ui.service.NotificationService
import com.yenaly.stars.ui.view.CircularSeekBar
import com.yenaly.stars.ui.viewmodel.MainViewModel
import com.yenaly.yenaly_libs.base.YenalyFragment
import com.yenaly.yenaly_libs.utils.getSpValue
import com.yenaly.yenaly_libs.utils.putSpValue
import com.yenaly.yenaly_libs.utils.secondToTimeCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 15:22
 * @Description : Description...
 */
class TimerFragment : YenalyFragment<FragmentTimerBinding, MainViewModel>() {

    private val mottoList = listOf("你好，世界", "Hello World!", "宇宙为你而闪烁")
    private var dialog: SelectBottomDialog? = null

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
        viewModel.uniListLiveData.observe(viewLifecycleOwner) { uni ->
            val uniList = uni.filter { !it.isLight }
            if (uniList.isNotEmpty()) {
                binding.seekBar.visibility = View.VISIBLE
                binding.timer.visibility = View.VISIBLE
                binding.uniName.visibility = View.VISIBLE
                binding.uniAppearance.visibility = View.VISIBLE
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val lastUniName = getSpValue("uni", uniList[0].name)
                        val lastUni = UniverseRepo.getInstance().findByName(lastUniName)
                        lastUni?.let {
                            withContext(Dispatchers.Main) {
                                binding.uniName.text = it.name
                                binding.uniAppearance.setImageResource(it.appearance)
                            }
                        }
                    }
                }
            } else {
                binding.seekBar.visibility = View.GONE
                binding.timer.visibility = View.GONE
                binding.uniName.visibility = View.GONE
                binding.uniAppearance.visibility = View.GONE
            }

            dialog = SelectBottomDialog.newInstance(uniList) { position ->
                putSpValue("uni", uniList[position].name)
                binding.uniName.text = uniList[position].name
                binding.uniAppearance.setImageResource(uniList[position].appearance)
            }
        }

        binding.uniAppearance.setOnClickListener {
            dialog?.show(requireActivity().supportFragmentManager, "SelectDialog")
        }

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
                val progress = seekBar.progress
                flow {
                    for (i in seekBar.progress downTo 0) {
                        emit(i)
                        if (i != 0) {
                            delay(1000)
                        }
                    }
                }.flowOn(Dispatchers.Main)
                    .onStart {
                        val intent = Intent(activity, NotificationService::class.java).apply {
                            putExtra("stop", false)
                        }
                        activity?.startService(intent)
                    }
                    .onCompletion {
                        seekBar.isTouchEnabled = true
                        successNotification()
                        withContext(Dispatchers.IO) {
                            val lastUniName =
                                getSpValue("uni", viewModel.uniList.filter { !it.isLight }[0].name)
                            val lastUni = UniverseRepo.getInstance().findByName(lastUniName)
                            lastUni?.let {
                                it.focusTime += progress / 60F
                                UniverseRepo.getInstance().updateUniverse(it)
                            }
                            val intent = Intent(activity, NotificationService::class.java).apply {
                                putExtra("stop", true)
                            }
                            activity?.startService(intent)
                        }
                    }
                    .onEach { seekBar.progress = it }
                    .launchIn(lifecycleScope)
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {

            }
        })
    }

    private fun successNotification() {
        val mnm = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "114514",
                "Stars",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(false)
            mnm.createNotificationChannel(channel)
        }
        val notificationIntent = Intent(activity, MainActivity::class.java)
        val pt = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(requireContext(), "114514")
            .setSmallIcon(R.drawable.ic_uni_2)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("倒计时结束，快回来看看吧")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pt)
            .build()
        mnm.notify(2, notification)
    }
}