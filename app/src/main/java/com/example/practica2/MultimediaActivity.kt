package com.example.practica2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MultimediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia)

        val botonMedia: Button = findViewById(R.id.btnMedia)
        val botonVideo: Button = findViewById(R.id.btnVideo)

        botonMedia.setOnClickListener {
            val intent = Intent(applicationContext, MediaActivity::class.java)
            startActivity(intent)
        }
        botonVideo.setOnClickListener {
            val intent = Intent(applicationContext, VideoActivity::class.java)
            startActivity(intent)
        }
    }
}