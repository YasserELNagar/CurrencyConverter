package com.yasser.currencyconverter.data.currency.remote.dto

import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.data._common.util.ApiResponseWrapper

data class CurrencyApiResponse(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: HashMap<String,Double>,
    val success: Boolean,
    val timestamp: Int,
    val apiError: ApiError?=null
)