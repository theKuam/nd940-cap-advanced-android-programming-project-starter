package com.example.android.politicalpreparedness.ui.login

import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.common.SharedViewModel
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun initObserver() {
        sharedViewModel.authenticationState.observe(this) { authenticationState ->
            when (authenticationState) {
                SharedViewModel.AuthenticationState.AUTHENTICATED -> {
                    navToRepresentatives()
                }
                else -> {}
            }
        }
    }

    private fun navToRepresentatives() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRepresentativeFragment())
    }

    override fun initAction() {
        binding.btnLogin.setOnClickListener {
            launchSignInFlow()
        }
    }

    override fun initView() {}
}