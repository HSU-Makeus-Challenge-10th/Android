package com.umc.yido

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.yido.databinding.ItemProductHomeBinding

class HomeProductAdapter(private val productList: MutableList<Product>)
    : RecyclerView.Adapter<HomeProductAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: ItemProductHomeBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.itemHomeIvProduct.setImageResource(product.imageResId)
            binding.itemHomeTvName.text = product.name
            binding.itemHomeTvPrice.text = product.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemProductHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
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
