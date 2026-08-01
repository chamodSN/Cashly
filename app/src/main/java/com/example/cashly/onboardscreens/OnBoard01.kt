package com.example.cashly.onboardscreens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cashly.R

class OnBoard01 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_board01)

        val nextBtn = findViewById<Button>(R.id.nextOB1)

        nextBtn.setOnClickListener{

            val intent = Intent(this, OnBoard02::class.java)
            startActivity(intent)
        }
    }
}