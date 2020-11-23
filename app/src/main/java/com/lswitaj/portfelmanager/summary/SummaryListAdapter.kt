package com.lswitaj.portfelmanager.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.portfelmanager.databinding.ListSummaryItemBinding
import com.lswitaj.portfelmanager.summary.SummaryListAdapter.SummaryListViewHolder

class SummaryListAdapter() : ListAdapter<String, SummaryListViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    class SummaryListViewHolder(private var binding: ListSummaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: String) {
            binding.symbol = symbol
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SummaryListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListSummaryItemBinding.inflate(layoutInflater, parent, false)
                return SummaryListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryListViewHolder {
        return SummaryListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SummaryListViewHolder, position: Int) {
        val symbol = getItem(position)
        holder.bind(symbol)
    }
}
