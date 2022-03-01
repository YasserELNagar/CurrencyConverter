package com.yasser.currencyconverter.domain.currency.usecase

import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 01/03/2022
 */
class ClearCurrencyDataBeforeThirtyDaysUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend operator fun invoke() {
        repository.getClearOldDates(getThirtyOneDayDate())
    }

    private fun getThirtyOneDayDate(): Long {

        val cal = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -31)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }


        val sss=cal.timeInMillis
        return cal.timeInMillis
    }
}