package com.android_basic.assignment6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.android_basic.assignment6.R
import com.android_basic.assignment6.adapter.SongAdapter
import com.android_basic.assignment6.databinding.FragmentSongBinding
import com.android_basic.assignment6.model.Datasource
import com.android_basic.assignment6.model.Song

/***
 * Created by HoangRyan aka LilDua on 10/6/2023.
 */
class SongFragment : Fragment() {
    private var binding: FragmentSongBinding? = null
    private lateinit var adapter: SongAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSongBinding.inflate(inflater,container,false)
        binding = fragmentBinding

        val dataSong = Datasource().loadSong()
        adapter = SongAdapter(dataSong) {song ->  showSongDetails(song)}
        binding?.recycleViewSong?.layoutManager = LinearLayoutManager(activity)
        binding?.recycleViewSong?.adapter = adapter

        return fragmentBinding.root
    }

    private fun showSongDetails(song: Song) {
        val fragmentPlay = PlayFragment.newInstance(song)
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragmentPlay)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}