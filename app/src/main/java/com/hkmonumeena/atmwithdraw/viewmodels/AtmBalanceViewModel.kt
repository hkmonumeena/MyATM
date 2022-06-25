package com.hkmonumeena.atmwithdraw.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hkmonumeena.atmwithdraw.dao.CurrencyDao
import com.hkmonumeena.atmwithdraw.dataclass.CurrencyNote
import com.hkmonumeena.atmwithdraw.helper.AppDatabase
import com.hkmonumeena.atmwithdraw.helper.Craft.putKeyBoolean
import com.hkmonumeena.atmwithdraw.helper.Keys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AtmBalanceViewModel(private val mApplication: Application) : AndroidViewModel(mApplication) {
    private val _getAllNotesLiveData = MutableLiveData<List<CurrencyNote>>()
    val getAllNotesLiveData: LiveData<List<CurrencyNote>> = _getAllNotesLiveData
    private var currencyDao: CurrencyDao? = null
    private fun getNotesFromRoom() {
        currencyDao = AppDatabase.getAppDatabase(mApplication)?.notesDao()
        CoroutineScope(Dispatchers.IO).launch {
            _getAllNotesLiveData.postValue(currencyDao?.getAllCurrencyNotes())
        }
    }

    private fun insertNotesInToRoom() {
        currencyDao = AppDatabase.getAppDatabase(mApplication)?.notesDao()
        CoroutineScope(Dispatchers.IO).launch {
            val insert100Rs = CurrencyNote(100, 75, 1)
            val insert200Rs = CurrencyNote(200, 50, 2)
            val insert500Rs = CurrencyNote(500, 25, 3)
            val insert2000Rs = CurrencyNote(2000, 10, 4)
            currencyDao?.insertCurrencyNote(insert100Rs)
            currencyDao?.insertCurrencyNote(insert200Rs)
            currencyDao?.insertCurrencyNote(insert500Rs)
            currencyDao?.insertCurrencyNote(insert2000Rs)
            mApplication.putKeyBoolean(Keys.isNoteInserted, true)
            _getAllNotesLiveData.postValue(currencyDao?.getAllCurrencyNotes())
        }
    }

    private fun updateNoteCountInToRoom(noteId: Int, deductedCount: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao = AppDatabase.getAppDatabase(mApplication)?.notesDao()
            val note = currencyDao?.getNoteById(noteId)
            note?.count = note?.count?.minus(deductedCount)!!
            currencyDao?.updateNote(note)
            _getAllNotesLiveData.postValue(currencyDao?.getAllCurrencyNotes())
        }
    }

    fun updateNoteCount(noteId:Int,deductedCount:Int ) {
        updateNoteCountInToRoom(noteId,deductedCount)
    }

    fun insertNotes() {
        insertNotesInToRoom()
    }

    fun getAvailableNotes() {
        getNotesFromRoom()
    }

}