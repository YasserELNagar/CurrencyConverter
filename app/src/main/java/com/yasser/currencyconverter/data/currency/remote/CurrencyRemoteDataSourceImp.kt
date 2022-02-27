package com.yasser.currencyconverter.data.currency.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.data._common.util.NetworkManager
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencySymbolsApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class CurrencyRemoteDataSourceImp @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRemoteDataSource {

    override suspend fun getHistoricalCurrency(date: String): BaseResult<HashMap<String, Double>, ApiError> {
        val response = api.getHistoricalCurrency(date)
        val responseBody = response.body()!!
        return if (responseBody.success) {
            BaseResult.Success(responseBody.rates)
        } else {
            BaseResult.Failure(responseBody.apiError)
        }
    }

    override suspend fun getLatestCurrency(): BaseResult<HashMap<String, Double>, ApiError> {
        val response = api.getLatestCurrency()
        val responseBody = response.body()!!
        return if (responseBody.success) {
            BaseResult.Success(responseBody.rates)
        } else {
            BaseResult.Failure(responseBody.apiError)
        }
    }

    override suspend fun getCurrencySymbols(): BaseResult<HashMap<String, String>, ApiError> {
        val response = api.getCurrencySymbols()
        val responseBody = response.body()!!
        return if (responseBody.success) {
            BaseResult.Success(responseBody.symbols)
        } else {
            BaseResult.Failure(responseBody.apiError)
        }
    }
}