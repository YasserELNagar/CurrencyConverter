package com.yasser.currencyconverter.data.currency.repository

import com.yasser.currencyconverter.data._common.util.mapper.CurrencySymbolMapper
import com.yasser.currencyconverter.data.currency.local.CurrencyDao
import com.yasser.currencyconverter.data.currency.local.CurrencyLocalEntity
import com.yasser.currencyconverter.data.currency.local.CurrencySymbolLocalEntity
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.data.currency.remote.dto.CurrencySymbolsApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    override suspend fun getHistoricalCurrency(date: String): Flow<BaseResult<HashMap<String, Double>, CurrencyApiResponse>> =
        flow {
            //try to get the base currency from local and if not found get it from remote
            var currency = local.getCurrency(date)
            if (currency == null) {
                val response = remote.getHistoricalCurrency(date)
                if (response is BaseResult.Success) {
                    currency = CurrencyLocalEntity(
                        date,
                        response.data
                    )
                    local.insertCurrency(currency)
                    emit(BaseResult.Success(currency.rates))
                } else if (response is BaseResult.Failure) {
                    emit(BaseResult.Failure(response.error))
                }
            } else {
                emit(BaseResult.Success(currency.rates))
            }
        }.flowOn(ioDispatcher)

    override suspend fun getLatestCurrency(): Flow<BaseResult<HashMap<String, Double>, CurrencyApiResponse>> =
        flow {
            val response = remote.getLatestCurrency()
            emit(response)
        }.flowOn(ioDispatcher)

    override suspend fun getCurrencySymbols(): Flow<BaseResult<HashMap<String, String>, CurrencySymbolsApiResponse>> =
        flow {
            //try to get the base currency symbols from local and if not found get it from remote
            var symbols = local.getCurrencySymbols()
            if (symbols == null) {
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
}
