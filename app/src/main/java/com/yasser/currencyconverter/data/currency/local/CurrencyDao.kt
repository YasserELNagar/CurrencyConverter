package com.yasser.currencyconverter.data.currency.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
@Dao
interface CurrencyDao {

    @Query("Select * From currency Where :date==date")
    suspend fun getCurrency(date:String):CurrencyLocalEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrency(currency:CurrencyLocalEntity)

}