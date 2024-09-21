package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.setVolume(1f, 1f)
        totalTime = mediaPlayer.duration

        binding.play.setOnClickListener {
            mediaPlayer.start()
        }
        binding.pause.setOnClickListener {
            mediaPlayer.pause()
        }
        binding.stop.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }

        //When user changes time stamp, reflect changes

        binding.seekbarMusic.max = totalTime
        binding.seekbarMusic.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        // change the seekbar position
        val handler = Handler()
        handler.postDelayed( object : Runnable{
            override fun run() {
                try {
                binding.seekbarMusic.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 1000)
                }catch (exception : java.lang.Exception){
                    binding.seekbarMusic.progress = 0
                }
            }
        },0)
    }
}