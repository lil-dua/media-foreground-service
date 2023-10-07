package com.android_basic.assignment6.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.android_basic.assignment6.R
import com.android_basic.assignment6.databinding.FragmentPlayBinding
import com.android_basic.assignment6.model.Song
import com.android_basic.assignment6.service.MusicService

/***
 * Created by HoangRyan aka LilDua on 10/6/2023.
 */
class PlayFragment: Fragment() {
    private var binding: FragmentPlayBinding? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false

    companion object {
        private const val ARG_SONG = "song"

        fun newInstance(song: Song): PlayFragment {
            val fragment = PlayFragment()
            val args = Bundle()
            args.putSerializable(ARG_SONG, song)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPlayBinding.inflate(inflater,container,false)
        binding = fragmentBinding

        //get data
        val song = arguments?.getSerializable(ARG_SONG) as Song
        binding?.textSongName?.text = song.name
        binding?.imageSong?.setImageResource(song.image)
        binding?.textSingerName?.text = song.singer

        initializeMediaPlayer(song)
        binding?.iconPlay?.setOnClickListener { handlePlayButton() }
        //back button
        binding?.imageBack?.setOnClickListener {activity?.onBackPressed()}
        return fragmentBinding.root
    }

    private fun initializeMediaPlayer(song: Song) {
        startMusicService(song)
        mediaPlayer = MediaPlayer.create(requireContext(),song.linkMp3)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            // Music playback completed, stop the service
            stopMusicService()
            isPlaying = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    //on play click -----------------------------------------------------------------
    private fun handlePlayButton(){
        if(isPlaying) {
            mediaPlayer?.start()
            binding?.iconPlay?.setImageResource(R.drawable.ic_pause_circle_outline)
            isPlaying = false
        }else{
            mediaPlayer?.pause()
            binding?.iconPlay?.setImageResource(R.drawable.ic_play_circle_outline)
            isPlaying = true
        }
    }

    private fun startMusicService(song: Song) {
        MusicService.startService(requireContext(),song)
        val serviceIntent = Intent(requireContext(), MusicService::class.java)
        requireActivity().startService(serviceIntent)
    }

    private fun stopMusicService() {
        val stopIntent = Intent(requireContext(), MusicService::class.java)
        requireActivity().stopService(stopIntent)
    }
}