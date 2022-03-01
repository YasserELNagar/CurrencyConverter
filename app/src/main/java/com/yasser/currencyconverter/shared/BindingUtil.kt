package com.yasser.currencyconverter.shared

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.currencyconverter.domain.currency.entity.HistoricalCurrencyItem
import com.yasser.currencyconverter.ui.convertCurrency.adapter.CurrencySymbolsAdapter
import com.yasser.currencyconverter.ui.currencyDetails.adapter.HistoricalCurrenciesAdapter
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


@BindingAdapter("historicalCurrencies")
fun RecyclerView.historicalCurrencies(items: List<HistoricalCurrencyItem>?) {
    items?.let {
        val adapter = this.adapter as HistoricalCurrenciesAdapter
        adapter.submitList(items)
    }
}



@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["baseCurrency","baseCurrencyAmount", "otherCurrency", "otherCurrencyAmount"], requireAll = true)
fun TextView.formatCurrency(baseCurrency: String, baseCurrencyAmount: Double, otherCurrency: String, otherCurrencyAmount: Double) {
    this.text = "${baseCurrencyAmount.roundTo(1)} $baseCurrency = ${otherCurrencyAmount.roundTo(2)} $otherCurrency"
}

