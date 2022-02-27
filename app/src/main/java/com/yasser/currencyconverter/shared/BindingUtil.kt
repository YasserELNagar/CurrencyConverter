package com.yasser.currencyconverter.shared

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.yasser.currencyconverter.ui.convertCurrency.adapter.CurrencySymbolsAdapter

/**
 *Created by Yasser.Elnagar on 27/02/2022
 */

@BindingAdapter("currencySymbols")
fun Spinner.currencySymbols(items:List<String>?) {
    items?.let {
        val adapter = this.adapter as CurrencySymbolsAdapter
        adapter.addAll(it)
        adapter.notifyDataSetChanged()
    }
}