package com.example.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.other.Resource
import com.example.shoppinglisttesting.remote.response.ImageResponse

interface ShoppingRepository {


    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}