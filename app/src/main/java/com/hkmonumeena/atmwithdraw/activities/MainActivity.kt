package com.hkmonumeena.atmwithdraw.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hkmonumeena.atmwithdraw.adapter.RvTransactions
import com.hkmonumeena.atmwithdraw.dao.CurrencyDao
import com.hkmonumeena.atmwithdraw.databinding.ActivityMainBinding
import com.hkmonumeena.atmwithdraw.dataclass.CurrencyNote
import com.hkmonumeena.atmwithdraw.dataclass.Transactions
import com.hkmonumeena.atmwithdraw.helper.AppDatabase
import com.hkmonumeena.atmwithdraw.helper.Craft.getKeyBoolean
import com.hkmonumeena.atmwithdraw.helper.Craft.isValidate
import com.hkmonumeena.atmwithdraw.helper.Craft.showToast
import com.hkmonumeena.atmwithdraw.helper.Keys
import com.hkmonumeena.atmwithdraw.viewmodels.AtmBalanceViewModel
import com.hkmonumeena.atmwithdraw.viewmodels.TransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listOfCurrencyNotes: List<CurrencyNote>
    private val atmBalanceViewModel by viewModels<AtmBalanceViewModel>()
    private val transactionViewModel by viewModels<TransactionViewModel>()
    private lateinit var currencyDao: CurrencyDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currencyDao = AppDatabase.getAppDatabase(this)?.notesDao()!!
        binding.materialButtonWithdraw.setOnClickListener(this)
        showTransaction()
        transactionViewModel.showTransactions()
        atmBalanceViewModel.getAvailableNotes()
        if (getKeyBoolean(Keys.isNoteInserted) == false) {
            atmBalanceViewModel.insertNotes()
        }
        showAvailableNote()

    }

    private fun showAvailableNote() {
        atmBalanceViewModel.getAllNotesLiveData.observe(this) { list ->
            var getTotalAmount = 0
            listOfCurrencyNotes = list
            list.forEach { note ->
                getTotalAmount += note.count * note.noteValue
                when (note.id) {
                    1 -> {
                        binding.textViewHundredRupeeCount.text = note.count.toString()
                    }
                    2 -> {
                        binding.textViewTwoHundredRupeeCount.text = note.count.toString()
                    }
                    3 -> {
                        binding.textViewFiveHundredRupeeCount.text = note.count.toString()
                    }
                    4 -> {
                        binding.textViewTwoThousandRupeeCount.text = note.count.toString()
                    }
                }
            }

            binding.textViewTotalAmount.text = getTotalAmount.toString()
        }

    }

    private fun deductionOfCash(
        variation: IntArray,
        price: Int,
        position: Int
    ): List<Array<Int?>>? {

        val listOfValues = arrayListOf<Int>()
        val listOfAmount = arrayListOf<Int>()
        listOfCurrencyNotes.forEach {
            listOfAmount.add(it.count)
            listOfValues.add(it.noteValue)
        }
        val list: MutableList<Array<Int?>> = ArrayList()
        val value = compute(listOfValues, variation)
        if (value < price) {
            for (i in position until listOfValues.size) {
                if (listOfAmount[i] > variation[i]) {
                    val newvariation = variation.clone()
                    newvariation[i]++
                    val newList = deductionOfCash(newvariation, price, i)
                    if (newList != null) {
                        list.addAll(newList)
                    }
                }
            }
        } else if (value == price) {
            list.add(myCopy(variation))
        }
        return list
    }

    private fun compute(values: ArrayList<Int>, variation: IntArray): Int {
        var ret = 0
        for (i in variation.indices) {
            ret += values[i] * variation[i]
        }
        return ret
    }

    private fun myCopy(ar: IntArray): Array<Int?> {
        val ret = arrayOfNulls<Int>(ar.size)
        for (i in ar.indices) {
            ret[i] = ar[i]
        }
        return ret
    }

    private fun deductAmount(amount: Int) {
        val results = deductionOfCash(
            IntArray(listOfCurrencyNotes.size), amount, 0
        )

        if (results!!.isNotEmpty()) {
            for (i in results.size - 1 until results.size) {
                Log.e("dsklbdsk", "deductAmount: ${results[i].contentToString()}")
                CoroutineScope(Dispatchers.IO).launch {
                    transactionViewModel.addTransaction(
                        results[i][0]!!,
                        results[i][1]!!,
                        results[i][2]!!,
                        results[i][3]!!,
                        amount
                    )
                }
                for (j in results[i].indices) {
                    Log.e("dskhdipsohd", "deductAmount: $j")
                    when (j) {
                        0 -> {
                            if (results[i][j] != 0) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    atmBalanceViewModel.updateNoteCount(1, results[i][j]!!)
                                }
                            }
                        }
                        1 -> {
                            if (results[i][j] != 0) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    atmBalanceViewModel.updateNoteCount(2, results[i][j]!!)
                                }
                            }
                        }
                        2 -> {
                            if (results[i][j] != 0) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    atmBalanceViewModel.updateNoteCount(3, results[i][j]!!)
                                }
                            }
                        }
                        3 -> {
                            if (results[i][j] != 0) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    Log.e("dsnbfdoslohfdos", "deductAmount: ${results[i][j]}")
                                    atmBalanceViewModel.updateNoteCount(4, results[i][j]!!)
                                }
                            }
                        }
                    }
                }
            }

        } else {
            showToast("Cash not available for the selected amount please select another amount.")
        }


    }

    private fun showTransaction() {
        transactionViewModel.transactionLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.rvTransactions.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = RvTransactions(it as ArrayList<Transactions>)
                }
            }
        }

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.materialButtonWithdraw -> {
                try {
                    val getValidFields =
                        isValidate(binding.editTextWithdrawAmount).getValidatedFields()
                    if (getValidFields) {
                        val amount = binding.editTextWithdrawAmount.text.toString().toInt()
                        if (amount % 100 == 0) {
                            deductAmount(amount)
                        } else {
                            showToast("Amount must be multiplication of 100")
                        }
                    }
                } catch (e: NumberFormatException) {
                    showToast("Invalid value selected")
                }

            }
        }
    }

}