package com.yasser.currencyconverter.data.currency.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.yasser.currencyconverter.data.currency.local.CurrencyDao
import com.yasser.currencyconverter.data.currency.local.CurrencyLocalEntity
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class CurrencyRepositoryImp @Inject constructor(
    private val remote: CurrencyRemoteDataSource,
    private val local: CurrencyDao,
    private val ioDispatcher: CoroutineContext
) : CurrencyRepository {
    override suspend fun getHistoricalCurrency(date: String): Flow<BaseResult<HashMap<String, Double>>> =
        flow<BaseResult<HashMap<String, Double>>> {
            //try to get the base currency from local and if not found get it from remote
            var currency = local.getCurrency(date)
            if (currency == null) {
                val response = remote.getLatestCurrency()
                if (response is BaseResult.Success && response.data != null) {
                    currency = CurrencyLocalEntity(
                        response.data.date,
                        response.data.rates
                    )
                    local.insertCurrency(currency)
                    emit(BaseResult.Success(currency.rates))
                } else {
                    emit(BaseResult.Failure(response.error!!))
                }
            } else {
                emit(BaseResult.Success(currency.rates))
            }
        }.flowOn(ioDispatcher)


    override suspend fun getLatestCurrency(date: String): Flow<BaseResult<HashMap<String, Double>>> =
        flow {
            //try to get the base currency from local and if not found get it from remote
            var currency = local.getCurrency(date)
            if (currency == null) {
                val response = remote.getLatestCurrency()
                if (response is BaseResult.Success && response.data != null) {
                    currency = CurrencyLocalEntity(
                        response.data.date,
                        response.data.rates
                    )
                    local.insertCurrency(currency)
                    emit(BaseResult.Success(currency.rates))
                } else {
                    emit(BaseResult.Failure(response.error!!))
                }
            } else {
                emit(BaseResult.Success(currency.rates))
            }
        }.flowOn(ioDispatcher)


}
