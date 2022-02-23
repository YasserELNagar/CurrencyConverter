package com.yasser.currencyconverter.data.currency.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.yasser.currencyconverter.data._common.util.NetworkManager
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class CurrencyRemoteDataSourceImp @Inject constructor(
    private val api: CurrencyApi,
    private val networkManager: NetworkManager
) : CurrencyRemoteDataSource {

    override suspend fun getHistoricalCurrency(date: String): BaseResult<CurrencyApiResponse> {
        return networkManager.processCall {
            api.getHistoricalCurrency(date)
        }
    }

    override suspend fun getLatestCurrency(): BaseResult<CurrencyApiResponse> {
        return networkManager.processCall {
            api.getLatestCurrency()
        }
    }
}