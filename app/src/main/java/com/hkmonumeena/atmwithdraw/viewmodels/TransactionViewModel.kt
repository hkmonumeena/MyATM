package com.hkmonumeena.atmwithdraw.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hkmonumeena.atmwithdraw.dao.TransactionDao
import com.hkmonumeena.atmwithdraw.dataclass.Transactions
import com.hkmonumeena.atmwithdraw.helper.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(val mApplication: Application) : AndroidViewModel(mApplication) {
    private var transactionDao: TransactionDao? = null
    private val _transactionLiveData = MutableLiveData<List<Transactions>>()
    val transactionLiveData: LiveData<List<Transactions>> = _transactionLiveData
    private fun addNewTransaction(
        rupee100: Int,
        rupee200: Int,
        rupee500: Int,
        rupee2000: Int,
        totalAmount: Int
    ) {
        transactionDao = AppDatabase.getAppDatabase(mApplication)?.transactionDao()
        CoroutineScope(Dispatchers.IO).launch {
            transactionDao?.insertTransaction(
                Transactions(
                    rupee100,
                    rupee200,
                    rupee500,
                    rupee2000,
                    totalAmount
                )
            )
            showAllTransactions()
        }
    }

    private fun showAllTransactions() {
        transactionDao = AppDatabase.getAppDatabase(mApplication)?.transactionDao()
          CoroutineScope(Dispatchers.IO).launch {
              _transactionLiveData.postValue(transactionDao?.getAllTransactions())
          }

    }

    fun showTransactions() {
        showAllTransactions()
    }

    fun addTransaction(
        rupee100: Int,
        rupee200: Int,
        rupee500: Int,
        rupee2000: Int,
        totalAmount: Int,
    ) {
        addNewTransaction(rupee100, rupee200, rupee500, rupee2000, totalAmount)
    }
}