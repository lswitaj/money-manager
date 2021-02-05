package com.lswitaj.moneymanager.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.databinding.ListSummaryItemBinding
import com.lswitaj.moneymanager.summary.SummaryListAdapter.SummaryListViewHolder

class SummaryListAdapter() : ListAdapter<Position, SummaryListViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Position>() {
        override fun areItemsTheSame(oldItem: Position, newItem: Position): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Position, newItem: Position): Boolean {
            return oldItem.positionId == newItem.positionId
        }
    }

    class SummaryListViewHolder(private var binding: ListSummaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Position) {
            binding.position = position
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

    override fun onBindViewHolder(holder: SummaryListViewHolder, _position: Int) {
        val position = getItem(_position)
        holder.bind(position)
    }
}
