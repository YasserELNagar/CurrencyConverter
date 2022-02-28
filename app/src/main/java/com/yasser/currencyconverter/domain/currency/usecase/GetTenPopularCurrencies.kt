package com.yasser.currencyconverter.domain.currency.usecase

import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 28/02/2022
 */
class GetTenPopularCurrencies @Inject constructor() {

    operator fun invoke(
        selectedCurrency: String,
        currencyRates: HashMap<String, Double>
    ): HashMap<String, Double> {

        val selectedCurrencyValue = currencyRates[selectedCurrency]!!

        val mostPopularCurrencies = mutableListOf(
            "USD",
            "EUR",
            "JPY",
            "GBP",
            "AUD",
            "CAD",
            "CHF",
            "CNY",
            "SEK",
            "MXN",
            "NZD"
        )
        mostPopularCurrencies.remove(selectedCurrency)

        if (mostPopularCurrencies.size == 11) {
            mostPopularCurrencies.removeLast()
        }

        val result = currencyRates.mapValues {
            it.value / selectedCurrencyValue
        }.filter {
            it.key in mostPopularCurrencies
        }

        return HashMap(result)
    }
}