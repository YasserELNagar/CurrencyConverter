package com.yasser.currencyconverter.data.currency.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yasser.currencyconverter.data._common.util.NetworkManager
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class CurrencyRemoteDataSourceImp @Inject constructor(
    private val api: CurrencyApi) : CurrencyRemoteDataSource {

    override suspend fun getHistoricalCurrency(date: String): BaseResult<HashMap<String,Double>,CurrencyApiResponse>
     {
         val response = api.getHistoricalCurrency(date)
         val responseBody =response.body()!!
         return if (responseBody.success){
              BaseResult.Success(responseBody.rates)
         }
         else{
             val type = object : TypeToken<CurrencyApiResponse>(){}.type
             val responseError = Gson().fromJson<CurrencyApiResponse>(response.errorBody()!!.charStream(), type)!!
             BaseResult.Failure(responseError)
         }

    }

    override suspend fun getLatestCurrency(): BaseResult<HashMap<String,Double>,CurrencyApiResponse> {
        val response = api.getLatestCurrency()
        val responseBody =response.body()!!
        return if (responseBody.success){
            BaseResult.Success(responseBody.rates)
        }
        else{
            val type = object : TypeToken<CurrencyApiResponse>(){}.type
            val responseError = Gson().fromJson<CurrencyApiResponse>(response.errorBody()!!.charStream(), type)!!
            BaseResult.Failure(responseError)
        }
    }
}