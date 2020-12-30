package com.lswitaj.moneymanager.summary

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.authorisation.login.LogInFragment
import com.lswitaj.moneymanager.data.database.SymbolsDatabase
import com.lswitaj.moneymanager.databinding.FragmentSummaryBinding
import com.lswitaj.moneymanager.utils.showSnackbar
import java.lang.Thread.sleep
import kotlin.reflect.typeOf

//val args: LogInFragment by navArgs()

class SummaryFragment : Fragment() {
    lateinit var viewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSummaryBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = SymbolsDatabase.getInstance(application).symbolsDatabaseDao
        val viewModelFactory = SummaryViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SummaryViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.summaryList.adapter = SummaryListAdapter()

        viewModel.refreshSymbols(requireArguments().getBoolean("downloadDataFromServer"))

        viewModel.navigateToSearch.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_summaryFragment_to_searchFragment)
                viewModel.onNavigatedToSearch()
            }
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
                showSnackbar(view, getString(R.string.autorisation_error_message))
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_summaryFragment_to_logInFragment)
                viewModel.onNavigatedToLogin()
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

        inflater.inflate(R.menu.summary_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> viewModel.logOut()
            else -> showSnackbar(view, getString(R.string.not_implemented_error_message))
        }
        return true
    }
}