package com.example.demo.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.util.LogPrinter
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.example.demo.R
import com.example.demo.views.utils.ResourceUtils

class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.i("yyyyyyyyy", "MyService: onCreate")
        Looper.getMainLooper().dump(LogPrinter(Log.VERBOSE, "yyyyyyy"), "PREFIX")
        Thread.sleep(10000)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Thread.sleep(10000)
        Log.i("yyyyyyyyy", "MyService: onStartCommand")
        startForeground(123, getNotification(this))
        return super.onStartCommand(intent, flags, startId)
    }

    fun getNotification(context: Context): Notification? {
        val res = context.resources
        createNotificationChannel(context, "copy_translate",
            R.string.action_settings,
            R.string.signature_title)
        val builder = NotificationCompat.Builder(
            context, "copy_translate")
        builder.setSmallIcon(R.drawable.ic_close_dark_24dp)
            .setColor(ResourceUtils.getColor(R.color.colorPrimary))
            .setShowWhen(false)
            .setContentTitle(res.getString(R.string.signature_title))
            .setContentText(res.getString(R.string.signature_title))
        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_NO_CLEAR or Notification.FLAG_ONGOING_EVENT
        return notification
    }

    fun createNotificationChannel(context: Context, channelId: String,
                                  @StringRes nameRes: Int,
                                  @StringRes descRes: Int) { // Android 8.0 以上支持自定义通知的渠道ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // notificationManager 为空，或者已经创建过渠道了
            if (notificationManager.getNotificationChannel(channelId) != null) {
                return
            }
            // 创建快速查词通知的渠道
            var importance = NotificationManager.IMPORTANCE_LOW
            if ("hindict_push" == channelId) {
                importance = NotificationManager.IMPORTANCE_DEFAULT
            }
            val channel = NotificationChannel(channelId,
                context.getString(nameRes),
                importance)
            channel.description = context.getString(descRes)
            channel.setShowBadge(false)
            channel.enableLights(false)
            notificationManager.createNotificationChannel(channel)
        }
    }
}