package com.example.week02.ui.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week02.R
import com.example.week02.data.ProductItem
import com.example.week02.databinding.ItemShopProductBinding

class ShopProductAdapter(
    private val items: MutableList<ProductItem>,
    private val onHeartClick: ((position: Int) -> Unit)? = null,
) : RecyclerView.Adapter<ShopProductAdapter.VH>() {

    class VH(val binding: ItemShopProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemShopProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val b = holder.binding
        b.ivProduct.setImageResource(item.imageResId)
        b.tvName.text = item.name
        if (item.subtitle != null) {
            b.tvSubtitle.visibility = View.VISIBLE
            b.tvSubtitle.text = item.subtitle
        } else {
            b.tvSubtitle.visibility = View.GONE
        }
        if (item.colorsLabel != null) {
            b.tvColors.visibility = View.VISIBLE
            b.tvColors.text = item.colorsLabel
        } else {
            b.tvColors.visibility = View.GONE
        }
        b.tvPrice.text = item.price
        b.tvBestSeller.visibility = if (item.isBestSeller) View.VISIBLE else View.GONE
        if (item.showHeart) {
            b.ivHeart.visibility = View.VISIBLE
            b.ivHeart.setImageResource(
                if (item.heartFilled) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline,
            )
            b.ivHeart.setOnClickListener {
                val pos = holder.bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
                if (onHeartClick != null) {
                    onHeartClick.invoke(pos)
                } else {
                    val current = items[pos]
                    items[pos] = current.copy(heartFilled = !current.heartFilled)
                    notifyItemChanged(pos)
                }
            }
        } else {
            b.ivHeart.visibility = View.GONE
            b.ivHeart.setOnClickListener(null)
        }
    }

    override fun getItemCount() = items.size
}
