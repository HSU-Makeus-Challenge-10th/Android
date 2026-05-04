package com.example.umc10th

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlin.collections.listOf


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ex_name")

val PRODUCTS_KEY = stringPreferencesKey("products_key")

val PURCHASE_PRODUCTS_KEY = stringPreferencesKey("purchase_products_key")
private val gson = Gson()

suspend fun saveProducts(context: Context, Products: List<Product>) {

    val jsonString = gson.toJson(Products)
    context.dataStore.edit { settings ->
        settings[PRODUCTS_KEY] = jsonString
    }
}

fun getProducts(context: Context): Flow<List<Product>> {
    return context.dataStore.data.map { preferences ->
        val jsonString = preferences[PRODUCTS_KEY] ?: "[]"
        val type = object : TypeToken<List<Product>>() {}.type
        gson.fromJson(jsonString, type)
    }
}



suspend fun savePurchaseProducts(context: Context, Products: List<PurchaseProduct>) {

    val jsonString = gson.toJson(Products)
    context.dataStore.edit { settings ->
        settings[PURCHASE_PRODUCTS_KEY] = jsonString
    }
}

fun getPurchaseProducts(context: Context): Flow<List<PurchaseProduct>> {
    return context.dataStore.data.map { preferences ->
        val jsonString = preferences[PURCHASE_PRODUCTS_KEY] ?: "[]"
        val type = object : TypeToken<List<PurchaseProduct>>() {}.type
        gson.fromJson(jsonString, type)
    }
}
suspend fun initializeProducts(context: Context) {
    val currentProducts = getProducts(context).first()
    if(currentProducts.isNotEmpty())return

    val defaultProducts = listOf(
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115"),
        Product(R.drawable.shoes1, "Air Jordan XXXVI", "Men's Shoes", "US$185"),
        Product(R.drawable.shoes2, "Nike Air Force 1 '07", "Women's Shoes", "US$115")
    )
    saveProducts(context, defaultProducts)
}



suspend fun initializePurchaseProducts(context: Context) {
    val currentProducts = getPurchaseProducts(context).first()
    if(currentProducts.isNotEmpty())return

    val defaultPurchaseProducts = listOf(
        PurchaseProduct(1, R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
        PurchaseProduct(2, R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
        PurchaseProduct(3, R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
        PurchaseProduct(4, R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
        PurchaseProduct(5, R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
        PurchaseProduct(6, R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
        PurchaseProduct(7, R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
        PurchaseProduct(8, R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
        PurchaseProduct(9, R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
        PurchaseProduct(10, R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
        PurchaseProduct(11, R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
        PurchaseProduct(12, R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
        PurchaseProduct(13, R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
        PurchaseProduct(14, R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
        PurchaseProduct(15, R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
        PurchaseProduct(16, R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
        PurchaseProduct(17, R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
        PurchaseProduct(18, R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
        PurchaseProduct(19, R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
        PurchaseProduct(20, R.drawable.socks1, false, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "5 Colours", "US\$10"),
        PurchaseProduct(21, R.drawable.socks2, false, "Nike Elite Crew", "Basketball Socks", "7 Colours","US\$16"),
        PurchaseProduct(22, R.drawable.shoes1, true,"Nike Air Force 1 '07", "Women's Shoes", "5 Colours", "US\$115"),
        PurchaseProduct(23, R.drawable.shoes2, true, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "2 Colours","US\$115"),
        )
    savePurchaseProducts(context, defaultPurchaseProducts)
}