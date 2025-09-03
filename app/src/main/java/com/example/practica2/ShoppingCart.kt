// src/main/java/com/example/practica2/ShoppingCart.kt

package com.example.practica2

object ShoppingCart {
    private val products = mutableListOf<Product>()

    fun addItem(product: Product) {
        products.add(product)
    }

    fun getProducts(): List<Product> {
        return products.toList()
    }

    fun clearCart() {
        products.clear()
    }
}