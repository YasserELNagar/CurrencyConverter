package com.yasser.currencyconverter.domain.currency.usecase

import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class ConvertCurrencyUseCase @Inject constructor() {

    operator fun invoke(
        currencyRates: HashMap<String, Double>,
        from: String,
        to: String,
        fromAmount: Double?,
        toAmount: Double?
    ): Double {
        val fromCurrency = currencyRates[from]!!
        val toCurrency = currencyRates[to]!!

        return when {
            fromAmount != null -> {
                (toCurrency / fromCurrency) * fromAmount
            }
            toAmount != null -> {
                (fromCurrency / toCurrency) * toAmount
            }
            else -> {
                0.0
            }
        }
    }

}