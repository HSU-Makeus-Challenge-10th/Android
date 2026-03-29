package com.example.week02.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week02.data.ProductItem
import com.example.week02.databinding.ItemHomeNewProductBinding

class HomeNewProductAdapter(
    private val items: List<ProductItem>,
) : RecyclerView.Adapter<HomeNewProductAdapter.VH>() {

    class VH(val binding: ItemHomeNewProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemHomeNewProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.ivProduct.setImageResource(item.imageResId)
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = item.price
    }

    override fun getItemCount() = items.size
}
