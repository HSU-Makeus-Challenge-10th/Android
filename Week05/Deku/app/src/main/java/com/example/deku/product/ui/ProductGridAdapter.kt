package com.example.deku.product.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.deku.R
import com.example.deku.databinding.ItemShopProductBinding
import com.example.deku.product.data.ItemList

class ProductGridAdapter(
    private val onHeartClick: (ItemList) -> Unit,
    private val onItemClick: (ItemList) -> Unit
) : RecyclerView.Adapter<ProductGridAdapter.ProductGridViewHolder>() {

    private val items = mutableListOf<ItemList>()

    inner class ProductGridViewHolder(
        private val binding: ItemShopProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemList) {
            binding.ivProduct.setImageResource(item.imageResId)
            binding.tvName.text = item.name
            binding.tvCategory.text = item.category
            binding.tvColorCount.text = "${item.colorCount} Color"
            binding.tvPrice.text = item.price
            val heartDrawable = if (item.isWish) {
                R.drawable.heart_filled
            } else {
                R.drawable.heart
            }
            binding.ivHeart.setImageDrawable(
                ContextCompat.getDrawable(binding.root.context, heartDrawable)
            )
            binding.ivHeart.setOnClickListener {
                onHeartClick(item)
            }
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductGridViewHolder {
        val binding = ItemShopProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductGridViewHolder(binding)
    }

    fun updateItems(newItems: List<ItemList>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductGridViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
