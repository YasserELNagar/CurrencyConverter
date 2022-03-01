package com.yasser.currencyconverter.ui.currencyDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.yasser.currencyconverter.R
import com.yasser.currencyconverter.databinding.FragmentCurrencyDetailsBinding
import com.yasser.currencyconverter.ui.currencyDetails.adapter.HistoricalCurrenciesAdapter
import com.yasser.currencyconverter.ui.currencyDetails.adapter.OtherCurrenciesAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding

    private val viewModel: CurrencyDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupUI()
        observeData()
        return binding.root
    }

    private fun setupUI() {
        setupOtherCurrenciesAdapter()
        setupHistoricalCurrenciesAdapter()
    }

    private fun setupOtherCurrenciesAdapter() {
        binding.rvOtherCurrencies.apply {
            adapter = OtherCurrenciesAdapter() {

            }
        }
    }


    private fun setupHistoricalCurrenciesAdapter() {
        binding.rvHostricalCurrencies.apply {
            adapter = HistoricalCurrenciesAdapter() {

            }
        }
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CurrencyDetailsState.Init,
                is CurrencyDetailsState.SuccessLoadingHistoricalCurrencies,
                is CurrencyDetailsState.Finished-> Unit
                is CurrencyDetailsState.Loading -> {
                    handleLoadingState(it.isLoading)
                }
                is CurrencyDetailsState.ShowToast -> {
                    showToastMessage(it.message)
                }
                is CurrencyDetailsState.ShowError -> {
                    handleExceptions(it.t)
                }
            }
        }
    }

    private fun handleLoadingState(loading: Boolean) {
        binding.isLoading = loading
    }


    private fun showToastMessage(message: String?) {
        message?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleExceptions(t: Throwable?) {
        if (t == null) return

        when (t) {
            is IOException -> {
                showToastMessage(getString(R.string.internet_connection_problem))
            }
            else -> {
                showToastMessage(getString(R.string.general_error_message))
            }
        }
    }

}