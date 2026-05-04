package com.example.week02.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.week02.R
import com.example.week02.data.ItemList
import com.example.week02.databinding.ItemShopProductBinding

class ShopProductAdapter(
    private val onHeartClick: (ItemList) -> Unit,
    private val onItemClick: (ItemList) -> Unit
) : RecyclerView.Adapter<ShopProductAdapter.ShopProductViewHolder>() {

    private val items = mutableListOf<ItemList>()

    inner class ShopProductViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopProductViewHolder {
        val binding = ItemShopProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShopProductViewHolder(binding)
    }

    fun updateItems(newItems: List<ItemList>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ShopProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
