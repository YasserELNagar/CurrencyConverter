package com.yasser.currencyconverter.data._common.util

import android.content.Context
import android.net.ConnectivityManager
import com.yasser.currencyconverter.domain._common.BaseError
import com.yasser.currencyconverter.domain._common.BaseResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 23/02/2022
 */
class NetworkManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun isConnected(): Boolean {
        val networkInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


//    suspend fun <T : Any,E : Any> processCall(call: suspend () -> Response<T>): Flow<BaseResult<T, E>> =
//        flow {
//            try {
//                val response = call.invoke()
//                val responseBody = response.body()!!
//                emit(BaseResult.Success(responseBody))
//            } catch (t: Throwable) {
//                emit(BaseResult.Failure(BaseError.GENERAL_ERROR))
//            }
//        }
}