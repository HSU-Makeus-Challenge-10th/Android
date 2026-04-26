package com.example.umc10th

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc10th.databinding.ItemLoadingProductBinding
import com.example.umc10th.databinding.ItemProductBinding

class ProductAdapter(
    private var items: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private var loadingItemCount = DEFAULT_LOADING_ITEM_COUNT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder(viewType=$viewType, isLoading=$isLoading)")
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                val binding = ItemLoadingProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadingViewHolder(binding)
            }
            else -> {
                val binding = ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProductViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(
            TAG,
            "onBindViewHolder(position=$position, holder=${holder::class.java.simpleName}, isLoading=$isLoading)"
        )
        if (holder is ProductViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val viewType = if (isLoading) VIEW_TYPE_LOADING else VIEW_TYPE_PRODUCT
        Log.d(TAG, "getItemViewType(position=$position) -> $viewType")
        return viewType
    }

    fun showLoading() {
        isLoading = true
        Log.d(TAG, "showLoading()")
        notifyDataSetChanged()
    }

    fun submitList(newItems: List<Product>) {
        isLoading = false
        items = newItems
        Log.d(TAG, "submitList(size=${newItems.size})")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        val count = if (isLoading) loadingItemCount else items.size
        Log.d(TAG, "getItemCount() -> $count (isLoading=$isLoading)")
        return count
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.productImage.setImageResource(item.imageResId)
            binding.productName.text = item.name
            binding.productPrice.text = item.price
        }
    }

    class LoadingViewHolder(
        binding: ItemLoadingProductBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is ProductViewHolder) {
            holder.itemView.setOnClickListener {
                val position = holder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION && !isLoading) {
                    onItemClick(items[position])
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    companion object {
        private const val TAG = "ProductAdapter"
        private const val VIEW_TYPE_PRODUCT = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val DEFAULT_LOADING_ITEM_COUNT = 3
    }
}
