package com.yasser.currencyconverter.data.currency.local.databaseMapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *Created by Yasser.Elnagar on 27/02/2022
 */
object HashMapTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): HashMap<String, Double> {
        return Gson().fromJson(value,  object : TypeToken<Map<String, Double>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: HashMap<String, Double>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}