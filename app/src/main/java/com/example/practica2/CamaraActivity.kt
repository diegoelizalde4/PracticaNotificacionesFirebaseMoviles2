package com.example.practica2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CamaraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        val btnLector: Button = findViewById(R.id.btnLector)
        val btnFoto: Button = findViewById(R.id.btnFoto)

        btnLector.setOnClickListener {
            startActivity(Intent(this, LectorActivity::class.java))
        }
        btnFoto.setOnClickListener {
            startActivity(Intent(this, FotoActivity::class.java))
        }
    }
}