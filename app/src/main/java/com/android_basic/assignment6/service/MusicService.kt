package com.android_basic.assignment6.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android_basic.assignment6.MainActivity
import com.android_basic.assignment6.MyApp
import com.android_basic.assignment6.R
import com.android_basic.assignment6.model.Song
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

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
        val song = intent?.getSerializableExtra(EXTRA_SONG) as? Song
        if (song != null){
            sendNotification(song)
        }

        currentSongUri?.let {
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(applicationContext,it)
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }

        return START_STICKY
    }

    private fun sendNotification(song: Song) {
        Glide.with(this)
            .asBitmap()
            .load(song.image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val notification: Notification = NotificationCompat.Builder(this@MusicService, MyApp.CHANNEL_ID)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.ic_play_circle_outline)
                        .setLargeIcon(resource)
                        .setContentTitle(song.name)
                        .setContentText(song.singer)
                        //add media control button
                        .addAction(R.drawable.ic_skip_previous,"Previous",null)
                        .addAction(R.drawable.ic_pause_circle_outline,"Pause",null)
                        .addAction(R.drawable.ic_skip_next,"Next",null)
                        //apply the media style template
                        .setSilent(true)
                        .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(1 /* #1: pause button*/)
                            )
                        .build()

                    val managerCompat = NotificationManagerCompat.from(this@MusicService)
                    if (ActivityCompat.checkSelfPermission(
                            this@MusicService,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    //start service
                    managerCompat.notify(1, notification)
                    startForeground(1,notification)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Called when the resource is cleared
                }
            })
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_SONG = "song"
        fun startService(context: Context, song: Song) {
            val intent = Intent(context, MusicService::class.java).apply {
                putExtra(EXTRA_SONG, song)
            }
            context.startService(intent)
        }
    }

}