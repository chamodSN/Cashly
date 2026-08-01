package com.example.cashly.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.cashly.R
import com.example.cashly.database.AppDatabase
import com.example.cashly.utils.FileManager
import com.example.cashly.utils.SharedPreferenceManager
import com.example.cashly.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch

class Profile : Fragment() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var etName: EditText
    private lateinit var etBudget: EditText
    private lateinit var saveNameButton: Button
    private lateinit var currencySpinner: Spinner
    private lateinit var saveCurrencyButton: Button
    private lateinit var fileManager: FileManager
    private lateinit var backupBtn: Button
    private lateinit var restoreBtn: Button
    private lateinit var budgetBtn: Button

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        etName = view.findViewById(R.id.etName)
        saveNameButton = view.findViewById(R.id.saveName)
        currencySpinner = view.findViewById(R.id.etCurrency)
        saveCurrencyButton = view.findViewById(R.id.saveCurrency)
        budgetBtn = view.findViewById(R.id.budgetBtn)
        etBudget = view.findViewById(R.id.etBudget)
        backupBtn = view.findViewById(R.id.backupBtn)
        restoreBtn = view.findViewById(R.id.restoreBtn)

        fileManager = FileManager(requireContext())
        sharedPreferenceManager = SharedPreferenceManager(requireContext())

        backupBtn.setOnClickListener {
            lifecycleScope.launch {
                val dao = AppDatabase.getDatabase(requireContext()).transactionDao()
                val transactions = transactionViewModel.allTransactions.value ?: emptyList()
                val ok = fileManager.backupData(transactions)
                Toast.makeText(
                    requireContext(),
                    if (ok) "Backup successful" else "Backup failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        restoreBtn.setOnClickListener {
            lifecycleScope.launch {
                val restored = fileManager.restoreData()
                if (restored == null) {
                    Toast.makeText(requireContext(), "No backup file found", Toast.LENGTH_SHORT).show()
                } else {
                    restored.forEach { transactionViewModel.insert(it.copy(id = 0)) }
                    Toast.makeText(
                        requireContext(),
                        "Restore successful! ${restored.size} transactions loaded.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        budgetBtn.setOnClickListener {
            val budgetString = etBudget.text.toString()
            val budget = budgetString.toFloatOrNull()
            if (budget == null || budget < 0) {
                Toast.makeText(requireContext(), "Please enter a valid positive budget", Toast.LENGTH_SHORT).show()
            } else {
                sharedPreferenceManager.saveBudget(budget)
                Toast.makeText(requireContext(), "Budget saved", Toast.LENGTH_SHORT).show()
            }
        }

        saveNameButton.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotBlank()) {
                sharedPreferenceManager.savePreferredName(name)
                Toast.makeText(requireContext(), "Name saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }

        saveCurrencyButton.setOnClickListener {
            val selectedCurrency = currencySpinner.selectedItem.toString()
            if (selectedCurrency == "Select Base Currency") {
                Toast.makeText(requireContext(), "Please select a valid currency", Toast.LENGTH_SHORT).show()
            } else {
                val symbol = getCurrencySymbol(selectedCurrency)
                sharedPreferenceManager.savePreferredCurrency(selectedCurrency, symbol)
                Toast.makeText(requireContext(), "Currency saved", Toast.LENGTH_SHORT).show()
                activity?.recreate()
            }
        }

        loadSavedData()
        return view
    }

    private fun loadSavedData() {
        etName.setText(sharedPreferenceManager.getPreferredName())

        val savedCurrency = sharedPreferenceManager.getPreferredCurrency()
        val currencyArray = resources.getStringArray(R.array.base_currencies_array)
        val index = currencyArray.indexOf(savedCurrency)
        if (index != -1) currencySpinner.setSelection(index)

        val savedBudget = sharedPreferenceManager.getBudget()
        etBudget.setText(if (savedBudget > 0f) savedBudget.toString() else "")
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