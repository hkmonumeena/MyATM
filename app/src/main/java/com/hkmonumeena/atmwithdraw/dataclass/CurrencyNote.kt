package com.hkmonumeena.atmwithdraw.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class CurrencyNote(val noteValue:Int, var count:Int, @PrimaryKey(autoGenerate = true) var id: Int? = null)
