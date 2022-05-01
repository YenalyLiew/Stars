package com.yenaly.stars.ui.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yenaly.stars.R
import com.yenaly.stars.ui.activity.MainActivity

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/05/01 001 11:24
 * @Description : Description...
 */
class NotificationService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val mnm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "114514",
                "Stars",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableLights(true)
            mnm.createNotificationChannel(channel)
        }
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pt = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, "114514")
            .setSmallIcon(R.drawable.ic_uni_2)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("倒计时结束，快回去看看吧！")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pt)
            .build()
        startForeground(1, notification)
    }
}