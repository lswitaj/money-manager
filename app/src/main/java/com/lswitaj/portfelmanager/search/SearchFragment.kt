package com.lswitaj.portfelmanager.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lswitaj.portfelmanager.databinding.FragmentSearchBinding
import com.lswitaj.portfelmanager.network.SymbolMatches

class SearchFragment : Fragment() {
    val adapter = SearchableListAdapter()

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.searchList.adapter = adapter

        return binding.root
    }
}