package com.javierestudio.appsosafe.mapModule

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.javierestudio.appsosafe.R
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.entities.ReviewsEntity
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.PoisResponse
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.ResultResponse
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.common.utils.TypeError
import com.javierestudio.appsosafe.databinding.FragmentMapBinding
import com.javierestudio.appsosafe.mapModule.viewModel.MapViewModel
import com.javierestudio.appsosafe.showInfoModule.ShowInfoFragment
import java.util.*
import kotlin.collections.HashMap


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    android.widget.SearchView.OnQueryTextListener, GoogleMap.OnMarkerClickListener{

    private var marker: Marker? = null

    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mMapViewModel: MapViewModel
    private lateinit var mContext : Context

    private lateinit var mMap: GoogleMap
    private var mMyLocation: LatLng? = null

    private var hashMap: HashMap<Marker, ResultResponse> = HashMap()
    private val reviewsList = arrayListOf<ReviewsEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMapBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = requireActivity().applicationContext

        setupMap()
        setupViewModel()
        setupSearchView()

    }

    private fun setupSearchView() {
        mBinding.searchView.queryHint = getString(R.string.map_fragment_hint_searchview)
        mBinding.searchView.setOnQueryTextListener(this)
    }

    private fun setupViewModel() {
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        mMapViewModel.getTypeError().observe(viewLifecycleOwner, { typeError ->
            val msgRes = when (typeError) {
                TypeError.GET -> getString(R.string.main_error_get)
                else -> getString(R.string.main_error_unkown)
            }
            Snackbar.make(mBinding.root, msgRes, Snackbar.LENGTH_SHORT)

        })

        mMapViewModel.getFavoritePlaceClicked().observe(viewLifecycleOwner){ placeEntity ->
            if (placeEntity != null){
                launchShowInfoFragment(placeEntity)
            } else {
                marker?.let {
                    launchShowInfoFragment(it)
                }
            }

        }

        mMapViewModel.isShowProggresBar().observe(viewLifecycleOwner,{ isVisible ->
            mBinding.progressBar.visibility = if(isVisible) View.VISIBLE else View.GONE
        })

        mMapViewModel.getPOIs().observe(viewLifecycleOwner, { poisResponse ->
            val listPois = mutableListOf<ResultResponse>()
            listPois.addAll(poisResponse.results)
            createMarkers(listPois)
        })
    }

    private fun createMarkers(listPois: MutableList<ResultResponse>) {
        hashMap.clear()
        mMap.clear()
        if (listPois.isNotEmpty()) {
            for (poi in listPois) {
                val latLng = LatLng(poi.geometry.location.lat, poi.geometry.location.lng)
                val marker = mMap.addMarker(
                    MarkerOptions().position(latLng).title(poi.name))

                hashMap[marker!!] = poi
            }
        } else{
            Snackbar.make(
                mBinding.root,
                getString(R.string.frag_map_list_pois_empty),
                Snackbar.LENGTH_SHORT
            )
        }
    }


    private fun setupMap() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableLocation()
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMarkerClickListener(this)
    }

    private fun getMyCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                mMyLocation = LatLng(location.latitude, location.longitude)
                firstCameraMoveToCurrentLocation()
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.frag_map_error_get_current_location),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun firstCameraMoveToCurrentLocation() {
        if(mMyLocation != null){
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(mMyLocation!!, 18f),
                5000, null
            )
        }
    }

    private fun enableLocation() {
        if(!::mMap.isInitialized) return
        if (isLocationPermissionGranted()){
            mMap.isMyLocationEnabled = true
            getMyCurrentLocation()
        } else requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        mContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                mMap.isMyLocationEnabled = true
                getMyCurrentLocation()
            } else {
                mMap.isMyLocationEnabled = false
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.frag_map_fine_permission_denied_activate_manually),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun searchByPoint(query: String) {
        if (mMyLocation != null)
            mMapViewModel.getAllPOIs(query, mMyLocation!!)
        else
        Toast.makeText(
            requireActivity(),
            getString(R.string.frag_map_msg_error_location_sv_submit),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        super.onResume()
        if(!::mMap.isInitialized) return
        if (!isLocationPermissionGranted()){
            mMap.isMyLocationEnabled = false
            Toast.makeText(
                requireContext(),
                getString(R.string.frag_map_msg_info_permission),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapViewModel.setTypeError(TypeError.NONE)
        mMapViewModel.setPOIs(PoisResponse(listOf(), "", listOf(), ""))
        mMapViewModel.setShowProggresBar(Constants.HIDE)
    }

    override fun onMyLocationButtonClick(): Boolean {
        getMyCurrentLocation()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            hideKeyboard()
            searchByPoint(query.toLowerCase(Locale.ENGLISH))
            mBinding.searchView.setQuery("", false)
        }
        else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.frag_map_msg_warning_sv_submit),
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        if (view != null) {
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    //Acá se viola uno de los principios SOLID |Segregación de interfaces| pero no sé como evitar que este método se implemente
    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        mMapViewModel.getPlaceByName(hashMap[marker]!!.name)
        this.marker = marker
        return true
    }

    private fun launchShowInfoFragment(clickedMarker: Marker?){
        val dataMarker = hashMap[clickedMarker]
        val fragment = ShowInfoFragment()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val bundle = Bundle()
        with(bundle){
            if (dataMarker != null) {
                putDouble(Constants.POI_LAT, dataMarker.geometry.location.lat)
                putDouble(Constants.POI_LNG, dataMarker.geometry.location.lng)
                putString(Constants.POI_PLACE_ID, dataMarker.placeId)
                putString(Constants.POI_NAME, dataMarker.name)
                putString(Constants.POI_VICINITY, dataMarker.vicinity)
                putDouble(Constants.POI_RATING, dataMarker.rating)
                if (dataMarker.photos != null) {
                    putString(Constants.POI_PHOTO, dataMarker.photos[0].photoReference)
                } else putString(Constants.POI_PHOTO, "")
            }
        }

        mBinding.map.visibility = View.GONE
        mBinding.searchView.visibility = View.GONE

        fragment.arguments = bundle
        fragmentTransaction.add(R.id.containerMainFragment, fragment, Constants.MAP_FRAGMENT)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun launchShowInfoFragment(entity: PlaceEntity) {
        val fragment = ShowInfoFragment()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val bundle = Bundle()
        with(bundle) {
            putLong(Constants.POI_ID, entity.idIntern)
            putDouble(Constants.POI_LAT, entity.coordinates.latitude)
            putDouble(Constants.POI_LNG, entity.coordinates.longitude)
            putString(Constants.POI_NAME, entity.name)
            putString(Constants.POI_VICINITY, entity.vicinity)
            putDouble(Constants.POI_RATING, entity.rating)
            putBoolean(Constants.POI_IS_FAV, entity.isFavorite)
            putString(Constants.POI_PHOTO, entity.photoPlace)
            for (reviews in entity.reviews) {
                reviewsList.add(reviews)
            }
            putParcelableArrayList(Constants.POI_REVIEWS, reviewsList)
        }

        mBinding.map.visibility = View.GONE
        mBinding.searchView.visibility = View.GONE

        fragment.arguments = bundle
        fragmentTransaction.add(R.id.containerMainFragment, fragment, Constants.FAV_FRAGMENT)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}