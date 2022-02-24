package com.yasser.currencyconverter.domain.currency.usecase

import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 24/02/2022
 */
class GetLatestCurrencyUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): Flow<BaseResult<HashMap<String, Double>, CurrencyApiResponse>> {
        return repository.getLatestCurrency()
    }
}