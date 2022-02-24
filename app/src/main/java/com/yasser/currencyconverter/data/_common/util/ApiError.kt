package com.yasser.currencyconverter.data._common.util

data class ApiError(
    val code: Int,
    val info: String,
    val type: String
)