package com.umc.yido

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.umc.yido.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "LIFE_QUIZ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen 정상 종료 처리 (Android 12+)
        installSplashScreen()

        // 다크 모드 강제 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity : onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 처음 화면은 홈으로 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, HomeFragment())
                .commit()
        }

        // 바텀 네비게이션 클릭 이벤트
        binding.mainBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_shop -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, ShopFragment())
                        .commit()
                    true
                }
                R.id.nav_wishlist -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, WishlistFragment())
                        .commit()
                    true
                }
                R.id.nav_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, CartFragment())
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    // CartFragment에서 주문하기 클릭 시 구매하기 탭으로 이동
    fun navigateToShop() {
        binding.mainBottomNav.selectedItemId = R.id.nav_shop
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, ShopFragment())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity : onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity : onResume")

        // 에뮬레이터 렌더링 문제 우회: 강제로 레이아웃 다시 그리기
        binding.root.post {
            binding.root.requestLayout()
            binding.root.invalidate()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity : onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity : onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainActivity : onRestart")
    }
}
