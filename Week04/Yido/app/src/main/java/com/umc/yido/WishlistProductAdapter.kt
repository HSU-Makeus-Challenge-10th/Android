package com.umc.yido

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.yido.databinding.ItemProductWishlistBinding

class WishlistProductAdapter(private val productList: MutableList<Product>)
    : RecyclerView.Adapter<WishlistProductAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(val binding: ItemProductWishlistBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.itemWishIvProduct.setImageResource(product.imageResId)
            binding.itemWishTvName.text = product.name
            binding.itemWishTvPrice.text = product.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding = ItemProductWishlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateList(newList: MutableList<Product>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}
