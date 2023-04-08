package com.example.android.politicalpreparedness.core

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.common.SharedViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

abstract class BaseFragment<DB: ViewDataBinding>(private val resourceId: Int) : Fragment() {

    protected lateinit var binding: DB
    protected val sharedViewModel: SharedViewModel by activityViewModel()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        onSignInResult(result)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(
                inflater,
                resourceId,
                container,
                false
            )
        initView()
        initAction()
        initObserver()
        return binding.root
    }

    abstract fun initObserver()

    abstract fun initAction()

    abstract fun initView()

    protected fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult?) {
        val response = result?.idpResponse
        if (result?.resultCode == Activity.RESULT_OK) {
            Timber.i("Successfully signed in user " +
                    "${FirebaseAuth.getInstance().currentUser?.displayName}!")
        } else {
            Timber.i("Sign in unsuccessful ${response?.error?.errorCode}")
        }
    }
}