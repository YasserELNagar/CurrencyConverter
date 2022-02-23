package com.yasser.currencyconverter.domain._common

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
sealed class BaseResult<T>(
    val data: T?,
    val error: BaseError?
) {
    class Success<T>(data: T) : BaseResult<T>(data, null)

    class Failure<T>(error: BaseError) : BaseResult<T>(null, error)
}