package com.lswitaj.moneymanager.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.data.database.PositionsDatabase
import com.lswitaj.moneymanager.databinding.FragmentSearchBinding
import com.lswitaj.moneymanager.utils.showSnackbar

class SearchFragment : Fragment() {
    lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = PositionsDatabase.getInstance(application).positionsDatabaseDao
        val viewModelFactory = SearchViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.searchList.adapter = SearchableListAdapter(SearchableListAdapter.OnClickListener {
            viewModel.onNavigateToAddPosition(it)
        })

        viewModel.navigateToAddPosition.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
                //TODO(to change positionName to the whole position instead as it was before)
                this.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToAddPositionFragment(viewModel.positionName))
                viewModel.onNavigatedToAddPosition()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            showSnackbar(view, it)
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        //TODO(to wait until all operations like requests and db are done, maybe display spinner)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchSymbols(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchSymbols(query)
                return true
            }

        })
    }
}