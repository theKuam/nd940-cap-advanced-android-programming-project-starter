package com.example.android.politicalpreparedness.ui.launch

import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.common.util.Screens
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding

class LaunchFragment : BaseFragment<FragmentLaunchBinding>(R.layout.fragment_launch) {

    override fun initObserver() {}

    override fun initAction() {
        binding.apply {
            btnUpcomingElections.setOnClickListener {
                navToLogin(Screens.ELECTIONS_SCREEN)
            }
            btnFindMyRepresentatives.setOnClickListener {
                navToLogin(Screens.REPRESENTATIVE_SCREEN)
            }
        }
    }

    override fun initView() {}

    private fun navToLogin(screenTo: Int) {
        this.findNavController()
            .navigate(LaunchFragmentDirections.actionLaunchFragmentToLoginFragment(screenTo))
    }
}
