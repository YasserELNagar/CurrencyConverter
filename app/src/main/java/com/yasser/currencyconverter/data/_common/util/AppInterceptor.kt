package com.yasser.currencyconverter.data._common.util

import com.yasser.currencyconverter.shared.API_ACCESS_KEY
import com.yasser.currencyconverter.shared.API_ACCESS_KEY_VALUE
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url = request.url().newBuilder().addQueryParameter(API_ACCESS_KEY, API_ACCESS_KEY_VALUE).build()
        request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}