package com.example.week02.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.week02.data.remote.reqres.ReqresUser
import com.example.week02.databinding.ItemFollowingAvatarBinding

class FollowingAvatarAdapter : ListAdapter<ReqresUser, FollowingAvatarAdapter.VH>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemFollowingAvatarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(
        private val binding: ItemFollowingAvatarBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReqresUser) {
            binding.ivAvatar.load(item.avatar)
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<ReqresUser>() {
            override fun areItemsTheSame(oldItem: ReqresUser, newItem: ReqresUser): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ReqresUser, newItem: ReqresUser): Boolean =
                oldItem == newItem
        }
    }
}

