package com.example.umc10th

import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc10th.databinding.ItemPurchaseProductBinding

class PurchaseProductAdapter(
    private val items: List<PurchaseProduct>
) : RecyclerView.Adapter<PurchaseProductAdapter.PurchaseProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseProductViewHolder {
        val binding = ItemPurchaseProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PurchaseProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class PurchaseProductViewHolder(
        private val binding: ItemPurchaseProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PurchaseProduct) {
            binding.productImage.setImageResource(item.imageResId)
            binding.productBestSeller.visibility = if (item.isBest) View.VISIBLE else View.GONE
            binding.productTitle.text = item.title
            binding.productDescription.text = item.description
            binding.productPrice.text = item.price
            updateWishlistIcon(item.isWishlisted)

            binding.wishlistButton.setOnClickListener {
                item.isWishlisted = !item.isWishlisted
                updateWishlistIcon(item.isWishlisted)
            }
        }

        private fun updateWishlistIcon(isWishlisted: Boolean) {
            val iconResId = if (isWishlisted) {
                R.drawable.img_filled_heart
            } else {
                R.drawable.img_blank_heart
            }
            binding.wishlistButton.setImageResource(iconResId)
        }
    }
}
