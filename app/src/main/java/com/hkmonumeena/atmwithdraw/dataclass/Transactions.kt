package com.hkmonumeena.atmwithdraw.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transactions(
    val rupee100:Int,
    val rupee200:Int,
    val rupee500:Int,
    val rupee2000:Int,
    val totalAmount:Int,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
)
