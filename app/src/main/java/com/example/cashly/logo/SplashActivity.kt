package com.example.cashly.logo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cashly.R
import com.example.cashly.navigation.BottomNavBar
import com.example.cashly.onboardscreens.OnBoard01
import com.example.cashly.utils.SharedPreferenceManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val isInitialSetupDone = SharedPreferenceManager(this).getInitialSetupFlag()

        if (isInitialSetupDone) {
            // If setup is done, navigate to BottomNavBar after a 3-second delay
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, BottomNavBar::class.java))
                finish() // Optionally close this activity
            }, 3000)
        } else {
            // If setup is not done, navigate to OnBoard01 after a 3-second delay
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, OnBoard01::class.java))
                finish() // Optionally close this activity
            }, 3000)
        }



    }
}