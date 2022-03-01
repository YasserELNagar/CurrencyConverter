package com.yasser.currencyconverter.domain.currency.entity

/**
 *Created by Yasser.Elnagar on 01/03/2022
 */
data class HistoricalCurrencyItem(
    val date: String,
    val fromCurrency:String,
    val fromCurrencyAmount:Double,
    val toCurrency:String,
    val toCurrencyAmount:Double,
    )