package com.example.cashly.onboardscreens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cashly.R
import com.example.cashly.startinguserprefs.UserNameActivity

class OnBoard03 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_board03)

        val nextBtn = findViewById<Button>(R.id.getStarted)

        nextBtn.setOnClickListener {

            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
            }
    }


    }