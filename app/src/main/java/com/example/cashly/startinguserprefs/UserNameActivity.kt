package com.example.cashly.startinguserprefs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.cashly.R
import com.example.cashly.utils.SharedPreferenceManager

class UserNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_name)

        val etName = findViewById<EditText>(R.id.etName)
        val nextBtn = findViewById<Button>(R.id.nextBtn)

        nextBtn.setOnClickListener {
            val name = etName.text.toString().trim()

            when {
                name.isEmpty() -> {
                    etName.error = "Please enter your name"
                    etName.requestFocus()
                }
                !name.matches(Regex("[a-zA-Z ]+")) -> {
                    etName.error = "Name should only contain letters"
                    etName.requestFocus()
                }
                else -> {
                    val sharedPreferenceManager = SharedPreferenceManager(this)
                    sharedPreferenceManager.savePreferredName(name)

                    Toast.makeText(this, "Welcome, $name!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CurrencySelectionActivity::class.java))
                }
            }
        }
    }
}
