package com.example.android.politicalpreparedness.launch

import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding

class LaunchFragment : BaseFragment<FragmentLaunchBinding>(R.layout.fragment_launch) {

    override fun initObserver() {}

    override fun initAction() {
        binding.apply {
            btnUpcomingElections.setOnClickListener {
                navToElections()
            }
            btnFindMyRepresentatives.setOnClickListener {
                navToLogin()
            }
        }
    }

    override fun initView() {}

    private fun navToElections() {
        this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToElectionsFragment())
    }

    private fun navToLogin() {
        this.findNavController().navigate(LaunchFragmentDirections.actionLaunchFragmentToLoginFragment())
    }

}
