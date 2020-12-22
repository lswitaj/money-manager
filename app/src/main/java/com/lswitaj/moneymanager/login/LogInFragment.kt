package com.lswitaj.moneymanager.login

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lswitaj.moneymanager.databinding.FragmentLogInBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.search.SearchFragmentDirections

class LogInFragment : Fragment() {
    //TODO(check if the viewModel should be lateinit or initialized in onCreateView())
    lateinit var viewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLogInBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //TODO(to make navigation in search, summary and login consistent)
        viewModel.navigateToSummary.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_logInFragment_to_summaryFragment)
                viewModel.onNavigatedToSummary()
            }
        }

        return binding.root
    }
}