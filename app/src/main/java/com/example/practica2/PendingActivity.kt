package com.example.practica2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PendingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending)

        val messageTextView: TextView = findViewById(R.id.tv_pending_message)

        // Obtener el mensaje del Intent
        val message = intent.getStringExtra("message")
        if (!message.isNullOrEmpty()) {
            messageTextView.text = message
        }
    }
}