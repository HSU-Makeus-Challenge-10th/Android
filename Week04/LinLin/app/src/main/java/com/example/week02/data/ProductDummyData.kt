package com.example.week02.data

import com.example.week02.R

object ProductDummyData {

    /** 홈 — What's new 가로 목록 (미션 동영상·시안과 비슷한 예시) */
    fun homeNewProducts(): List<ProductItem> = listOf(
        ProductItem(1, R.drawable.img_air_jordan_xxxvi, "Air Jordan XXXVI", price = "US\$185"),
        ProductItem(2, R.drawable.img_nike_air_force_1_07, "Nike Air Force 1 '07", price = "US\$115"),
        ProductItem(3, R.drawable.img_nike_elite_crew, "Nike Elite Crew", price = "US\$16"),
        ProductItem(4, R.drawable.img_nike_everyday_plus_cushioned, "Nike Everyday Plus Cushioned", price = "US\$10"),
        ProductItem(5, R.drawable.img_jordan_nike_af1_07_essentials, "Jordan / Nike Air Force 1 '07 Essentials", price = "US\$115"),
    )

    /** 구매하기 — 2열 그리드 */
    fun shopProducts(): List<ProductItem> = listOf(
        ProductItem(
            10, R.drawable.img_nike_everyday_plus_cushioned,
            "Nike Everyday Plus Cushioned",
            subtitle = "Training Ankle Socks (6 Pairs)",
            colorsLabel = "5 Colours",
            price = "US\$10",
            showHeart = true,
            heartFilled = true,
        ),
        ProductItem(
            11, R.drawable.img_nike_elite_crew,
            "Nike Elite Crew",
            subtitle = "Basketball Socks",
            colorsLabel = "7 Colours",
            price = "US\$16",
            showHeart = true,
            heartFilled = false,
        ),
        ProductItem(
            12, R.drawable.img_nike_air_force_1_07,
            "Nike Air Force 1 '07",
            subtitle = "Women's Shoes",
            colorsLabel = "5 Colours",
            price = "US\$115",
            isBestSeller = true,
            showHeart = true,
            heartFilled = false,
        ),
        ProductItem(
            13, R.drawable.img_jordan_nike_af1_07_essentials,
            "Jordan / Nike Air Force 1 '07 Essentials",
            subtitle = "Men's Shoes",
            colorsLabel = "2 Colours",
            price = "US\$115",
            isBestSeller = true,
            showHeart = true,
            heartFilled = false,
        ),
    )

    /** 위시리스트 — 2열 그리드 */
    fun wishlistProducts(): List<ProductItem> = listOf(
        ProductItem(
            21, R.drawable.img_nike_everyday_plus_cushioned,
            "Nike Everyday Plus Cushioned",
            subtitle = "Training Ankle Socks (6 Pairs)",
            colorsLabel = "5 Colours",
            price = "US\$10",
        ),
    )
}
