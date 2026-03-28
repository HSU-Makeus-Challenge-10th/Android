package com.umc.yido

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.yido.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "LIFE_QUIZ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "CartFragment : onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "CartFragment : onCreateView")
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "CartFragment : onViewCreated")

        // 주문하기 버튼 클릭 → 구매하기(Shop) 탭으로 이동
        binding.cartBtnOrder.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.navigateToShop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "CartFragment : onDestroyView")
        _binding = null
    }
}
