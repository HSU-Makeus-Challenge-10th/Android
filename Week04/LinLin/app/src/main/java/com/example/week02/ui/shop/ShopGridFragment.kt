package com.example.week02.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week02.data.ProductItem
import com.example.week02.data.ProductPreferencesRepository
import com.example.week02.databinding.FragmentShopGridBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopGridFragment : Fragment() {
    private var _binding: FragmentShopGridBinding? = null
    private val binding get() = _binding!!

    private val items = mutableListOf<ProductItem>()
    private lateinit var repo: ProductPreferencesRepository

    private val gridAdapter: ShopProductAdapter by lazy {
        ShopProductAdapter(items) { position ->
            val id = items.getOrNull(position)?.id ?: return@ShopProductAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) { repo.toggleShopHeart(id) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShopGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repo = ProductPreferencesRepository(requireContext())
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = gridAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            repo.ensureSeeded()
            repo.shopProductsFlow().collect { list ->
                items.clear()
                items.addAll(list)
                gridAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
