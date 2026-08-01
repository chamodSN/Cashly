package com.example.cashly.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.cashly.databinding.FragmentTransactionDetailsBinding
import com.example.cashly.utils.SharedPreferenceManager
import com.example.cashly.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch

class TransactionDetailsFragment : Fragment() {

    private var _binding: FragmentTransactionDetailsBinding? = null
    private val binding get() = _binding!!

    private val transactionViewModel: TransactionViewModel by activityViewModels()
    private var transactionId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false)
        transactionId = arguments?.getInt(ARG_ID) ?: -1

        loadTransaction()

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                val repoTransaction = com.example.cashly.database.AppDatabase
                    .getDatabase(requireContext()).transactionDao().getById(transactionId)
                if (repoTransaction != null) {
                    transactionViewModel.delete(repoTransaction)
                }
                parentFragmentManager.popBackStack()
            }
        }

        binding.btnUpdate.setOnClickListener {
            // Simple inline update: bump nothing by default, real edit UI can be added later.
            // Left as a hook point — currently just returns to the list.
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun loadTransaction() {
        lifecycleScope.launch {
            val transaction = com.example.cashly.database.AppDatabase
                .getDatabase(requireContext()).transactionDao().getById(transactionId)

            if (transaction == null) {
                parentFragmentManager.popBackStack()
                return@launch
            }

            val symbol = SharedPreferenceManager(requireContext()).getCurrencySymbol()
            binding.tvDetailsCategory.text = transaction.category
            binding.tvDetailsAmount.text = "$symbol${String.format("%.2f", transaction.amount)}"
            binding.tvDetailsAccount.text = transaction.title
            binding.tvDetailsDate.text = transaction.date
            binding.tvDetailsType.text = transaction.type.replaceFirstChar { it.uppercase() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ID = "transaction_id"

        fun newInstance(id: Int): TransactionDetailsFragment {
            val fragment = TransactionDetailsFragment()
            fragment.arguments = Bundle().apply { putInt(ARG_ID, id) }
            return fragment
        }
    }
}