package com.javierestudio.appsosafe.showInfoModule

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.javierestudio.appsosafe.R
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.entities.ReviewsEntity
import com.javierestudio.appsosafe.common.entities.mapListToDb
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.PlaceDetailsResponse
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.ReviewResponse
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.mapListToDb
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.common.utils.GlideHelper
import com.javierestudio.appsosafe.common.utils.TypeError
import com.javierestudio.appsosafe.databinding.FragmentShowInfoBinding
import com.javierestudio.appsosafe.showInfoModule.adapters.ShowInfoAdapter
import com.javierestudio.appsosafe.showInfoModule.viewModel.ShowInfoViewModel
import java.util.ArrayList


class ShowInfoFragment: Fragment(), OnMapReadyCallback {

    private lateinit var mAdapter : ShowInfoAdapter
    private lateinit var mLinearLayout: LinearLayoutManager
    private var reviews = mutableListOf<ReviewResponse>()
    private lateinit var reviewsEntity: List<ReviewsEntity>

    private lateinit var mPlaceEntity: PlaceEntity

    private lateinit var mBinding: FragmentShowInfoBinding
    private lateinit var mShowInfoViewModel: ShowInfoViewModel


    private lateinit var mMap: GoogleMap

    private lateinit var mContext : Context

    private var placeLocation: LatLng? = null

    private var poiId: Long? = null
    private var placeId: String? = null
    private var placeName: String? = null
    private var placeVicinity: String? = null
    private var placeRating: Double? = null
    private var isFavorite: Boolean? = null
    private var placePhoto: String? = null
    private var reviewsList: List<ReviewsEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentShowInfoBinding.inflate(inflater, container, false)

        if (tag!! == Constants.MAP_FRAGMENT){
            getArgumentsFromMap(arguments)
        } else if (tag == Constants.FAV_FRAGMENT){
            getArgumentsFromFav(arguments)
        }
        onBackPress()
        return mBinding.root
    }

    private fun getArgumentsFromMap(arguments: Bundle?) {
        if (arguments != null) {
            with(requireArguments()) {
                placeLocation = LatLng(
                    getDouble(Constants.POI_LAT),
                    getDouble(Constants.POI_LNG)
                )
                placeId = getString(Constants.POI_PLACE_ID)
                placeName = getString(Constants.POI_NAME)
                placeVicinity = getString(Constants.POI_VICINITY)
                placeRating = getDouble(Constants.POI_RATING)
                placePhoto = getString(Constants.POI_PHOTO)
            }
        }
    }

    private fun getArgumentsFromFav(arguments: Bundle?) {
        if (arguments != null) {
            with(requireArguments()) {
                placeLocation = LatLng(
                    getDouble(Constants.POI_LAT),
                    getDouble(Constants.POI_LNG)
                )
                poiId = getLong("POI_ID")
                placeName = getString(Constants.POI_NAME)
                placeVicinity = getString(Constants.POI_VICINITY)
                placeRating = getDouble(Constants.POI_RATING)
                placePhoto = getString(Constants.POI_PHOTO)
                isFavorite = getBoolean("POI_IS_FAV")
                reviewsList = getParcelableArrayList("POI_REVIEWS")
                reviews.addAll((reviewsList as ArrayList<ReviewsEntity>).mapListToDb())
            }
        }
        mPlaceEntity = PlaceEntity(poiId!!, placeLocation!!, placeName!!, placeVicinity!!, placeRating!!, isFavorite!!, placePhoto!!, reviewsList!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireActivity().applicationContext

        setupMap()
        setupViewModel()
        if (tag == Constants.MAP_FRAGMENT) {
            mShowInfoViewModel.getAllPlaceDetails(placeId!!)
        } else if(tag == Constants.FAV_FRAGMENT){
            setupRecyclerView()
            setupData()
        }
        switchListener()

    }

    private fun switchListener() {
        mBinding.swSave.setOnCheckedChangeListener { _ , isChecked ->
            reviewsEntity = reviews.mapListToDb()
            if (isChecked){
                mPlaceEntity = PlaceEntity(0L, placeLocation!!, placeName!!, placeVicinity!!, placeRating!!, mBinding.swSave.isChecked, placePhoto!!, reviewsEntity)
                mShowInfoViewModel.saveFavoritePlace(mPlaceEntity)
            } else {
                mShowInfoViewModel.deleteFavoritePlace(mPlaceEntity)
            }
        }
    }

    private fun setupRecyclerView() {
        mAdapter = ShowInfoAdapter()
        mLinearLayout = LinearLayoutManager(mContext)
        mBinding.recycler.apply {
            layoutManager = mLinearLayout
            adapter = mAdapter
        }
        if (reviews.isNullOrEmpty()){
            mBinding.tvNoData.visibility = View.VISIBLE
        } else {
            mAdapter.submitList(reviews)
        }
    }

    private fun setupMap() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapShowInfo) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupViewModel() {
        mShowInfoViewModel = ViewModelProvider(this)[ShowInfoViewModel::class.java]

        mShowInfoViewModel.getTypeError().observe(viewLifecycleOwner, { typeError ->
            val msgRes = when (typeError) {
                TypeError.GET -> getString(R.string.main_error_get)
                else -> getString(R.string.main_error_unkown)
            }
            Snackbar.make(mBinding.root, msgRes, Snackbar.LENGTH_SHORT)

        })

        mShowInfoViewModel.isShowProggresBar().observe(viewLifecycleOwner,{ isVisible ->
            mBinding.progressBarShowInfo.visibility = if(isVisible) View.VISIBLE else View.GONE
        })

        mShowInfoViewModel.getPlaceDetails().observe(viewLifecycleOwner, { placeDetailsResponse ->
            if (placeDetailsResponse.result!!.reviews != null) {
                reviews.addAll(placeDetailsResponse.result!!.reviews)
                setupRecyclerView()
            }else{
                mBinding.tvNoData.visibility = View.VISIBLE
            }
            setupData()
        })
    }

    private fun setupData() {
        mBinding.tvPoiName.text = placeName
        mBinding.tvPoiDirection.text = placeVicinity
        mBinding.tvPoiRating.text = placeRating.toString()
        GlideHelper.getGlide(this, placePhoto!!, mBinding.imgPoi)
        if (tag == Constants.FAV_FRAGMENT)
        mBinding.swSave.isChecked = isFavorite!!
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (isLocationPermissionGranted()) {
            mMap.isMyLocationEnabled = true
            moveToDevicePosition()
        }
    }

        private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun moveToDevicePosition() {
        if(placeLocation != null){
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(placeLocation!!, 18f),
                1000, null
            )
            mMap.addMarker(
                MarkerOptions().position(placeLocation!!).title(placeName))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.tvNoData.visibility = View.GONE
        mShowInfoViewModel.setTypeError(TypeError.NONE)
        mShowInfoViewModel.setPlacesDetails(PlaceDetailsResponse(listOf(), null, ""))
        mShowInfoViewModel.setShowProggresBar(Constants.HIDE)
    }

    private fun onBackPress(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (tag!! == Constants.MAP_FRAGMENT) {
                        parentFragmentManager.popBackStack()
                        requireActivity().findViewById<View>(R.id.map).visibility = View.VISIBLE
                        requireActivity().findViewById<View>(R.id.searchView).visibility =
                            View.VISIBLE
                        requireActivity().findViewById<View>(R.id.recyclerView).visibility = View.VISIBLE
                    } else if(tag!! == Constants.FAV_FRAGMENT){
                        parentFragmentManager.popBackStack()
                        requireActivity().findViewById<View>(R.id.map).visibility = View.VISIBLE
                        requireActivity().findViewById<View>(R.id.searchView).visibility =
                            View.VISIBLE
                        requireActivity().findViewById<View>(R.id.recyclerView).visibility = View.VISIBLE
                    }
                }
            }
        )
    }
}