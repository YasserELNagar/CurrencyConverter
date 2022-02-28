package com.yasser.currencyconverter.shared

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 *Created by Yasser.Elnagar on 28/02/2022
 */
@Parcelize
data class CurrencyDetailsDto(
    val fromCurrency: String,
    val currencyRates: HashMap<String, Double>
):Parcelable