package com.example.deku.feature.profile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.deku.R
import com.example.deku.databinding.ItemFollowingUserBinding
import com.example.deku.feature.profile.model.ProfileUser

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val items = mutableListOf<ProfileUser>()

    inner class FollowingViewHolder(
        private val binding: ItemFollowingUserBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileUser) {
            binding.ivAvatar.load(item.avatarUrl) {
                crossfade(true)
                placeholder(R.drawable.nike_logo)
                error(R.drawable.nike_logo)
            }
            binding.tvName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemFollowingUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ProfileUser>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
