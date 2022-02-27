package com.yasser.currencyconverter.data.currency.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yasser.currencyconverter.data.currency.local.databaseMapper.HashMapTypeConverter
import com.yasser.currencyconverter.data.currency.local.entity.CurrencyLocalEntity
import com.yasser.currencyconverter.data.currency.local.entity.CurrencySymbolLocalEntity

/**
 *Created by Yasser.Elnagar on 27/02/2022
 */
@Database(entities = [CurrencySymbolLocalEntity::class, CurrencyLocalEntity::class], version = 1)
@TypeConverters(HashMapTypeConverter::class)
abstract class CurrencyDatabase:RoomDatabase() {

    abstract fun currencyDao():CurrencyDao
}