package com.lswitaj.moneymanager.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.databinding.FragmentLogInBinding
import com.lswitaj.moneymanager.showSnackbar

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

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showSnackbar(view, it)
        }

        return binding.root
    }
}