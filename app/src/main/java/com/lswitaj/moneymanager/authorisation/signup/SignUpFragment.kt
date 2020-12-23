package com.lswitaj.moneymanager.authorisation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.databinding.FragmentSignUpBinding
import com.lswitaj.moneymanager.utils.showSnackbar

class SignUpFragment : Fragment() {
    lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showSnackbar(view, it)
        }

        viewModel.navigateToSummary.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_signUpFragment_to_summaryFragment)
                viewModel.onNavigatedToLogIn()
            }
        }

        return binding.root
    }
}