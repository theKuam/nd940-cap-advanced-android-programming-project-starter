package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.core.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import java.util.Locale

class RepresentativeFragment :
    BaseFragment<FragmentRepresentativeBinding, RepresentativeViewModel>(R.layout.fragment_representative) {

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 666
    }

    override fun initViewModel() {
        //TODO: Declare ViewModel
    }

    override fun initObserver() {}

    override fun initAction() {
        requestLocationPermissions()

        //TODO: Establish button listeners for field and location search
    }

    private fun requestLocationPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun initView() {

        //TODO: Define and assign Representative adapter

        //TODO: Populate Representative adapter
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
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    @Suppress("DEPRECATION")
    private fun geoCodeLocation(location: Location): Address? {
        var returnAddress: Address? = null
        context?.let {
            val geocoder = Geocoder(it, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                ) { addressList ->
                    returnAddress = addressList.map { address ->
                        Address(
                            address.thoroughfare,
                            address.subThoroughfare,
                            address.locality,
                            address.adminArea,
                            address.postalCode
                        )
                    }.first()
                }
            } else {
                returnAddress = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1)
                    ?.map { address ->
                        Address(
                            address.thoroughfare,
                            address.subThoroughfare,
                            address.locality,
                            address.adminArea,
                            address.postalCode
                        )
                    }?.first()
            }
        }
        return returnAddress
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}