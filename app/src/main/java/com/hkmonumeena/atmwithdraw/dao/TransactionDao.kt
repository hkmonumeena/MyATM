package com.hkmonumeena.atmwithdraw.dao

import androidx.room.*
import com.hkmonumeena.atmwithdraw.dataclass.Transactions


@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTransaction(transactions: Transactions)

    @Update
    suspend fun updateTransaction(transactions: Transactions)

    @Delete
    suspend fun deleteTransaction(transactions: Transactions)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): List<Transactions>

    @Query("SELECT * FROM transactions WHERE  id==:id")
    fun getTransactionById(id:Int): Transactions

}