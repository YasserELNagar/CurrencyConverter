package com.yasser.currencyconverter.data._common.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yasser.currencyconverter.data._common.util.AppInterceptor
import com.yasser.currencyconverter.data.currency.remote.CurrencyApi
import com.yasser.currencyconverter.shared.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *Created by Yasser.Elnagar on 22/02/2022
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideHttpOkClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,httpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(retrofit: Retrofit):CurrencyApi{
        return retrofit.create(CurrencyApi::class.java)
    }

}