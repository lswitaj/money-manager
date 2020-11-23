package com.lswitaj.portfelmanager.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lswitaj.portfelmanager.R
import com.lswitaj.portfelmanager.database.SymbolsDatabase
import com.lswitaj.portfelmanager.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    lateinit var viewModel: SummaryViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSummaryBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = SymbolsDatabase.getInstance(application).symbolsDatabaseDao
        val viewModelFactory = SummaryViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SummaryViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.summaryList.adapter = SummaryListAdapter()

        viewModel.navigateToSearch.observe(viewLifecycleOwner,
            { shouldNavigate ->
                if (shouldNavigate == true) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_summaryFragment_to_searchFragment)
                    viewModel.onNavigatedToSearch()
                }
            })

        return binding.root
    }
}