package com.example.android.politicalpreparedness.ui.representative

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.ui.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RepresentativeFragment :
    BaseFragment<FragmentRepresentativeBinding>(R.layout.fragment_representative) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var representativeListAdapter: RepresentativeListAdapter

    private val representativeViewModel by viewModel<RepresentativeViewModel>()

    @ExperimentalCoroutinesApi
    override fun initObserver() {
        representativeViewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        country.collect {
                            getCountryState(it)
                        }
                    }
                    launch {
                        representatives.collect {
                            representativeListAdapter.submitList(it)
                        }
                    }

                    launch {
                        address.collectLatest {
                            sharedViewModel.setSharedAddress(it)
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

    override fun initAction() {
        requestLocationPermissions()

        binding.apply {
            btnSearch.setOnClickListener {
                representativeViewModel.findMyRepresentatives()
                hideKeyboard()
            }
            btnLocation.setOnClickListener {
                getLocation()
            }
        }
    }

    private fun checkPermission(it: FragmentActivity) =
        ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun initView() {
        coordinateMotion()
        bindRepresentativeList()
        setUpDataBinding()
    }

    private fun setUpDataBinding() {
        binding.apply {
            viewModel = representativeViewModel
            lifecycleOwner = viewLifecycleOwner
            representativeViewModel.setDefaultStates()
            executePendingBindings()
        }
    }

    private fun bindRepresentativeList() {
        representativeListAdapter = RepresentativeListAdapter()
        binding.rvRepresentatives.adapter = representativeListAdapter
        binding.rvRepresentatives.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun coordinateMotion() {
        binding.apply {
            val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
                motionLayout.progress = seekPosition
            }

            appBarLayout.addOnOffsetChangedListener(listener)
            appBarLayout.outlineProvider = null
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {

            }
            else -> {

            }
        }
    }

    private fun getLocation() {
        activity?.let {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
            if (checkPermission(it)) {
                return@let
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { lastLocation ->
                if (lastLocation != null) {
                    geoCodeLocation(lastLocation)
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.location_required),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun geoCodeLocation(location: Location) {
        context?.let {
            val geocoder = Geocoder(it, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                ) { addressList ->
                    val address = addressList.map { address ->
                        Address(
                            address.countryName,
                            address.thoroughfare,
                            address.subThoroughfare,
                            address.locality,
                            address.adminArea,
                            address.postalCode
                        )
                    }.first()
                    representativeViewModel.useMyLocation(address)
                    representativeViewModel.setState(address.state)

                }
            } else {
                val address = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                    ?.map { address ->
                        Address(
                            address.countryName,
                            address.thoroughfare,
                            address.subThoroughfare,
                            address.locality,
                            address.adminArea,
                            address.postalCode
                        )
                    }?.first()
                representativeViewModel.useMyLocation(address)
                representativeViewModel.setState(address?.state)
            }
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}