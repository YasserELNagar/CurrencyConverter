package com.yasser.currencyconverter.shared

import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by Yasser.Elnagar on 01/03/2022
 */

fun Double.roundTo(places:Int): String {
    return "%.${places}f".format(this)
}


fun String.toDateMilliSec(): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date: Date = sdf.parse(this)
    return date.time
}