package com.yasser.currencyconverter.data.currency.repository

import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.data._common.util.mapper.CurrencySymbolMapper
import com.yasser.currencyconverter.data.currency.local.CurrencyDao
import com.yasser.currencyconverter.data.currency.local.entity.CurrencyLocalEntity
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import com.yasser.currencyconverter.shared.toDateMilliSec
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

    override suspend fun getHistoricalCurrency(date: String): BaseResult<Pair<String, HashMap<String, Double>>, ApiError> {
        //try to get the base currency from local and if not found get it from remote
        return withContext(ioDispatcher) {
            val dateMilliSec = date.toDateMilliSec()
            var currency = local.getCurrency(dateMilliSec)
            if (currency == null) {
                val response = remote.getHistoricalCurrency(date)
                if (response is BaseResult.Success) {
                    currency = CurrencyLocalEntity(
                        dateMilliSec,
                        response.data
                    )
                    local.insertCurrency(currency)
                    BaseResult.Success(Pair(date, currency.rates))
                } else {
                    BaseResult.Failure((response as BaseResult.Failure).error)
                }
            } else {
                BaseResult.Success(Pair(date, currency.rates))
            }
        }

    }


    override suspend fun getLatestCurrency(): Flow<BaseResult<HashMap<String, Double>, ApiError>> =
        flow {
            val response = remote.getLatestCurrency()
            emit(response)
        }.flowOn(ioDispatcher)


    override suspend fun getCurrencySymbols(): Flow<BaseResult<HashMap<String, String>, ApiError>> =
        flow {
            //try to get the base currency symbols from local and if not found get it from remote
            var symbols = local.getCurrencySymbols()
            if (symbols.isNullOrEmpty()) {
                val response = remote.getCurrencySymbols()
                if (response is BaseResult.Success) {
                    val symbolsHashMap = response.data
                    val currencySymbols = symbolsHashMap.map {
                        CurrencySymbolMapper().fromDomain(it)
                    }
                    local.insertCurrencySymbol(*currencySymbols.toTypedArray())
                    emit(BaseResult.Success(symbolsHashMap))
                } else if (response is BaseResult.Failure) {
                    emit(BaseResult.Failure(response.error))
                }
            } else {
                val symbolsHashMap = symbols.withIndex().associateTo(HashMap()) {
                    CurrencySymbolMapper().fromEntity(it.value)
                }
                emit(BaseResult.Success(symbolsHashMap))
            }
        }.flowOn(ioDispatcher)

    override suspend fun getClearOldDates(date: Long) {
        withContext(ioDispatcher){
            local.clearOldDates(date)
        }
    }
}
