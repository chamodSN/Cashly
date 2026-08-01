package com.example.cashly.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cashly.R
import com.example.cashly.utils.SharedPreferenceManager
import com.example.cashly.viewmodel.TransactionViewModel

class Analytics : Fragment() {

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_analytics, container, false)

        val tvIncome = view.findViewById<TextView>(R.id.tvIncome)
        val tvExpenses = view.findViewById<TextView>(R.id.tvExpenses)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val symbol = SharedPreferenceManager(requireContext()).getCurrencySymbol()

        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            val income = transactions.filter { it.type.equals("income", true) }.sumOf { it.amount }
            val expenses = transactions.filter { it.type.equals("expense", true) }.sumOf { it.amount }
            val balance = income - expenses

            tvIncome.text = "$symbol${String.format("%.2f", income)}"
            tvExpenses.text = "$symbol${String.format("%.2f", expenses)}"
            tvTotal.text = "$symbol${String.format("%.2f", balance)}"
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Analytics()
    }
}
}