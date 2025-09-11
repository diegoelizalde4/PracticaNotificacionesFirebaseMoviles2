package com.example.practica2

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {
    private var videoView: VideoView? = null
    private var mediaController: MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.videoView)
        mediaController = MediaController(this)
        mediaController!!.setAnchorView(videoView)
        //Reproducir un video en especifico
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.video)
        videoView?.setVideoURI(uri)
        videoView?.requestFocus()
        videoView?.start()
        Toast.makeText(this, "Comienza video", Toast.LENGTH_LONG).show()
        videoView?.setMediaController(mediaController)
    }
}