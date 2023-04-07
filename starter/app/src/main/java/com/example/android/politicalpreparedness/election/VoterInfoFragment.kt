package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : BaseFragment<FragmentVoterInfoBinding, VoterInfoViewModel>(R.layout.fragment_voter_info) {

    override fun initViewModel() {
        //TODO: Add ViewModel values and create ViewModel
    }

    override fun initObserver() {
        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
    }

    override fun initAction() {
        //TODO: cont'd Handle save button clicks
    }

    override fun initView() {
        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */
    }

    //TODO: Create method to load URL intents

}