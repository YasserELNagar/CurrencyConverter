package com.yasser.currencyconverter.ui.convertCurrency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.yasser.currencyconverter.R
import com.yasser.currencyconverter.databinding.FragmentConvertCurrencyBinding
import com.yasser.currencyconverter.ui.convertCurrency.adapter.CurrencySymbolsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {

    private lateinit var binding: FragmentConvertCurrencyBinding

    private val viewModel: ConvertCurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConvertCurrencyBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupUI()
        observeData()
        return binding.root
    }

    private fun setupUI() {
        setupCurrencyFromSymbolsAdapter()
        setupCurrencyToSymbolsAdapter()
    }

    private fun setupCurrencyFromSymbolsAdapter() {
        binding.spinnerFromCurrency.apply {
            adapter = CurrencySymbolsAdapter(requireContext())
        }
    }

    private fun setupCurrencyToSymbolsAdapter() {
        binding.spinnerToCurrency.apply {
            adapter = CurrencySymbolsAdapter(requireContext())
        }
    }

    private fun observeData() {
        binding.edtFromAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charStream: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!binding.edtFromAmount.hasFocus()) return
                if (!charStream.isNullOrEmpty()){
                    viewModel.convertCurrency(charStream.toString().toDouble(),null)
                }
                else{
                    binding.edtFromAmount.setText("1")
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.edtToAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charStream: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!binding.edtToAmount.hasFocus()) return
                if (!charStream.isNullOrEmpty()){
                    viewModel.convertCurrency(null,charStream.toString().toDouble())
                }
                else{
                    binding.edtToAmount.setText("1")
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewModel.fromCurrencySelectedPosition.observe(viewLifecycleOwner){
            viewModel.convertCurrency(binding.edtFromAmount.text.toString().toDouble(),null)
        }

        viewModel.toCurrencySelectedPosition.observe(viewLifecycleOwner){
            viewModel.convertCurrency(binding.edtFromAmount.text.toString().toDouble(),null)
        }



        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ConvertCurrencyState.Init -> Unit
                is ConvertCurrencyState.SuccessLoadingSymbols -> Unit
                is ConvertCurrencyState.SuccessLoadingLatestCurrency -> Unit
                is ConvertCurrencyState.Finished -> Unit
                is ConvertCurrencyState.Loading -> {
                    handleLoadingState(it.isLoading)
                }
                is ConvertCurrencyState.ShowToast -> {
                    showToastMessage(it.message)
                }
                is ConvertCurrencyState.ShowError -> {
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