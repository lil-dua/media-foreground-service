package com.android_basic.assignment6.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android_basic.assignment6.MainActivity
import com.android_basic.assignment6.R

/***
 * Created by HoangRyan aka LilDua on 10/6/2023.
 */
class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private var currentSongUri: Int? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,notificationIntent,
            PendingIntent.FLAG_IMMUTABLE)
        val notification: Notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Foreground Service Channel")
            .setSmallIcon(R.drawable.ic_play_circle_outline)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .build()

        val mangeCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission denied!",Toast.LENGTH_SHORT).show()
        }
        mangeCompat.notify(1, notification)
        startForeground(1,notification)

        currentSongUri?.let {
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(applicationContext,it)
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val EXTRA_SONG = "song"
    }

}