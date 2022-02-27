package com.yasser.currencyconverter.domain._common

import com.yasser.currencyconverter.data._common.util.ApiError

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
sealed class BaseResult<out T:Any,out E:Any>() {
    data class Success<T:Any>(val data: T) : BaseResult<T,Nothing>()

    data class Failure<E:Any>(val error: E?) : BaseResult<Nothing,E>()
}