package com.android_basic.assignment6.model

import com.android_basic.assignment6.R

/***
 * Created by HoangRyan aka LilDua on 10/6/2023.
 */
class Datasource {
    fun loadSong() : List<Song> {
        return listOf(
            Song(
                R.drawable.img_a_loi ,
                "À Lôi",
                "Double2T, Masew",
                R.raw.song_a_loi
            ),
            Song(
                R.drawable.img_mot_ngay_chang_nang,
                "Một Ngày Chẳng Nắng",
                "Pháo",
                R.raw.song_mot_ngay_chang_nang
            ),
            Song(
                R.drawable.img_wating_for_you,
                "Waiting For You",
                "MONO",
                R.raw.song_waiting_for_you
            ),
            Song(
                R.drawable.img_mot_ngay_chang_nang,
                "Một Ngày Chẳng Nắng",
                "Pháo",
                R.raw.song_mot_ngay_chang_nang
            ),
            Song(
                R.drawable.img_wating_for_you,
                "Waiting For You",
                "MONO",
                R.raw.song_waiting_for_you
            ),
            Song(
                R.drawable.img_a_loi ,
                "À Lôi",
                "Double2T, Masew",
                R.raw.song_a_loi
            ),
            Song(
                R.drawable.img_mot_ngay_chang_nang,
                "Một Ngày Chẳng Nắng",
                "Pháo",
                R.raw.song_mot_ngay_chang_nang
            ),
            Song(
                R.drawable.img_a_loi ,
                "À Lôi",
                "Double2T, Masew",
                R.raw.song_a_loi
            ),
        )
    }
}