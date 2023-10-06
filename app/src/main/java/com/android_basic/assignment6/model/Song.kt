package com.android_basic.assignment6.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import java.io.Serializable

/***
 * Created by HoangRyan aka LilDua on 10/6/2023.
 */
data class Song(
    @DrawableRes val image: Int,
    val name: String,
    val singer: String,
    @RawRes val linkMp3: Int
) : Serializable
