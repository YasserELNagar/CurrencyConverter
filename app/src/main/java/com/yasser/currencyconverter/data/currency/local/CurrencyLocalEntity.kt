package com.yasser.currencyconverter.data.currency.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
@Entity(tableName = "currency")
class CurrencyLocalEntity(
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val rates: HashMap<String,Double>
)