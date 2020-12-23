package com.lswitaj.moneymanager.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.databinding.FragmentLogInBinding
import com.lswitaj.moneymanager.databinding.FragmentSignUpBinding
import com.lswitaj.moneymanager.hideKeyboard

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
            showSnackbar(it)
        }

        return binding.root
    }

    //TODO(extract showSnackbar function to Utils)
    private fun showSnackbar(message: String) {
        view?.hideKeyboard()
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }
}