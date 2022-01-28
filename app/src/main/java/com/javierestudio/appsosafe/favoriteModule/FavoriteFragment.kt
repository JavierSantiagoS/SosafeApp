package com.javierestudio.appsosafe.favoriteModule

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.javierestudio.appsosafe.R
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.entities.ReviewsEntity
import com.javierestudio.appsosafe.common.interfacesAux.HomeAux
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.databinding.FragmentFavoriteBinding
import com.javierestudio.appsosafe.favoriteModule.adapters.FavoriteAdapter
import com.javierestudio.appsosafe.favoriteModule.adapters.OnClickListener
import com.javierestudio.appsosafe.favoriteModule.viewModel.FavoriteViewModel
import com.javierestudio.appsosafe.showInfoModule.ShowInfoFragment

class FavoriteFragment : Fragment(), HomeAux, OnClickListener {

    private lateinit var mAdapter : FavoriteAdapter
    private lateinit var mGridLayout: GridLayoutManager

    private lateinit var mBinding: FragmentFavoriteBinding
    private lateinit var mFavoriteViewModel: FavoriteViewModel

    private lateinit var mContext : Context

    private val reviewsList = arrayListOf<ReviewsEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireActivity().applicationContext

        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mFavoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        mFavoriteViewModel.getFavoritePlaces().observe(viewLifecycleOwner){ favoritePlaces ->
            mBinding.progressBar.visibility = View.GONE
            mAdapter.submitList(favoritePlaces)
        }

        mFavoriteViewModel.isShowProggresBar().observe(viewLifecycleOwner) { isShowProgress ->
            mBinding.progressBar.visibility = if (isShowProgress) View.VISIBLE else View.GONE
        }
    }

    private fun setupRecyclerView() {
        mAdapter = FavoriteAdapter(this)
        mGridLayout = GridLayoutManager(mContext, resources.getInteger(R.integer.grid_span_value))
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mFavoriteViewModel.setShowProggresBar(Constants.HIDE)
    }



    override fun goToTop() {
        //mBinding.recyclerView.smoothScrollToPosition(0)
    }

    override fun onClick(placeEntity: PlaceEntity) {
        launchShowInfoFragment(placeEntity)
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
            reviewsList.clear()
            for (reviews in entity.reviews) {
                reviewsList.add(reviews)
            }
            putParcelableArrayList(Constants.POI_REVIEWS, reviewsList)
        }

        mBinding.recyclerView.visibility = View.GONE

        fragment.arguments = bundle
        fragmentTransaction.add(R.id.containerFragmentFav, fragment, Constants.FAV_FRAGMENT)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}