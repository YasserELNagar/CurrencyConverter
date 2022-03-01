package com.yasser.currencyconverter.ui.currencyDetails

import androidx.lifecycle.*
import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.entity.HistoricalCurrencyItem
import com.yasser.currencyconverter.domain.currency.usecase.ConvertCurrencyUseCase
import com.yasser.currencyconverter.domain.currency.usecase.GetLastThirtyDaysCurrencyUseCase
import com.yasser.currencyconverter.domain.currency.usecase.GetTenPopularCurrencies
import com.yasser.currencyconverter.shared.CurrencyDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *Created by Yasser.Elnagar on 28/02/2022
 */
@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTenPopularCurrencies: GetTenPopularCurrencies,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getLastThirtyDaysCurrencyUseCase: GetLastThirtyDaysCurrencyUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CurrencyDetailsState>(CurrencyDetailsState.Init)
    val state: LiveData<CurrencyDetailsState> get() = _state

    private val _currencyData = MutableLiveData<CurrencyDetailsDto>()
    val currencyData: LiveData<CurrencyDetailsDto> get() = _currencyData

    val latestCurrencyRates: LiveData<HashMap<String, Double>> = Transformations.map(currencyData) {
        getTenPopularCurrencies(it.fromCurrency, it.currencyRates)
    }

    private val _historicalCurrencyRates = MutableLiveData<List<HistoricalCurrencyItem>>()
    val historicalCurrencyRates: LiveData<List<HistoricalCurrencyItem>> get() = _historicalCurrencyRates

    init {
        val data = savedStateHandle.get<CurrencyDetailsDto>("data")
        data?.let {
            _currencyData.value = it

            getLastThirtyDaysCurrencyRates(it.fromCurrency, it.toCurrency)
        }
    }

    private fun getLastThirtyDaysCurrencyRates(fromCurrency: String, toCurrency: String) {
        viewModelScope.launch {
            getLastThirtyDaysCurrencyUseCase(fromCurrency, toCurrency)
                .onStart {
                    showLoading()
                }
                .catch {
                    hideLoading()
                    handleThrowableError(it)
                    setFinishedState()
                }
                .collect {
                    hideLoading()
                    if (it is BaseResult.Success) {
                        onLastThirtyDaysCurrencyRatesSuccess(it.data)
                    } else if (it is BaseResult.Failure) {
                        val error = it.error?.firstOrNull()
                        handleApiError(error)
                    }
                    setFinishedState()
                }
        }
    }

    private fun onLastThirtyDaysCurrencyRatesSuccess(historicalCurrencyList: List<HistoricalCurrencyItem>) {
        _historicalCurrencyRates.value = historicalCurrencyList
        _state.value= CurrencyDetailsState.SuccessLoadingHistoricalCurrencies
    }

    private fun showLoading() {
        _state.value = CurrencyDetailsState.Loading(true)
    }

    private fun hideLoading() {
        _state.value = CurrencyDetailsState.Loading(false)
    }

    private fun handleApiError(apiError: ApiError?) {
        _state.value = CurrencyDetailsState.ShowToast(apiError?.info)
    }

    private fun handleThrowableError(t: Throwable) {
        _state.value = CurrencyDetailsState.ShowError(t)
    }

    private fun setFinishedState() {
        _state.value = CurrencyDetailsState.Finished
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