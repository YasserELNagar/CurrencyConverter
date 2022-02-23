package com.yasser.currencyconverter.domain.currency

import androidx.lifecycle.LiveData
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import kotlinx.coroutines.flow.Flow

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
interface CurrencyRemoteDataSource {
    suspend fun getHistoricalCurrency(date:String): BaseResult<CurrencyApiResponse>
    suspend fun getLatestCurrency(): BaseResult<CurrencyApiResponse>
}