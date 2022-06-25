package com.hkmonumeena.atmwithdraw.dao

import androidx.room.*
import com.hkmonumeena.atmwithdraw.dataclass.CurrencyNote


@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertCurrencyNote(currencyNote: CurrencyNote)

    @Update
    suspend fun updateNote(currencyNote: CurrencyNote)

    @Delete
    suspend fun deleteNote(currencyNote: CurrencyNote)

    @Query("SELECT * FROM notes")
    fun getAllCurrencyNotes(): List<CurrencyNote>

    @Query("SELECT * FROM notes WHERE  id==:noteId")
    fun getNoteById(noteId:Int): CurrencyNote

}