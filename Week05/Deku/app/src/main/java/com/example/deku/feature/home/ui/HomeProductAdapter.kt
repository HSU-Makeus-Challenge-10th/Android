package com.example.deku.feature.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deku.databinding.ItemHomeProductBinding
import com.example.deku.product.data.ItemList

class HomeProductAdapter(
    private val onItemClick: (ItemList) -> Unit
) : RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>() {

    private val items = mutableListOf<ItemList>()

    inner class HomeProductViewHolder(
        private val binding: ItemHomeProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemList) {
            binding.ivProduct.setImageResource(item.imageResId)
            binding.tvName.text = item.name
            binding.tvPrice.text = item.price
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductViewHolder {
        val binding = ItemHomeProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeProductViewHolder(binding)
    }

    fun updateItems(newItems: List<ItemList>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
