package com.example.android.politicalpreparedness.ui.election

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.ui.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.ui.election.adapter.ElectionListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElectionsFragment : BaseFragment<FragmentElectionBinding>(R.layout.fragment_election) {

    private lateinit var upcomingElectionListAdapter: ElectionListAdapter
    private lateinit var savedElectionListAdapter: ElectionListAdapter

    private val electionViewModel by viewModel<ElectionsViewModel>()

    override fun initObserver() {
        electionViewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        upcomingElections.collect {
                            upcomingElectionListAdapter.submitList(it)
                        }
                    }
                    launch {
                        savedElections.collect {
                            savedElectionListAdapter.submitList(it)
                        }
                    }
                    launch {
                        errorState.collectLatest {
                            if (it != "") {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                                resetErrorState()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initAction() {}

    override fun initView() {
        bindingElectionList()
        setUpDataBinding()
        setUpElectionList()
        //TODO: Link elections to voter info
    }

    private fun bindingElectionList() {
        upcomingElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            navToVoterInfo(election)
        })
        savedElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            navToVoterInfo(election)
        })
        binding.rvUpcomingElections.apply {
            adapter = upcomingElectionListAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.rvSavedElections.apply {
            adapter = savedElectionListAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun navToVoterInfo(election: Election) {
        findNavController().navigate(
            ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                election.id,
                election.division
            )
        )
    }

    private fun setUpElectionList() {
        electionViewModel.apply {
            fetchUpcomingElections()
            getSavedElections()
        }
    }

    private fun setUpDataBinding() {
        binding.apply {
            viewModel = electionViewModel
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }

    //TODO: Refresh adapters when fragment loads

}