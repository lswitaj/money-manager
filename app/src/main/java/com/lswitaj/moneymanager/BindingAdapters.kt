package com.lswitaj.moneymanager

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.moneymanager.data.database.Position
import com.lswitaj.moneymanager.data.network.Symbol
import com.lswitaj.moneymanager.search.SearchableListAdapter
import com.lswitaj.moneymanager.summary.SummaryListAdapter

@BindingAdapter("listData")
fun bindSearchableList(recyclerView: RecyclerView, data: List<Symbol>?) {
    val adapter = recyclerView.adapter as SearchableListAdapter
    adapter.submitList(data?.take(25)) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}

@BindingAdapter("summaryData")
fun bindSummary(recyclerView: RecyclerView, data: List<Position>?) {
    val adapter = recyclerView.adapter as SummaryListAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}