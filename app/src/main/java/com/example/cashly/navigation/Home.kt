package com.example.cashly.navigation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashly.R
import com.example.cashly.adapters.TransactionAdapter
import com.example.cashly.models_entity.Transaction
import com.example.cashly.transactionfragments.AddExpence
import com.example.cashly.transactionfragments.AddIncome
import com.example.cashly.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : Fragment() {

    private lateinit var mainFab: FloatingActionButton
    private lateinit var incomeFab: FloatingActionButton
    private lateinit var expenseFab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    // Shared across all fragments in the activity
    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        mainFab = rootView.findViewById(R.id.mainFab)
        incomeFab = rootView.findViewById(R.id.incomeBtn)
        expenseFab = rootView.findViewById(R.id.expencesBtn)
        recyclerView = rootView.findViewById(R.id.recyclerViewTransactions)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val transactionAdapter = TransactionAdapter { transaction: Transaction ->
            openDetails(transaction)
        }
        recyclerView.adapter = transactionAdapter

        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.updateTransactions(transactions)
        }

        mainFab.setOnClickListener {
            if (incomeFab.visibility == View.VISIBLE) hideFABs() else showFABs()
        }

        incomeFab.setOnClickListener {
            (requireActivity() as BottomNavBar).replaceFragment(AddIncome.newInstance(), addToBackStack = true)
            hideFABs()
        }

        expenseFab.setOnClickListener {
            (requireActivity() as BottomNavBar).replaceFragment(AddExpence.newInstance(), addToBackStack = true)
            hideFABs()
        }

        incomeFab.visibility = View.GONE
        expenseFab.visibility = View.GONE

        return rootView
    }

    private fun openDetails(transaction: Transaction) {
        val fragment = TransactionDetailsFragment.newInstance(transaction.id)
        (requireActivity() as BottomNavBar).replaceFragment(fragment, addToBackStack = true)
    }

    private fun showFABs() {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(incomeFab, "translationY", 0f),
                ObjectAnimator.ofFloat(expenseFab, "translationY", 0f)
            )
            start()
        }
        incomeFab.visibility = View.VISIBLE
        expenseFab.visibility = View.VISIBLE
    }

    private fun hideFABs() {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(incomeFab, "translationY", 500f),
                ObjectAnimator.ofFloat(expenseFab, "translationY", 500f)
            )
            start()
        }
        incomeFab.visibility = View.GONE
        expenseFab.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = Home()
    }
}