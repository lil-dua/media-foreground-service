package com.android_basic.assignment6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android_basic.assignment6.R
import com.android_basic.assignment6.model.Song

/***
 * Created by HoangRyan aka LilDua on 10/6/2023.
 */
class SongAdapter(
    private val songList: List<Song>,
    private val itemClickListener: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSong: ImageView = itemView.findViewById(R.id.image_song)
        val textSongName: TextView = itemView.findViewById(R.id.text_song_name)
        val textSingerName: TextView = itemView.findViewById(R.id.text_singer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_container_song,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songList[position]
        holder.imageSong.setImageResource(song.image)
        holder.textSongName.text = song.name
        holder.textSingerName.text = song.singer

        //set action for item click
        holder.itemView.setOnClickListener {
            itemClickListener(song)
        }
    }
}