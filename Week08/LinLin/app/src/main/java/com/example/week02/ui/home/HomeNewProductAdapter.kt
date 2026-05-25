package com.example.week02.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week02.R
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
        // 홈 카드만 사용. JSON/DataStore에서 온 구형 imageResId를 쓰지 않고 id로 drawable 고정.
        holder.binding.ivProduct.setImageResource(homeRowDrawable(item.id))
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = item.price
    }

    private fun homeRowDrawable(productId: Int): Int = when (productId) {
        1 -> R.drawable.img_air_jordan_xxxvi
        2 -> R.drawable.img_nike_air_force_1_07
        3 -> R.drawable.img_nike_elite_crew
        4 -> R.drawable.img_nike_everyday_plus_cushioned
        5 -> R.drawable.img_jordan_nike_af1_07_essentials
        else -> R.drawable.bg_product_placeholder
    }

    override fun getItemCount() = items.size
}
