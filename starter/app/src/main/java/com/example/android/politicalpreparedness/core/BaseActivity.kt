package com.example.android.politicalpreparedness.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB: ViewDataBinding>(private val resourceId: Int) : AppCompatActivity() {

    private lateinit var binding: DB
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