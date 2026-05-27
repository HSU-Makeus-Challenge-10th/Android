package com.example.deku.data

import com.example.deku.R

object ProductCatalog {
    private val baseProducts = listOf(
        ProductItem(
            id = 1,
            name = "Air Jordan XXXVI",
            price = "US185",
            imageResId = R.drawable.nike_item1,
            category = CATEGORY_SHOES,
            colorCount = 3,
            description = PRODUCT_DESCRIPTION
        ),
        ProductItem(
            id = 2,
            name = "Nike Air Force 1 '07",
            price = "US185",
            imageResId = R.drawable.nike_item2,
            category = CATEGORY_SHOES,
            colorCount = 5,
            description = PRODUCT_DESCRIPTION
        ),
        ProductItem(
            id = 3,
            name = "Nike Everyday Plus Cushioned",
            price = "US185",
            imageResId = R.drawable.nike_item3,
            category = CATEGORY_SHOES,
            colorCount = 2,
            description = PRODUCT_DESCRIPTION
        ),
        ProductItem(
            id = 4,
            name = "Nike Dri-FIT Primary Top",
            price = "US185",
            imageResId = R.drawable.nike_item4,
            category = CATEGORY_TOPS,
            colorCount = 4,
            description = PRODUCT_DESCRIPTION
        ),
        ProductItem(
            id = 5,
            name = "Nike Everyday Plus Cushioned",
            price = "US185",
            imageResId = R.drawable.nike_item5,
            category = CATEGORY_SHOES,
            colorCount = 4,
            description = PRODUCT_DESCRIPTION
        ),
        ProductItem(
            id = 6,
            name = "Nike Everyday Plus Cushioned",
            price = "US185",
            imageResId = R.drawable.nike_item6,
            category = CATEGORY_SHOES,
            colorCount = 4,
            description = PRODUCT_DESCRIPTION
        )
    )

    fun initialProducts(): List<ProductItem> = buildList {
        repeat(DUMMY_PRODUCT_GROUP_COUNT) { groupIndex ->
            baseProducts.forEach { product ->
                val nextId = groupIndex * baseProducts.size + product.id
                val displayName = if (groupIndex == 0) {
                    product.name
                } else {
                    "${product.name} ${groupIndex + 1}"
                }

                add(
                    product.copy(
                        id = nextId,
                        name = displayName
                    )
                )
            }
        }
    }

    fun latestProducts(products: List<ProductItem>): List<ProductItem> = products.take(5)

    fun productsByCategory(
        products: List<ProductItem>,
        category: String?
    ): List<ProductItem> {
        return if (category == null) {
            products
        } else {
            products.filter { it.category == category }
        }
    }

    const val CATEGORY_TOPS = "Tops & T-Shirts"
    const val CATEGORY_SHOES = "Shoes"

    private const val DUMMY_PRODUCT_GROUP_COUNT = 6
    private const val PRODUCT_DESCRIPTION =
        "The Nike Everyday Plus Cushioned Socks bring comfort to your workout with extra cushioning under the heel and forefoot and a snug, supportive arch band. Sweat-wicking power and breathability up top help keep your feet dry and cool to help push you through that extra set."
}
