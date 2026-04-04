package com.example.week02

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.week02.databinding.ActivityMainBinding
import com.example.week02.ui.cart.CartFragment
import com.example.week02.ui.cart.OnCartOrderListener
import com.example.week02.ui.home.HomeFragment
import com.example.week02.ui.profile.ProfileFragment
import com.example.week02.ui.shop.ShopFragment
import com.example.week02.ui.wishlist.WishlistFragment

// 앱의 메인 액티비티 (Fragment 전환 + Navigation 관리)
class MainActivity : AppCompatActivity(), OnCartOrderListener {

    // ViewBinding 객체 (XML과 연결)
    private var _binding: ActivityMainBinding? = null
    // null 방지를 위한 getter
    private val binding get() = _binding!!
    // 로그 태그 (생명주기 확인용)
    private val TAG = "LIFE_QUIZ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 전체 화면 UI
        // XML 연결
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate")
        // 시스템 바 padding 적용
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 최초 실행 시 HomeFragment 설정 + 하단 네비 선택 상태와 일치
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment())
                .commit()
            binding.mainBnv.selectedItemId = R.id.homeFragment
        }
        // BottomNavigation 클릭 처리
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // 홈
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, HomeFragment())
                        .commit()
                    true
                }
                // 쇼핑
                R.id.shopFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ShopFragment())
                        .commit()
                    true
                }
                // 위시리스트
                R.id.wishlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, WishlistFragment())
                        .commit()
                    true
                }
                // 장바구니
                R.id.cartFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, CartFragment())
                        .commit()
                    true
                }
                // 프로필
                R.id.profileFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
    // Fragment → Activity → Fragment 이동 핵심
    override fun onOrderClick() {
        // ShopFragment로 이동
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ShopFragment())
            .commit()
        // BottomNavigation 상태 변경
        binding.mainBnv.selectedItemId = R.id.shopFragment
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        _binding = null // 메모리 누수 방지
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}
