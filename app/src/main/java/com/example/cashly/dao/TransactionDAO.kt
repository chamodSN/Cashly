package com.example.cashly.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cashly.models_entity.Transaction

@Dao
interface TransactionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Transaction?

    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY id DESC")
    fun getTransactionsByCategory(category: String): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY id DESC")
    fun getTransactionsByType(type: String): LiveData<List<Transaction>>
}