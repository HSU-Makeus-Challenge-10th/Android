package com.example.week02.profile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.week02.R
import com.example.week02.databinding.ItemFollowingUserBinding
import com.example.week02.profile.data.remote.dto.ReqResUserDto

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val items = mutableListOf<ReqResUserDto>()

    inner class FollowingViewHolder(
        private val binding: ItemFollowingUserBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReqResUserDto) {
            binding.ivAvatar.load(item.avatar) {
                crossfade(true)
                placeholder(R.drawable.nike_logo)
                error(R.drawable.nike_logo)
            }
            binding.tvName.text = item.fullName
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

    fun updateItems(newItems: List<ReqResUserDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
