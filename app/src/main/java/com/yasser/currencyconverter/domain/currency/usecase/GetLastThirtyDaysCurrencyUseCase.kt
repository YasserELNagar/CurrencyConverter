package com.yasser.currencyconverter.domain.currency.usecase

import com.yasser.currencyconverter.data.currency.remote.dto.CurrencyApiResponse
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.CurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

/**
 *Created by Yasser.Elnagar on 24/02/2022
 */
class GetLastThirtyDaysCurrencyUseCase @Inject constructor() {

    private val days = getLastThirtyDaysDates()

//    suspend operator fun invoke() :BaseResult<HashMap<String,HashMap<String,Double>>,CurrencyApiResponse>{
//        val dates = getLastThirtyDaysDates()
//        //
//    }


    private fun getLastThirtyDaysDates(): Array<String> {

        var thirtyDaysDatesArr = Array<String>(30) { "" }

        for (day in 1..30) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_MONTH, -day)
            val df = SimpleDateFormat("yyyy-MM-dd")
            val resultDate = Date(cal.timeInMillis)
            val dateString = df.format(resultDate)
            thirtyDaysDatesArr[day - 1] = dateString
        }
        return thirtyDaysDatesArr
    }


}