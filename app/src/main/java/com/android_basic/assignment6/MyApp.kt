package com.android_basic.assignment6

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/***
 * Created by HoangRyan aka LilDua on 10/7/2023.
 */
class MyApp : Application() {
    companion object {
        const val CHANNEL_ID = "CHANNEL_MUSIC"
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val musicChannel = NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager: NotificationManager = getSystemService(NotificationManager::class.java)
                if(manager != null){
                    manager.createNotificationChannel(musicChannel)
                }
            }
        }
    }
}