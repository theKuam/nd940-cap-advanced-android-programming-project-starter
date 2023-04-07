package com.example.android.politicalpreparedness.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<DB: ViewDataBinding, VM: ViewModel>(private val resourceId: Int) : Fragment() {

    private lateinit var binding: DB
    private lateinit var viewModel: VM

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
        initViewModel()
        initView()
        initAction()
        initObserver()
        return binding.root
    }

    abstract fun initViewModel()

    abstract fun initObserver()

    abstract fun initAction()

    abstract fun initView()
}