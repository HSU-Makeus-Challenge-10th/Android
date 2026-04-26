package com.example.week02.data

import com.example.week02.R

object ItemData {

    val products = listOf(
        ItemList(
            id = 1,
            name = "Air Jordan XXXVI",
            price = "US185",
            imageResId = R.drawable.nike_item1,
            category = "Shoes",
            description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set.",
            colorCount = 3
        ),
        ItemList(
            id = 2,
            name = "Nike Air Force 1 '07",
            price = "US185",
            imageResId = R.drawable.nike_item2,
            category = "Shoes",
            description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set.",
            colorCount = 5
        ),
        ItemList(
            id = 3,
            name = "Nike Everyday Plus Cushioned",
            price = "US185",
            imageResId = R.drawable.nike_item3,
            category = "Shoes",
            colorCount = 2,
            description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set.",

        ),
        ItemList(
            id = 4,
            name = "Nike Dri-FIT Primary Top",
            price = "US185",
            imageResId = R.drawable.nike_item4,
            category = "Tops & T-Shirts",
            colorCount = 4,
            description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set."

        ),
        ItemList(
            id = 5,
            name = "Nike Everyday Plus Cushioned",
            price = "US185",
            imageResId = R.drawable.nike_item5,
            category = "Shoes",

            colorCount = 4,
            description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set."
        ),
        ItemList(
            id = 6,
            name = "Nike Everyday Plus Cushioned",
            price = "US185",
            imageResId = R.drawable.nike_item6,
            category = "Shoes",
            colorCount = 4,
            description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set."
        )
    )

    fun latestProducts(): List<ItemList> = products.take(5)

    fun productsByCategory(category: String?): List<ItemList> {
        if (category == null) return products
        return products.filter { it.category == category }
    }
}
