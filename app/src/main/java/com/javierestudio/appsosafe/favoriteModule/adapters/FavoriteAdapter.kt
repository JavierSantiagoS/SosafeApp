package com.javierestudio.appsosafe.favoriteModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javierestudio.appsosafe.R
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.utils.GlideHelper
import com.javierestudio.appsosafe.databinding.ItemFavoritePlaceBinding

class FavoriteAdapter(private var listener: OnClickListener): ListAdapter<PlaceEntity,
        RecyclerView.ViewHolder>(FavoriteDiffCallback()) {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_place, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val placeData = getItem(position)

        with(holder as FavoriteAdapter.ViewHolder) {
            setListener(placeData)
            binding.tvName.text = placeData.name
            binding.tvVicinity.text = placeData.vicinity
            GlideHelper.getGlideRoom(mContext, placeData.photoPlace , binding.imgPhoto)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFavoritePlaceBinding.bind(view)

        fun setListener(placeEntity: PlaceEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(placeEntity) }
            }
        }
    }
}

class FavoriteDiffCallback: DiffUtil.ItemCallback<PlaceEntity>() {
    override fun areContentsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
        return oldItem.idIntern == newItem.idIntern
    }

    override fun areItemsTheSame(oldItem: PlaceEntity, newItem: PlaceEntity): Boolean {
        return oldItem == newItem
    }
}