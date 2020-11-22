package com.lswitaj.portfelmanager.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lswitaj.portfelmanager.R
import com.lswitaj.portfelmanager.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    private val viewModel: SummaryViewModel by lazy {
        ViewModelProvider(this).get(SummaryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSummaryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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