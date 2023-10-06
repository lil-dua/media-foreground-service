package com.android_basic.assignment6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.android_basic.assignment6.fragment.SongFragment
import com.android_basic.assignment6.service.MusicService

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val fragment = SongFragment()
            supportFragmentManager.commit {
                replace(R.id.fragment_container,fragment)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        val serviceIntent = Intent(this,MusicService::class.java)
        stopService(serviceIntent)
    }

}