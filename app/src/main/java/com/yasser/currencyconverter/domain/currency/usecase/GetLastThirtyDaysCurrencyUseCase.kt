package com.yasser.currencyconverter.domain.currency.usecase

import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import com.yasser.currencyconverter.domain.currency.entity.HistoricalCurrencyItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

/**
 *Created by Yasser.Elnagar on 24/02/2022
 */
class GetLastThirtyDaysCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) {

    suspend operator fun invoke(
        fromCurrency: String,
        toCurrency: String
    ): Flow<BaseResult<List<HistoricalCurrencyItem>, Set<ApiError?>>> =
        flow {

            val dates = getLastThirtyDaysDates()

            val resultList =
                mutableListOf<Deferred<BaseResult<Pair<String, HashMap<String, Double>>, ApiError>>>()

            for (date in dates) {
                coroutineScope {
                    val result = async { repository.getHistoricalCurrency(date) }
                    resultList.add(result)
                }
            }

            val historicalList = mutableListOf<HistoricalCurrencyItem>()
            val errorSet = mutableSetOf<ApiError?>()

            for (result in resultList.awaitAll()) {
                if (result is BaseResult.Success) {
                    val currencyDate = result.data.first
                    val currencyHistoricalRates = result.data.second
                    val convertedCurrencyValue = convertCurrencyUseCase(
                        currencyHistoricalRates,
                        fromCurrency,
                        toCurrency,
                        1.0,
                        null
                    )

                    historicalList.add(
                        HistoricalCurrencyItem(
                            currencyDate,
                            fromCurrency,
                            1.0,
                            toCurrency,
                            convertedCurrencyValue
                        )
                    )
                } else if (result is BaseResult.Failure) {
                    errorSet.add(result.error)
                }
            }

            emit(BaseResult.Success(historicalList))
            emit(BaseResult.Failure(errorSet.toSet()))
        }


    private fun getLastThirtyDaysDates(): Array<String> {

        var thirtyDaysDatesArr = Array<String>(30) { "" }

        for (day in 1..30) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_MONTH, -day)
            val df = SimpleDateFormat("yyyy-MM-dd")
            val resultDate = Date(cal.timeInMillis)
            resultDate.time
            val dateString = df.format(resultDate)
            thirtyDaysDatesArr[day - 1] = dateString
        }
        return thirtyDaysDatesArr
    }


}