package com.yasser.currencyconverter.domain.currency.usecase

import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class ConvertCurrencyUseCase @Inject constructor() {

    operator fun invoke(
        rates: HashMap<String, Double>,
        from: String,
        to: String,
        fromAmount: Double?,
        toAmount: Double?
    ): Double {
        val fromCurrency = rates[from]!!
        val toCurrency = rates[to]!!

        return when {
            fromAmount != null -> {
                (toCurrency / fromCurrency) * fromAmount
            }
            toAmount != null -> {
                (fromCurrency / toAmount) * toAmount
            }
            else -> {
                0.0
            }
        }
    }

}