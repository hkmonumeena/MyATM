package com.hkmonumeena.atmwithdraw.helper


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hkmonumeena.atmwithdraw.dao.CurrencyDao
import com.hkmonumeena.atmwithdraw.dao.TransactionDao
import com.hkmonumeena.atmwithdraw.dataclass.CurrencyNote
import com.hkmonumeena.atmwithdraw.dataclass.Transactions


@Database(
    entities = [CurrencyNote::class,Transactions::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notesDao(): CurrencyDao
    abstract fun transactionDao(): TransactionDao


    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase? {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "atm_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}