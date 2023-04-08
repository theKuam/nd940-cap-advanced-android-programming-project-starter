package com.example.android.politicalpreparedness.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.android.politicalpreparedness.common.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<DB: ViewDataBinding>(private val resourceId: Int) : AppCompatActivity() {

    protected lateinit var binding: DB
    protected val sharedViewModel: SharedViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, resourceId)
        initView()
        initAction()
        initObserver()
    }

    abstract fun initObserver()

    abstract fun initAction()

    abstract fun initView()
}