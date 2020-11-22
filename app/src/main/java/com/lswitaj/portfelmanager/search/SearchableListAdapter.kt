package com.lswitaj.portfelmanager.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.portfelmanager.databinding.ListSearchableItemBinding
import com.lswitaj.portfelmanager.network.SearchableSymbols
import com.lswitaj.portfelmanager.network.SymbolMatches
import com.lswitaj.portfelmanager.search.SearchableListAdapter.SearchableListViewHolder

// TODO(all those Searchable items should be more meaningful as more searches can be implemented in the app, e.g. for already added to the wallet positions)
class SearchableListAdapter(val onClickListener: OnClickListener) : ListAdapter<SymbolMatches, SearchableListViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<SymbolMatches>() {
        override fun areItemsTheSame(oldItem: SymbolMatches, newItem: SymbolMatches): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SymbolMatches, newItem: SymbolMatches): Boolean {
            return oldItem == newItem
        }
    }

    class SearchableListViewHolder(private var binding: ListSearchableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbolMatches: SymbolMatches) {
            binding.searchable = symbolMatches
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchableListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListSearchableItemBinding.inflate(layoutInflater, parent, false)
                return SearchableListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchableListViewHolder {
        return SearchableListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchableListViewHolder, position: Int) {
        val searchableSymbol = getItem(position)
        holder.itemView.setOnClickListener { 
            onClickListener.onClick(searchableSymbol)
        }
        holder.bind(searchableSymbol)
    }

    class OnClickListener(val clickListener: (searchableSymbol: SymbolMatches) -> Unit) {
        fun onClick(searchableSymbol: SymbolMatches) = clickListener(searchableSymbol)
    }
}
