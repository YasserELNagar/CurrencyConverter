package com.yasser.currencyconverter.shared

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.currencyconverter.ui.convertCurrency.adapter.CurrencySymbolsAdapter
import com.yasser.currencyconverter.ui.currencyDetails.adapter.OtherCurrenciesAdapter

/**
 *Created by Yasser.Elnagar on 27/02/2022
 */

@BindingAdapter("currencySymbols")
fun Spinner.currencySymbols(items: List<String>?) {
    items?.let {
        val adapter = this.adapter as CurrencySymbolsAdapter
        adapter.addAll(it)
        adapter.notifyDataSetChanged()
    }
}


@BindingAdapter("otherCurrencies")
fun RecyclerView.otherCurrencies(items: HashMap<String, Double>?) {
    items?.let {
        val adapter = this.adapter as OtherCurrenciesAdapter
        adapter.submitList(it.entries.toList())
    }
}

