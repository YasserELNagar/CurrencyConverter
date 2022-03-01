package com.yasser.currencyconverter.domain.currency

import androidx.lifecycle.LiveData
import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencySymbolsApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import kotlinx.coroutines.flow.Flow

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
interface CurrencyRepository {
    suspend fun getHistoricalCurrency(date:String): BaseResult<Pair<String,HashMap<String, Double>>, ApiError>
    suspend fun getLatestCurrency(): Flow<BaseResult<HashMap<String,Double>,ApiError>>
    suspend fun getCurrencySymbols(): Flow<BaseResult<HashMap<String,String>,ApiError>>
}