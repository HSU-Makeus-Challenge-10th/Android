package com.example.umc10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc10th.databinding.ItemFollowingProfileBinding

class FollowingProfileAdapter(
    private var profiles: List<FollowingProfile> = emptyList()
) : RecyclerView.Adapter<FollowingProfileAdapter.FollowingProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingProfileViewHolder {
        val binding = ItemFollowingProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FollowingProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingProfileViewHolder, position: Int) {
        holder.bind(profiles[position])
    }

    override fun getItemCount(): Int = profiles.size

    fun submitList(newProfiles: List<FollowingProfile>) {
        profiles = newProfiles
        notifyDataSetChanged()
    }

    class FollowingProfileViewHolder(
        private val binding: ItemFollowingProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: FollowingProfile) {
            binding.ivFollowingProfile.setImageBitmap(profile.avatarBitmap)
        }
    }
}
