package com.yasser.currencyconverter.ui.currencyDetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasser.currencyconverter.databinding.ViewOtherCurrenciesItemBinding

/**
 *Created by Yasser.Elnagar on 28/02/2022
 */
class OtherCurrenciesAdapter(private val itemClickListener: (view: View) -> Unit) :
    ListAdapter<Map.Entry<String,Double>, OtherCurrenciesAdapter.OtherCurrencyItemViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherCurrencyItemViewHolder {
        return OtherCurrencyItemViewHolder.from(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: OtherCurrencyItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OtherCurrencyItemViewHolder(
        private val binding: ViewOtherCurrenciesItemBinding,
        private val itemClickListener: (view: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, itemClickListener: (view: View) -> Unit) =
                OtherCurrencyItemViewHolder(
                    ViewOtherCurrenciesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    itemClickListener
                )
        }

        fun bind(item: Map.Entry<String,Double>?) {
            binding.item=item
        }

    }


    object DiffCallback : DiffUtil.ItemCallback<Map.Entry<String,Double>>() {
        override fun areItemsTheSame(oldItem: Map.Entry<String,Double>, newItem: Map.Entry<String,Double>): Boolean {
            return oldItem.key==newItem.key
        }

        override fun areContentsTheSame(oldItem: Map.Entry<String,Double>, newItem: Map.Entry<String,Double>): Boolean {
            return oldItem.value==newItem.value
        }
    }
}