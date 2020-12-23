package com.lswitaj.moneymanager.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.moneymanager.databinding.ListSearchableItemBinding
import com.lswitaj.moneymanager.data.network.Symbol
import com.lswitaj.moneymanager.search.SearchableListAdapter.SearchableListViewHolder

// TODO(all those Searchable items should be more meaningful as more searches can be implemented in the app, e.g. for already added to the wallet positions)
class SearchableListAdapter(val onClickListener: OnClickListener) : ListAdapter<Symbol, SearchableListViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Symbol>() {
        override fun areItemsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
            return oldItem == newItem
        }
    }

    class SearchableListViewHolder(private var binding: ListSearchableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbolMatches: Symbol) {
            binding.searchable = symbolMatches
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

    class OnClickListener(val clickListener: (searchableSymbol: Symbol) -> Unit) {
        fun onClick(searchableSymbol: Symbol) = clickListener(searchableSymbol)
    }
}
