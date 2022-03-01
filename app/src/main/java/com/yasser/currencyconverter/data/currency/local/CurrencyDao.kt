package com.yasser.currencyconverter.data.currency.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yasser.currencyconverter.data.currency.local.entity.CurrencyLocalEntity
import com.yasser.currencyconverter.data.currency.local.entity.CurrencySymbolLocalEntity

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
@Dao
interface CurrencyDao {

    @Query("Select * From currency Where date = :date")
    suspend fun getCurrency(date:Long): CurrencyLocalEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrency(vararg currency: CurrencyLocalEntity)

    @Query("Select * From symbol")
    suspend fun getCurrencySymbols():List<CurrencySymbolLocalEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrencySymbol(vararg symbol: CurrencySymbolLocalEntity)

}