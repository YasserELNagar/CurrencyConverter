package com.yasser.currencyconverter.data.currency.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yasser.currencyconverter.data.currency.local.databaseMapper.HashMapTypeConverter

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
@Entity(tableName = "currency")
data class CurrencyLocalEntity(
    @PrimaryKey(autoGenerate = false)
    val date: Long,
    @TypeConverters(HashMapTypeConverter::class)
    val rates: HashMap<String,Double>
)