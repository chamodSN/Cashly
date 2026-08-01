package com.example.cashly.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Only stores lightweight user preferences now (name, currency, budget,
 * onboarding flag). Transaction storage lives in Room — see TransactionRepository.
 */
class SharedPreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun savePreferredCurrency(currency: String, symbol: String) {
        sharedPreferences.edit()
            .putString("preferred_currency", currency)
            .putString("currency_symbol", symbol)
            .apply()
    }

    fun getPreferredCurrency(): String =
        sharedPreferences.getString("preferred_currency", "USD - US Dollar") ?: "USD - US Dollar"

    fun getCurrencySymbol(): String =
        sharedPreferences.getString("currency_symbol", "$") ?: "$"

    fun saveBudget(budget: Float) {
        sharedPreferences.edit().putFloat("user_budget", budget).apply()
    }

    fun getBudget(): Float = sharedPreferences.getFloat("user_budget", 0f)

    fun savePreferredName(name: String) {
        sharedPreferences.edit().putString("preferred_name", name).apply()
    }

    fun getPreferredName(): String = sharedPreferences.getString("preferred_name", "") ?: ""

    fun setInitialSetupFlag(isDone: Boolean) {
        sharedPreferences.edit().putBoolean("is_initial_setup_done", isDone).apply()
    }

    fun getInitialSetupFlag(): Boolean = sharedPreferences.getBoolean("is_initial_setup_done", false)
}