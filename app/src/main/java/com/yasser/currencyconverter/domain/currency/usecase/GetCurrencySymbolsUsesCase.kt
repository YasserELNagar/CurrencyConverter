package com.yasser.currencyconverter.domain.currency.usecase

import com.yasser.currencyconverter.data.currency.remote.dto.CurrencySymbolsApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 26/02/2022
 */
class GetCurrencySymbolsUsesCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend operator fun invoke(): Flow<BaseResult<HashMap<String, String>, CurrencySymbolsApiResponse>> {
        return repository.getCurrencySymbols()
    }

}