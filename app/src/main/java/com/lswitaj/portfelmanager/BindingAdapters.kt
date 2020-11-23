package com.lswitaj.portfelmanager

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.portfelmanager.network.SymbolMatches
import com.lswitaj.portfelmanager.search.SearchableListAdapter
import com.lswitaj.portfelmanager.summary.SummaryListAdapter

@BindingAdapter("listData")
fun bindSearchableList(recyclerView: RecyclerView, data: List<SymbolMatches>?) {
    val adapter = recyclerView.adapter as SearchableListAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}

@BindingAdapter("summaryData")
fun bindSummary(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as SummaryListAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}