package com.lswitaj.moneymanager.summary

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.data.database.PositionsDatabase
import com.lswitaj.moneymanager.databinding.FragmentSummaryBinding
import com.lswitaj.moneymanager.utils.showSnackbar


class SummaryFragment : Fragment() {
    lateinit var viewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSummaryBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = PositionsDatabase.getInstance(application).positionsDatabaseDao
        val summaryAdapter = SummaryListAdapter()
        val viewModelFactory = SummaryViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SummaryViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.summaryList.adapter = summaryAdapter

        binding.apply {
            summaryList.apply {
                adapter = summaryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = summaryAdapter.currentList[viewHolder.adapterPosition]
                    viewModel!!.onPositionSwiped(position)
                }
            }).attachToRecyclerView(summaryList)
        }

        viewModel.navigateToSearch.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_summaryFragment_to_searchFragment)
                viewModel.onNavigatedToSearch()
            }
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
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
            R.id.action_logout -> {
                viewModel.logOut()
            }
            else -> showSnackbar(view, getString(R.string.not_implemented_error_message))
        }
        return true
    }
}