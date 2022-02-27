package com.yasser.currencyconverter.ui.convertCurrency.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.yasser.currencyconverter.R
import com.yasser.currencyconverter.databinding.ViewCurrencySymbolSpinnerItemBinding
import java.util.HashMap

/**
 *Created by Yasser.Elnagar on 26/02/2022
 */
class CurrencySymbolsAdapter(context: Context) : ArrayAdapter<String>(
    context,
    R.layout.view_currency_symbol_spinner_item
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        val binding = ViewCurrencySymbolSpinnerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        item?.let {
            binding.item = it
        }

        return binding.root
    }

}