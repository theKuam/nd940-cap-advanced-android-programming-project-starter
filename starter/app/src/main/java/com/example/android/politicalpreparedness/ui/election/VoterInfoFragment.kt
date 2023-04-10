package com.example.android.politicalpreparedness.ui.election

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoterInfoFragment : BaseFragment<FragmentVoterInfoBinding>(R.layout.fragment_voter_info) {

    private val voterInfoViewModel by viewModel<VoterInfoViewModel>()
    private var electionId: Int = 0

    override fun initObserver() {
        sharedViewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        sharedAddress.collectLatest { address ->
                            voterInfoViewModel.getVoterInfo(address, electionId)
                        }
                    }
                    launch {
                        voterInfoViewModel.voterInfo.collectLatest { voterInfoResponse ->
                            setElectionName(voterInfoResponse?.election?.name)
                            enableLink(
                                binding.stateLocationsHeader,
                                binding.stateBallotHeader,
                                voterInfoResponse?.state?.first()
                            )
                        }
                    }
                    launch {
                        voterInfoViewModel.saveState.collectLatest { isSaved ->
                            if (isSaved) {
                                binding.btnFollow.text = getString(R.string.unfollow_election)
                            } else {
                                binding.btnFollow.text = getString(R.string.follow_election)
                            }
                        }
                    }
                    launch {
                        voterInfoViewModel.errorState.collectLatest {
                            if (it != "") {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                                voterInfoViewModel.resetErrorState()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun enableLink(stateLocation: TextView, ballotInfo: TextView, state: State?) {
        state?.electionAdministrationBody?.let { body ->
            stateLocation.setOnClickListener { setIntent(body.electionInfoUrl ?: "") }
            ballotInfo.setOnClickListener { setIntent(body.ballotInfoUrl ?: "") }
        }
    }

    private fun setIntent(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        requireContext().startActivity(intent)
    }

    override fun initAction() {
        binding.btnFollow.setOnClickListener {
            voterInfoViewModel.onClickFollowButton(electionId)
        }
    }

    override fun initView() {
        setUpElection()
        setUpDataBinding()
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */
    }

    private fun setUpElection() {
        electionId = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElectionId
        voterInfoViewModel.checkSaveState(electionId)
    }

    private fun setUpDataBinding() {
        binding.apply {
            viewModel = voterInfoViewModel
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }

    override fun onDestroyView() {
        sharedViewModel.setElectionName("")
        super.onDestroyView()
    }
}