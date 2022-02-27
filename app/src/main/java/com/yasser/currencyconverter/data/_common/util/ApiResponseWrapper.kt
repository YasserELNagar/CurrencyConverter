package com.yasser.currencyconverter.data._common.util

import com.google.gson.annotations.SerializedName

/**
 *Created by Yasser.Elnagar on 24/02/2022
 */
open class ApiResponseWrapper<T,E>(
    @SerializedName("success") var status: Boolean,
    @SerializedName("data") var data: T?,
    @SerializedName("error") var error: E?=null,
)