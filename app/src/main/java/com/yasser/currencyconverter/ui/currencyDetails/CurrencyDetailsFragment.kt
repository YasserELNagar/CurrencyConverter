package com.yasser.currencyconverter.ui.currencyDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yasser.currencyconverter.R
import com.yasser.currencyconverter.databinding.FragmentCurrencyDetailsBinding
import com.yasser.currencyconverter.ui.currencyDetails.adapter.OtherCurrenciesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding : FragmentCurrencyDetailsBinding

    private val viewModel : CurrencyDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyDetailsBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        setupOtherCurrenciesAdapter()
        setupHistoricalCurrenciesAdapter()
    }

    private fun setupOtherCurrenciesAdapter() {
        binding.rvOtherCurrencies.apply {
            adapter = OtherCurrenciesAdapter(){

            }
        }
    }


    private fun setupHistoricalCurrenciesAdapter() {
        binding.rvHostricalCurrencies.apply {
            adapter = OtherCurrenciesAdapter(){

            }
        }
    }

}