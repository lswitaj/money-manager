package com.lswitaj.portfelmanager.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        // TODO(remove this super statement)
        // return super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentSummaryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}