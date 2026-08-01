package com.example.cashly.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cashly.R
import com.example.cashly.databinding.ActivityBottomNavBarBinding

class BottomNavBar : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(Home())
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.profile -> replaceFragment(Profile())
                R.id.analytics -> replaceFragment(Analytics())
            }
            true
        }
    }

    // Public so child fragments (Home) can push AddIncome/AddExpence/Details
    // into the SAME container this activity owns.
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutForNavBar, fragment)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}