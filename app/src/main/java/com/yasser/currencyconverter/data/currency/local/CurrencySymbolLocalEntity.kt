package com.yasser.currencyconverter.data.currency.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Yasser.Elnagar on 26/02/2022
 */
@Entity(tableName = "symbol")
data class CurrencySymbolLocalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val abbreviation: String,
    val name: String
)
