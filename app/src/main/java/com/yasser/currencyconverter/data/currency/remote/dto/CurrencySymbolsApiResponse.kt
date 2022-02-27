package com.yasser.currencyconverter.data.currency.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.yasser.currencyconverter.data._common.util.ApiError

data class CurrencySymbolsApiResponse(
    val success: Boolean,
    val symbols: HashMap<String,String>,
    @SerializedName("error")
    val apiError: ApiError?=null
)