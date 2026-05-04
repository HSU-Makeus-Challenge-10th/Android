package com.umc.yido

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.yido.databinding.ItemProductGridBinding

class ShopProductAdapter(
    private val productList: MutableList<Product>,
    private val onWishClick: (Product) -> Unit = {}
) : RecyclerView.Adapter<ShopProductAdapter.ShopViewHolder>() {

    inner class ShopViewHolder(val binding: ItemProductGridBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.itemGridIvProduct.setImageResource(product.imageResId)
            binding.itemGridTvName.text = product.name
            binding.itemGridTvPrice.text = product.price

            if (product.isWished) {
                binding.itemGridIvWish.setImageResource(R.drawable.ic_heart_filled_red)
            } else {
                binding.itemGridIvWish.setImageResource(R.drawable.ic_heart_outline)
            }

            binding.itemGridIvWish.setOnClickListener {
                onWishClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemProductGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
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
