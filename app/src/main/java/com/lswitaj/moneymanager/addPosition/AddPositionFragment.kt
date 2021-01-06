package com.lswitaj.moneymanager.addPosition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.lswitaj.moneymanager.R
import com.lswitaj.moneymanager.data.database.PositionsDatabase
import com.lswitaj.moneymanager.databinding.FragmentAddPositionBinding
import com.lswitaj.moneymanager.utils.showSnackbar

class AddPositionFragment : Fragment() {
    lateinit var viewModel: AddPositionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddPositionBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        val dataSource = PositionsDatabase.getInstance(application).positionsDatabaseDao
        val args: AddPositionFragmentArgs by navArgs()
        val positionName = args.positionName
        val viewModelFactory = AddPositionViewModelFactory(dataSource, positionName)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddPositionViewModel::class.java)

        binding.lifecycleOwner = this

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showSnackbar(view, it)
        }

        viewModel.navigateToSummary.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_addPositionFragment_to_summaryFragment)
                viewModel.addNewPositionComplete()
            }
        }

        return binding.root
    }
}