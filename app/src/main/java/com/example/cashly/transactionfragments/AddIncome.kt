package com.example.cashly.transactionfragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cashly.R
import com.example.cashly.models_entity.Transaction
import com.example.cashly.viewmodel.TransactionViewModel
import java.util.Calendar

class AddIncome : Fragment() {

    private lateinit var etTitle: EditText
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var btnAddTransaction: Button

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_income, container, false)

        etTitle = view.findViewById(R.id.etTitle)
        etAmount = view.findViewById(R.id.etAmount)
        etDate = view.findViewById(R.id.etDate)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        btnAddTransaction = view.findViewById(R.id.btnAddTransaction)

        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireActivity(),
                { _, year, month, dayOfMonth ->
                    etDate.setText("$dayOfMonth/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnAddTransaction.setOnClickListener { submit() }

        return view
    }

    private fun submit() {
        val title = etTitle.text.toString().trim()
        val amountString = etAmount.text.toString().trim()
        val date = etDate.text.toString().trim()
        val category = spinnerCategory.selectedItem?.toString() ?: ""

        if (title.isEmpty() || amountString.isEmpty() || date.isEmpty() ||
            category.isEmpty() || category == "Select Category"
        ) {
            Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            return
        }

        if (title.length > 30) {
            etTitle.error = "Title is too long (max 30 characters)"
            return
        }

        val amountDouble = amountString.toDoubleOrNull()
        if (amountDouble == null || amountDouble <= 0) {
            etAmount.error = "Enter a valid positive amount"
            return
        }

        val transaction = Transaction(
            title = title,
            amount = amountDouble,
            category = category,
            date = date,
            type = "income"
        )

        transactionViewModel.insert(transaction)
        Toast.makeText(requireContext(), "Income added", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddIncome()
    }
}