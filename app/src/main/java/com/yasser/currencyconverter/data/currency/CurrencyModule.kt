package com.yasser.currencyconverter.data.currency

import com.yasser.currencyconverter.data.currency.remote.CurrencyRemoteDataSourceImp
import com.yasser.currencyconverter.data.currency.repository.CurrencyRepositoryImp
import com.yasser.currencyconverter.domain.currency.CurrencyRemoteDataSource
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class CurrencyModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCurrencyRemoteDataSource(imp: CurrencyRemoteDataSourceImp): CurrencyRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindCurrencyRepository(imp: CurrencyRepositoryImp): CurrencyRepository
}