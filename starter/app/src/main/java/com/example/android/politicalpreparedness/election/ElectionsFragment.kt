package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElectionsFragment: BaseFragment<FragmentElectionBinding>(R.layout.fragment_election) {

    private val viewModel by viewModel<ElectionsViewModel>()

    override fun initObserver() {}

    override fun initAction() {}

    override fun initView() {
        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters
    }

    //TODO: Refresh adapters when fragment loads

}