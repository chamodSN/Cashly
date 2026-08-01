package com.example.cashly.startinguserprefs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cashly.R
import com.example.cashly.navigation.BottomNavBar
import com.example.cashly.utils.SharedPreferenceManager

class CurrencySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()
            setContentView(R.layout.activity_currency_selection)

            val currencySelector: Spinner = findViewById(R.id.currencySelector)
            val confirmButton: Button = findViewById(R.id.confirm)

            confirmButton.setOnClickListener {
                try {
                    val selectedCurrency = currencySelector.selectedItem.toString()

                    if (selectedCurrency == "Select Base Currency") {
                        Toast.makeText(this, "Please select a valid currency", Toast.LENGTH_SHORT).show()
                        currencySelector.requestFocus()
                    } else {
                        val currencySymbol = getCurrencySymbol(selectedCurrency)

                        val sharedPreferenceManager = SharedPreferenceManager(this)
                        sharedPreferenceManager.savePreferredCurrency(selectedCurrency, currencySymbol)
                        sharedPreferenceManager.setInitialSetupFlag(true)

                        val intent = Intent(this, BottomNavBar::class.java)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Initialization error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun getCurrencySymbol(currency: String): String {
        return when (currency) {
            "USD - US Dollar" -> "$"
            "EUR - Euro" -> "€"
            "LKR - Sri Lankan Rupee" -> "Rs"
            "INR - Indian Rupee" -> "₹"
            "GBP - British Pound" -> "£"
            "JPY - Japanese Yen" -> "¥"
            "AUD - Australian Dollar" -> "A$"
            "CAD - Canadian Dollar" -> "C$"
            else -> ""
        }
    }
}
