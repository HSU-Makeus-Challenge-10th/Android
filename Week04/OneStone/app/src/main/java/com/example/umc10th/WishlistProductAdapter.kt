package com.example.umc10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc10th.databinding.ItemWishlistProductBinding

class WishlistProductAdapter(
    private var items: List<PurchaseProduct>,
    private val onItemClick: (PurchaseProduct) -> Unit
) : RecyclerView.Adapter<WishlistProductAdapter.WishlistProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistProductViewHolder {
        val binding = ItemWishlistProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishlistProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<PurchaseProduct>){
        items = newItems
        notifyDataSetChanged()
    }

    class WishlistProductViewHolder(
        private val binding: ItemWishlistProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PurchaseProduct) {
            binding.productImage.setImageResource(item.imageResId)
            binding.productTitle.text = item.title
            binding.productDescription.text = item.description
            binding.productPrice.text = item.price
        }
    }

    override fun onViewAttachedToWindow(holder: WishlistProductViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(items[position])
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: WishlistProductViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }
}
