package com.example.cashly.utils

import android.content.Context
import com.example.cashly.models_entity.Transaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException

/**
 * Backs up / restores the REAL transaction list (from Room), as JSON,
 * to app-private internal storage. Call these from a coroutine.
 */
class FileManager(private val context: Context) {

    private val gson = Gson()

    fun backupData(transactions: List<Transaction>): Boolean {
        if (transactions.isEmpty()) return false
        return try {
            val file = File(context.filesDir, "backup.json")
            file.writeText(gson.toJson(transactions))
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun restoreData(): List<Transaction>? {
        val file = File(context.filesDir, "backup.json")
        if (!file.exists()) return null
        return try {
            val content = file.bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<Transaction>>() {}.type
            gson.fromJson(content, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}