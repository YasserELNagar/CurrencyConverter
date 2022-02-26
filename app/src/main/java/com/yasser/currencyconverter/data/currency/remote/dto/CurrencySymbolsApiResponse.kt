package com.yasser.currencyconverter.data.currency.remote.dto

data class CurrencySymbolsApiResponse(
    val success: Boolean,
    val symbols: HashMap<String,String>
)