package com.yasser.currencyconverter.data.currency.remote

import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
interface CurrencyApi {

    @GET("latest")
    fun getLatestCurrency(): Response<CurrencyApiResponse>

    @GET("latest")
    fun getHistoricalCurrency(@Query("date") date: String): Response<CurrencyApiResponse>

}