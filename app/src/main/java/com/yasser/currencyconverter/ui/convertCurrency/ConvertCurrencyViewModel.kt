package com.yasser.currencyconverter.ui.convertCurrency

import androidx.lifecycle.*
import com.yasser.currencyconverter.data._common.util.ApiError
import com.yasser.currencyconverter.domain._common.BaseResult
import com.yasser.currencyconverter.domain.currency.usecase.ConvertCurrencyUseCase
import com.yasser.currencyconverter.domain.currency.usecase.GetCurrencySymbolsUsesCase
import com.yasser.currencyconverter.domain.currency.usecase.GetLatestCurrencyUseCase
import com.yasser.currencyconverter.shared.CurrencyDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Yasser.Elnagar on 26/02/2022
 */
@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCurrencySymbolsUsesCase: GetCurrencySymbolsUsesCase,
    private val getLatestCurrencyUseCase: GetLatestCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<ConvertCurrencyState>(ConvertCurrencyState.Init)
    val state: LiveData<ConvertCurrencyState> get() = _state

    private val _currencySymbols = MutableLiveData<HashMap<String, String>>()
    val currencySymbols: LiveData<HashMap<String, String>> get() = _currencySymbols

    private val _latestCurrency = MutableLiveData<HashMap<String, Double>>()
    val latestCurrency: LiveData<HashMap<String, Double>> get() = _latestCurrency

    val currencyList = Transformations.map(currencySymbols) { hashMap ->
        hashMap.map { it.key }
            .toList()
            .sortedBy { it }
    }
    val fromCurrencySelectedPosition = MutableLiveData<Int>()

    val toCurrencySelectedPosition = MutableLiveData<Int>()

    val fromCurrencyAmount = MutableLiveData<String>("1")

    val toCurrencyAmount = MutableLiveData<String>("1.0")


    init {
        getCurrencySymbols()
        getLatestCurrency()
    }

    fun onSwapSelectedCurrenciesClick() {
        if (fromCurrencySelectedPosition.value != toCurrencySelectedPosition.value && fromCurrencySelectedPosition.value != null) {
            val temp = fromCurrencySelectedPosition.value
            fromCurrencySelectedPosition.value = toCurrencySelectedPosition.value
            toCurrencySelectedPosition.value = temp!!
        }
    }

    private fun getCurrencySymbols() {
        viewModelScope.launch {
            getCurrencySymbolsUsesCase()
                .onStart {
                    showLoading()
                }
                .catch {
                    hideLoading()
                    handleThrowableError(it)
                }.collect {
                    hideLoading()
                    if (it is BaseResult.Success) {
                        _currencySymbols.value = it.data
                        onLoadingSymbolsSuccess()
                    } else if (it is BaseResult.Failure) {
                        handleApiError(it.error)
                    }
                    setFinishedState()
                }

        }
    }

    private fun getLatestCurrency() {
        viewModelScope.launch {
            getLatestCurrencyUseCase()
                .onStart {
                    showLoading()
                }
                .catch {
                    hideLoading()
                    handleThrowableError(it)
                }
                .collect {
                    hideLoading()
                    if (it is BaseResult.Success) {
                        _latestCurrency.value = it.data
                        onLoadingLatestCurrency()
                    } else if (it is BaseResult.Failure) {
                        handleApiError(it.error)
                    }
                    setFinishedState()
                }
        }
    }

    fun convertCurrency(fromAmount: Double?, toAmount: Double?) {
        if (currencyList.value == null ||
            latestCurrency.value == null ||
            fromCurrencySelectedPosition.value == null ||
            toCurrencySelectedPosition.value == null
        )
            return


        val fromSelectedCurrency = currencyList.value!![fromCurrencySelectedPosition.value!!]
        val toSelectedCurrency = currencyList.value!![toCurrencySelectedPosition.value!!]
        val latestCurrencyRates = latestCurrency.value!!

        val convertedAmount = convertCurrencyUseCase(
            latestCurrencyRates,
            fromSelectedCurrency,
            toSelectedCurrency,
            fromAmount,
            toAmount
        )

        if (fromAmount == null) {
            fromCurrencyAmount.value = convertedAmount.toString()
        } else {
            toCurrencyAmount.value = convertedAmount.toString()
        }


    }

    fun onDetailsClick() {
        if (currencyList.value == null ||
            latestCurrency.value == null ||
            fromCurrencySelectedPosition.value == null
        )
            return

        val fromSelectedCurrency = currencyList.value!![fromCurrencySelectedPosition.value!!]
        val latestCurrencyRates = latestCurrency.value!!

        navigateToDetails(
            CurrencyDetailsDto(
                fromSelectedCurrency,
                latestCurrencyRates
            )
        )

        setFinishedState()
    }

    private fun showLoading() {
        _state.value = ConvertCurrencyState.Loading(true)
    }

    private fun hideLoading() {
        _state.value = ConvertCurrencyState.Loading(false)
    }

    private fun handleApiError(apiError: ApiError?) {
        _state.value = ConvertCurrencyState.ShowToast(apiError?.info)
    }

    private fun handleThrowableError(t: Throwable) {
        _state.value = ConvertCurrencyState.ShowError(t)
    }

    private fun onLoadingSymbolsSuccess() {
        _state.value = ConvertCurrencyState.SuccessLoadingSymbols
    }

    private fun onLoadingLatestCurrency() {
        _state.value = ConvertCurrencyState.SuccessLoadingLatestCurrency
    }

    private fun setFinishedState() {
        _state.value = ConvertCurrencyState.Finished
    }

    private fun navigateToDetails(data: CurrencyDetailsDto) {
        _state.value = ConvertCurrencyState.NavigateToDetails(data)
    }
}

sealed class ConvertCurrencyState {
    object Init : ConvertCurrencyState()
    object SuccessLoadingSymbols : ConvertCurrencyState()
    object SuccessLoadingLatestCurrency : ConvertCurrencyState()
    object Finished : ConvertCurrencyState()
    data class Loading(val isLoading: Boolean) : ConvertCurrencyState()
    data class NavigateToDetails(val data: CurrencyDetailsDto) : ConvertCurrencyState()
    data class ShowToast(val message: String?) : ConvertCurrencyState()
    data class ShowError(val t: Throwable?) : ConvertCurrencyState()
}