package com.example.cashly.database

import androidx.lifecycle.LiveData
import com.example.cashly.dao.TransactionDAO
import com.example.cashly.models_entity.Transaction

class TransactionRepository(private val dao: TransactionDAO) {

    val allTransactions: LiveData<List<Transaction>> = dao.getAllTransactions()

    suspend fun insert(transaction: Transaction) = dao.insert(transaction)

    suspend fun update(transaction: Transaction) = dao.update(transaction)

    suspend fun delete(transaction: Transaction) = dao.delete(transaction)

    suspend fun getById(id: Int): Transaction? = dao.getById(id)

    fun getByCategory(category: String): LiveData<List<Transaction>> = dao.getTransactionsByCategory(category)

    fun getByType(type: String): LiveData<List<Transaction>> = dao.getTransactionsByType(type)
}