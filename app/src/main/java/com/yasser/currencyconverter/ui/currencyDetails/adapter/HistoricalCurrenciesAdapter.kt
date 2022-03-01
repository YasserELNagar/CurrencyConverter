package com.yasser.currencyconverter.ui.currencyDetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.currencyconverter.databinding.ViewCurrencyHistoryItemBinding
import com.yasser.currencyconverter.domain.currency.entity.HistoricalCurrencyItem


/**
 *Created by Yasser.Elnagar on 01/03/2022
 */
class HistoricalCurrenciesAdapter(private val itemClickListener: (view: View) -> Unit) :
    ListAdapter<HistoricalCurrencyItem, HistoricalCurrenciesAdapter.HistoricalItemViewHolder>(
        DiffCallBack
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalItemViewHolder {
        return HistoricalItemViewHolder.from(parent,itemClickListener)
    }

    override fun onBindViewHolder(holder: HistoricalItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class HistoricalItemViewHolder(
        private val bindingItem: ViewCurrencyHistoryItemBinding,
        private val itemClickListener: (view: View) -> Unit
    ) : RecyclerView.ViewHolder(bindingItem.root) {

        companion object {
            fun from(parent: ViewGroup, itemClickListener: (view: View) -> Unit) =
                HistoricalItemViewHolder(
                    ViewCurrencyHistoryItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ),
                        parent,
                        false
                    ),
                    itemClickListener
                )

        }


        fun bind(item: HistoricalCurrencyItem?) {
            item?.let {
                bindingItem.item = item
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<HistoricalCurrencyItem>() {
        override fun areItemsTheSame(
            oldItem: HistoricalCurrencyItem,
            newItem: HistoricalCurrencyItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HistoricalCurrencyItem,
            newItem: HistoricalCurrencyItem
        ): Boolean {
            return oldItem == newItem
        }

    }


}