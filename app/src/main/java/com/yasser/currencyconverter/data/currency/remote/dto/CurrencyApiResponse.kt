package com.yasser.currencyconverter.data.currency.remote.dto

data class CurrencyApiResponse(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: HashMap<String,Double>,
    val success: Boolean,
    val timestamp: Int
)