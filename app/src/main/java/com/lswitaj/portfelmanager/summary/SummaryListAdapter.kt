package com.lswitaj.portfelmanager.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.portfelmanager.database.SymbolsOverview
import com.lswitaj.portfelmanager.databinding.ListSummaryItemBinding
import com.lswitaj.portfelmanager.summary.SummaryListAdapter.SummaryListViewHolder

class SummaryListAdapter() : ListAdapter<SymbolsOverview, SummaryListViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<SymbolsOverview>() {
        override fun areItemsTheSame(oldItem: SymbolsOverview, newItem: SymbolsOverview): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SymbolsOverview, newItem: SymbolsOverview): Boolean {
            return oldItem.symbolId == newItem.symbolId
        }
    }

    class SummaryListViewHolder(private var binding: ListSummaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbolOverview: SymbolsOverview) {
            binding.symbolOverview = symbolOverview
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
