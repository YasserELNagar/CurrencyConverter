package com.yasser.currencyconverter.shared

/**
 *Created by Yasser.Elnagar on 01/03/2022
 */

fun Double.roundTo(places:Int): String {
    return "%.${places}f".format(this)
}