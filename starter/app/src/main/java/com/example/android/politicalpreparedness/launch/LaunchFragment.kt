package com.example.android.politicalpreparedness.launch

import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding

class LaunchFragment : BaseFragment<FragmentLaunchBinding, ViewModel>(R.layout.fragment_launch) {
    override fun initViewModel() {}

    override fun initObserver() {}

    override fun initAction() {
//        binding.representativeButton.setOnClickListener { navToRepresentatives() }
//        binding.upcomingButton.setOnClickListener { navToElections() }
    }

    override fun initView() {}

    private fun navToElections() {
//        this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToElectionsFragment())
    }

    private fun navToRepresentatives() {
//        this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToRepresentativeFragment())
    }

}
