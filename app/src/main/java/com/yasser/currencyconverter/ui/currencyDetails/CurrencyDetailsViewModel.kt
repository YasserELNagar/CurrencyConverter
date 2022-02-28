package com.yasser.currencyconverter.ui.currencyDetails

import androidx.lifecycle.*
import com.yasser.currencyconverter.domain.currency.usecase.GetLastThirtyDaysCurrencyUseCase
import com.yasser.currencyconverter.domain.currency.usecase.GetTenPopularCurrencies
import com.yasser.currencyconverter.shared.CurrencyDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 28/02/2022
 */
@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTenPopularCurrencies: GetTenPopularCurrencies,
    private val getLastThirtyDaysCurrencyUseCase: GetLastThirtyDaysCurrencyUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CurrencyDetailsState>(CurrencyDetailsState.Init)
    val state: LiveData<CurrencyDetailsState> get() = _state

    private val _currencyData = MutableLiveData<CurrencyDetailsDto>()
    val currencyData: LiveData<CurrencyDetailsDto> get() = _currencyData

    val currencyRates: LiveData<HashMap<String, Double>> = Transformations.map(currencyData) {
        getTenPopularCurrencies(it.fromCurrency,it.currencyRates)
    }

    init {
        val data = savedStateHandle.get<CurrencyDetailsDto>("data")
        data?.let {
            _currencyData.value = it
        }
    }


}

sealed class CurrencyDetailsState {
    object Init : CurrencyDetailsState()
    object SuccessLoadingHistoricalCurrencies : CurrencyDetailsState()
    object Finished : CurrencyDetailsState()
    data class Loading(val isLoading: Boolean) : CurrencyDetailsState()
    data class ShowToast(val message: String?) : CurrencyDetailsState()
    data class ShowError(val t: Throwable?) : CurrencyDetailsState()
}