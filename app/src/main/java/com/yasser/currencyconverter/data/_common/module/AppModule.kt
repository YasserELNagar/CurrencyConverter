package com.yasser.currencyconverter.data._common.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *Created by Yasser.Elnagar on 27/02/2022
 */
@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    fun provideCoroutineContext():CoroutineContext{
        return Dispatchers.IO
    }


}