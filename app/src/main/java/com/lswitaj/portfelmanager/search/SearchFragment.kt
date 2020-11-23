package com.lswitaj.portfelmanager.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lswitaj.portfelmanager.R
import com.lswitaj.portfelmanager.database.SymbolsDatabase
import com.lswitaj.portfelmanager.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = SymbolsDatabase.getInstance(application).symbolsDatabaseDao
        val viewModelFactory = SearchViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.searchList.adapter = SearchableListAdapter(SearchableListAdapter.OnClickListener {
            viewModel.addNewSymbol(it)
        })

        viewModel.navigateToSummary.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToSummaryFragment(it))
                viewModel.addNewSymbolComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_item, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchSymbols(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
}