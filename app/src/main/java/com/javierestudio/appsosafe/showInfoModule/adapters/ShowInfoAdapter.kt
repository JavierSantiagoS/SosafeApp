package com.javierestudio.appsosafe.showInfoModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javierestudio.appsosafe.R
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.ReviewResponse
import com.javierestudio.appsosafe.common.utils.GlideHelper
import com.javierestudio.appsosafe.databinding.ItemReviewBinding

class ShowInfoAdapter : ListAdapter<ReviewResponse,
        RecyclerView.ViewHolder>(ShowInfoDiffCallback()) {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val review = getItem(position)

        with(holder as ViewHolder) {
            binding.itemTvName.text = review.authorName
            binding.itemTvTime.text = review.relativeTimeDescription
            binding.tvReview.text = review.text
            binding.tvRatingReview.text = review.rating.toString()
            GlideHelper.getGlide(mContext, review.profilePhotoUrl, binding.itemImgProfile)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemReviewBinding.bind(view)
    }

}

class ShowInfoDiffCallback: DiffUtil.ItemCallback<ReviewResponse>(){
    override fun areContentsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.authorName == newItem.authorName
    }

    override fun areItemsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem == newItem
    }

}