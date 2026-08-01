package com.example.cashly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cashly.R
import com.example.cashly.models_entity.Transaction
import com.example.cashly.utils.SharedPreferenceManager

class TransactionAdapter(
    private val onItemClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val transactionList = mutableListOf<Transaction>()

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDay: TextView = view.findViewById(R.id.tvDay)
        val tvDayName: TextView = view.findViewById(R.id.tvDayName)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvCurrencySymbol: TextView = view.findViewById(R.id.tvCurrencySymbol)
        val ivCategory: ImageView = view.findViewById(R.id.ivCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        val context = holder.itemView.context

        try {
            val inputFormat = java.text.SimpleDateFormat("d/M/yyyy", java.util.Locale.getDefault())
            val outputDay = java.text.SimpleDateFormat("dd", java.util.Locale.getDefault())
            val outputDayName = java.text.SimpleDateFormat("EEE", java.util.Locale.getDefault())
            val outputMonthYear = java.text.SimpleDateFormat("MM.yyyy", java.util.Locale.getDefault())

            val date = inputFormat.parse(transaction.date)
            if (date != null) {
                holder.tvDay.text = outputDay.format(date)
                holder.tvDayName.text = outputDayName.format(date)
                holder.tvDate.text = outputMonthYear.format(date)
            } else {
                holder.tvDay.text = "-"
                holder.tvDayName.text = "-"
                holder.tvDate.text = transaction.date
            }
        } catch (e: Exception) {
            holder.tvDay.text = "-"
            holder.tvDayName.text = "-"
            holder.tvDate.text = transaction.date
        }

        holder.tvCategory.text = transaction.category

        val currencySymbol = SharedPreferenceManager(context).getCurrencySymbol()
        holder.tvCurrencySymbol.text = currencySymbol
        holder.tvAmount.text = String.format("%.2f", transaction.amount)

        val colorRes = if (transaction.type.equals("expense", ignoreCase = true)) {
            R.color.red_500
        } else {
            R.color.green_500
        }
        holder.tvAmount.setTextColor(ContextCompat.getColor(context, colorRes))
        holder.tvCurrencySymbol.setTextColor(ContextCompat.getColor(context, colorRes))

        holder.itemView.setOnClickListener { onItemClick(transaction) }
    }

    fun updateTransactions(newList: List<Transaction>) {
        transactionList.clear()
        transactionList.addAll(newList)
        notifyDataSetChanged()
    }
}